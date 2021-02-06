package com.bruno.caetano.dev.shoppingcart.error;

public class UnavailableServiceException extends RuntimeException {

	private static String UNAVAILABLE_SERVICE_MSG = "Unavailable service %s.";


	public UnavailableServiceException(String serviceName) {
		super(String.format(UNAVAILABLE_SERVICE_MSG, serviceName));
	}


}
