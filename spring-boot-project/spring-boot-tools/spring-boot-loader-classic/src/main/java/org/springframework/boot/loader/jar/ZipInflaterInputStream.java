/*
 * Copyright 2012-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.loader.jar;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * {@link InflaterInputStream} that supports the writing of an extra "dummy" byte (which
 * is required with JDK 6) and returns accurate available() results.
 *
 * @author Phillip Webb
 */
class ZipInflaterInputStream extends InflaterInputStream {

	private final boolean ownsInflator;

	private int available;

	private boolean extraBytesWritten;

	ZipInflaterInputStream(InputStream inputStream, int size) {
		this(inputStream, new Inflater(true), size, true);
	}

	ZipInflaterInputStream(InputStream inputStream, Inflater inflater, int size) {
		this(inputStream, inflater, size, false);
	}

	private ZipInflaterInputStream(InputStream inputStream, Inflater inflater, int size, boolean ownsInflator) {
		super(inputStream, inflater, getInflaterBufferSize(size));
		this.ownsInflator = ownsInflator;
		this.available = size;
	}

	@Override
	public int available() throws IOException {
		if (this.available < 0) {
			return super.available();
		}
		return this.available;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int result = super.read(b, off, len);
		if (result != -1) {
			this.available -= result;
		}
		return result;
	}

	@Override
	public void close() throws IOException {
		super.close();
		if (this.ownsInflator) {
			this.inf.end();
		}
	}

	@Override
	protected void fill() throws IOException {
		try {
			super.fill();
		}
		catch (EOFException ex) {
			if (this.extraBytesWritten) {
				throw ex;
			}
			this.len = 1;
			this.buf[0] = 0x0;
			this.extraBytesWritten = true;
			this.inf.setInput(this.buf, 0, this.len);
		}
	}

	private static int getInflaterBufferSize(long size) {
		size += 2; // inflater likes some space
		size = (size > 65536) ? 8192 : size;
		size = (size <= 0) ? 4096 : size;
		return (int) size;
	}

}
