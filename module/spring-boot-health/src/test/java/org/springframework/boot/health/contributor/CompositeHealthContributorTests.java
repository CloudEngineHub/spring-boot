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

package org.springframework.boot.health.contributor;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CompositeHealthContributor}.
 *
 * @author Phillip Webb
 * @author Guirong Hu
 */
class CompositeHealthContributorTests {

	@Test
	void fromMapReturnsCompositeHealthContributorMapAdapter() {
		Map<String, HealthContributor> map = new LinkedHashMap<>();
		HealthIndicator indicator = () -> Health.down().build();
		map.put("test", indicator);
		CompositeHealthContributor composite = CompositeHealthContributor.fromMap(map);
		assertThat(composite).isInstanceOf(MapCompositeHealthContributor.class);
		HealthContributors.Entry entry = composite.iterator().next();
		assertThat(entry.name()).isEqualTo("test");
		assertThat(entry.contributor()).isSameAs(indicator);
	}

	@Test
	void fromMapWithAdapterReturnsCompositeHealthContributorMapAdapter() {
		Map<String, HealthContributor> map = new LinkedHashMap<>();
		HealthIndicator downIndicator = () -> Health.down().build();
		HealthIndicator upIndicator = () -> Health.up().build();
		map.put("test", downIndicator);
		CompositeHealthContributor composite = CompositeHealthContributor.fromMap(map, (value) -> upIndicator);
		assertThat(composite).isInstanceOf(MapCompositeHealthContributor.class);
		HealthContributors.Entry entry = composite.iterator().next();
		assertThat(entry.name()).isEqualTo("test");
		assertThat(entry.contributor()).isSameAs(upIndicator);
	}

}
