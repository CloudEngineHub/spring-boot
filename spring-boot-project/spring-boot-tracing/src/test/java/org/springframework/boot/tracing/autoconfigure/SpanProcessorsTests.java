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

package org.springframework.boot.tracing.autoconfigure;

import java.util.List;

import io.opentelemetry.sdk.trace.SpanProcessor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link SpanProcessors}.
 *
 * @author Moritz Halbritter
 */
class SpanProcessorsTests {

	@Test
	void ofList() {
		SpanProcessor spanProcessor1 = mock(SpanProcessor.class);
		SpanProcessor spanProcessor2 = mock(SpanProcessor.class);
		SpanProcessors spanProcessors = SpanProcessors.of(List.of(spanProcessor1, spanProcessor2));
		assertThat(spanProcessors).containsExactly(spanProcessor1, spanProcessor2);
		assertThat(spanProcessors.list()).containsExactly(spanProcessor1, spanProcessor2);
	}

	@Test
	void ofArray() {
		SpanProcessor spanProcessor1 = mock(SpanProcessor.class);
		SpanProcessor spanProcessor2 = mock(SpanProcessor.class);
		SpanProcessors spanProcessors = SpanProcessors.of(spanProcessor1, spanProcessor2);
		assertThat(spanProcessors).containsExactly(spanProcessor1, spanProcessor2);
		assertThat(spanProcessors.list()).containsExactly(spanProcessor1, spanProcessor2);
	}

}
