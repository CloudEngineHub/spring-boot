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

package org.springframework.boot.jdbc.autoconfigure;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.ObjectProvider;

/**
 * Post-processes beans of type {@link HikariDataSource} and name 'dataSource' to apply
 * the values from {@link JdbcConnectionDetails}.
 *
 * @author Moritz Halbritter
 * @author Andy Wilkinson
 * @author Phillip Webb
 */
class HikariJdbcConnectionDetailsBeanPostProcessor extends JdbcConnectionDetailsBeanPostProcessor<HikariDataSource> {

	HikariJdbcConnectionDetailsBeanPostProcessor(ObjectProvider<JdbcConnectionDetails> connectionDetailsProvider) {
		super(HikariDataSource.class, connectionDetailsProvider);
	}

	@Override
	protected Object processDataSource(HikariDataSource dataSource, JdbcConnectionDetails connectionDetails) {
		dataSource.setJdbcUrl(connectionDetails.getJdbcUrl());
		dataSource.setUsername(connectionDetails.getUsername());
		dataSource.setPassword(connectionDetails.getPassword());
		String driverClassName = connectionDetails.getDriverClassName();
		if (driverClassName != null) {
			dataSource.setDriverClassName(driverClassName);
		}
		return dataSource;
	}

}
