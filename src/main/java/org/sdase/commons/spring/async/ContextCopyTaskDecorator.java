/*
* Copyright (c). SDA SE Open Industry Solutions (https://www.sda.se).
*
* All rights reserved.
*/
package org.sdase.commons.spring.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class ContextCopyTaskDecorator implements TaskDecorator {
  private static final Logger LOG = LoggerFactory.getLogger(ContextCopyTaskDecorator.class);

  @Override
  public Runnable decorate(Runnable runnable) {
    try {
      // TODO need to copy logging context and tracing context as well when they are used
      return new ContextAwareRunnable(runnable, RequestContextHolder.currentRequestAttributes());
    } catch (IllegalStateException e) {
      LOG.debug("Not transferring request attributes. Not in a request context?");
      return runnable;
    }
  }

  private static class ContextAwareRunnable implements Runnable {

    private final Runnable task;
    private final RequestAttributes requestAttributes;

    public ContextAwareRunnable(Runnable task, RequestAttributes requestAttributes) {
      this.task = task;
      this.requestAttributes = requestAttributes;
    }

    @Override
    public void run() {
      RequestContextHolder.setRequestAttributes(requestAttributes);
      try {
        this.task.run();
      } finally {
        RequestContextHolder.resetRequestAttributes();
      }
    }
  }
}
