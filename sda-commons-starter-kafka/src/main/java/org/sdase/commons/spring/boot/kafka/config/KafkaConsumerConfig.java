/*
 * Copyright 2022- SDA SE Open Industry Solutions (https://www.sda.se)
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package org.sdase.commons.spring.boot.kafka.config;

import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "sda.kafka.consumer")
public record KafkaConsumerConfig(@NotNull RetryConfig retry) {
  public record RetryConfig(
      @NotNull Integer maxRetries,
      @NotNull Long initialBackoffInterval,
      @NotNull Long maxBackoffInterval,
      @NotNull Long backoffMultiplier) {}
}