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

package org.springframework.boot.testsupport.classpath;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ForkedClassPath @ForkedClassPath}.
 *
 * @author Andy Wilkinson
 */
@ForkedClassPath
class ModifiedClassPathExtensionForkParameterizedTests {

	private static final List<String> arguments = new ArrayList<>();

	@ParameterizedTest
	@ValueSource(strings = { "one", "two", "three" })
	void testIsInvokedOnceForEachArgument(String argument) {
		if (argument.equals("one")) {
			assertThat(arguments).isEmpty();
		}
		else if (argument.equals("two")) {
			assertThat(arguments).doesNotContain("two", "three");
		}
		else if (argument.equals("three")) {
			assertThat(arguments).doesNotContain("three");
		}
		arguments.add(argument);
	}

}
