/*
* Copyright (c). SDA SE Open Industry Solutions (https://www.sda.se).
*
* All rights reserved.
*/
package org.sdase.commons.spring.monitoring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:/org/sdase/commons/spring/monitoring/tracing.properties")
@Configuration
public class SdaTracingConfiguration {}
