/*
 * Copyright 2020 the original author or authors.
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
package example.spring.events.a.fundamentals.quickstart;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import example.spring.events.util.IntegrationTest;
import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * @author Oliver Drotbohm
 */
@IntegrationTest
@RequiredArgsConstructor
class OrderEventPublicationTests {

	private final OrderManagement orders;

	@MockBean
	OrderRepository repository;

	@Test
	void publishesEventOnCompletion() {
		orders.completeOrder(new Order());
	}

	@Test
	void doesNotPublishFurtherEventsOnException() {

		var order = new Order();

		doThrow(IllegalStateException.class).when(repository).save(order);

		assertThatExceptionOfType(RuntimeException.class)
				.isThrownBy(() -> orders.completeOrder(order));
	}
}
