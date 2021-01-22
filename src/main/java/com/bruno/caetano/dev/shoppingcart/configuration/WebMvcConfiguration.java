package com.bruno.caetano.dev.shoppingcart.configuration;

import com.bruno.caetano.dev.shoppingcart.utils.interceptor.HttpLoggerInterceptor;
import com.bruno.caetano.dev.shoppingcart.utils.interceptor.MdcInitInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration {

	@Bean
	public WebMvcConfigurer initializerWebMvcConfigurer(MdcInitInterceptor mdcInitHandler,
														HttpLoggerInterceptor loggingHandler) {
		return new WebMvcConfigurer() {
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(mdcInitHandler);
				registry.addInterceptor(loggingHandler);
			}
		};
	}

}
