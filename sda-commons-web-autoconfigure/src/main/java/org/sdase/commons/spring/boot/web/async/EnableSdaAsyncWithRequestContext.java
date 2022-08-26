/*
 * Copyright 2022- SDA SE Open Industry Solutions (https://www.sda.se)
 *
 * Use of this source code is governed by an MIT-style
 * license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 */
package org.sdase.commons.spring.boot.web.async;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.sdase.commons.spring.boot.web.auth.EnableSdaSecurity;
import org.sdase.commons.spring.boot.web.auth.opa.Constraints;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Configures Springs {@link org.springframework.scheduling.annotation.Async} executor to transfer
 * the request attributes of the current request to the {@link Thread} running the asynchronous
 * method.
 *
 * <p>{@link Constraints} from {@link EnableSdaSecurity} are available in the asynchronous {@link
 * Thread} as well.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableAsync
@Import(SdaAsyncConfiguration.class)
// TODO We may use this instead of the auto configuration
public @interface EnableSdaAsyncWithRequestContext {}
