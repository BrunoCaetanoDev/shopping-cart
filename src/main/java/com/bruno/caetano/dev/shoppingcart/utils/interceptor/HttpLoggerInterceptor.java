package com.bruno.caetano.dev.shoppingcart.utils.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

import static com.bruno.caetano.dev.shoppingcart.utils.constant.ShoppingCartConstant.*;

@Slf4j
@Component
public class HttpLoggerInterceptor implements HandlerInterceptor {

	StopWatch stopWatch;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		stopWatch = new StopWatch();
		log.info(LOGGING_HANDLER_INBOUND_MSG, request.getMethod(), request.getRequestURI(),
				Instant.now());
		stopWatch.start();
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		log.info(LOGGING_HANDLER_OUTBOUND_MSG, response.getStatus(), Instant.now());
		stopWatch.stop();
		log.info(LOGGING_HANDLER_PROCESS_TIME_MSG, stopWatch.getTotalTimeMillis());
	}

}
