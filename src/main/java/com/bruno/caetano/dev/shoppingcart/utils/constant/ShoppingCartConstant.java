package com.bruno.caetano.dev.shoppingcart.utils.constant;

public class ShoppingCartConstant {


	/**
	 * Operations
	 */
	public static final String GET_CARTS_SERVICE_OPERATION = "GetCarts";
	public static final String GET_CART_SERVICE_OPERATION = "GetCart";
	public static final String CREATE_CART_SERVICE_OPERATION = "CreateCart";
	public static final String UPDATE_CART_SERVICE_OPERATION = "UpdateCart";
	public static final String DELETE_CART_SERVICE_OPERATION = "DeleteCart";
	public static final String GET_CART_ITEMS_SERVICE_OPERATION = "GetCartItems";
	public static final String GET_CART_ITEM_SERVICE_OPERATION = "GetCartItem";
	public static final String ADD_CART_ITEM_SERVICE_OPERATION = "AddCartItem";
	public static final String REMOVE_CART_ITEM_SERVICE_OPERATION = "RemoveCartItem";
	public static final String UNDEFINED_SERVICE_OPERATION = "Undefined";

	/**
	 * MDC Keys
	 */
	public static final String SERVICE_OPERATION = "operation";
	public static final String TRACE_ID = "trace-id";

	/**
	 * Header Names
	 */
	public static final String TRACE_ID_HEADER = "Trace-Id";
	public static final String SERVICE_OPERATION_HEADER = "Service-Operation";
	public static final String LINK_HEADER = "Link";
	public static final String PAGE_NUMBER_HEADER = "Page-Number";
	public static final String PAGE_SIZE_HEADER = "Page-Size";
	public static final String TOTAL_ELEMENTS_HEADER = "Total-Elements";
	public static final String TOTAL_PAGES_HEADER = "Total-Pages";

	/**
	 * Messages
	 */
	public static final String GET_CARTS_MSG = "Getting carts";
	public static final String GET_CARTS_COUNT_MSG = "Got {} carts out of {}";
	public static final String GET_CART_MSG = "Getting cart";
	public static final String GET_CART_RESULT_MSG = "Got cart id::{}";
	public static final String CREATE_CART_MSG = "Creating cart";
	public static final String CREATE_CART_RESULT_MSG = "Created cart id::{}";
	public static final String UPDATE_CART_MSG = "Updating cart id::{}";
	public static final String UPDATE_CART_RESULT_MSG = "Updated cart id::{}";
	public static final String DELETE_CART_MSG = "Deleting cart id::{}";
	public static final String DELETE_CART_RESULT_MSG = "Deleted cart id::{}";
	public static final String RESTOCK_CART_MSG = "Restocking cart id::{}";
	public static final String RESTOCK_CART_RESULT_MSG = "Restocked cart id::{} by amount {}";
	public static final String DISPATCH_CART_MSG = "Dispatching cart id::{}";
	public static final String DISPATCH_CART_RESULT_MSG = "Dispatched cart id::{} by amount {}";
	public static final String LOGGING_HANDLER_INBOUND_MSG = "Received HTTP {} Request to {} at {}";
	public static final String LOGGING_HANDLER_OUTBOUND_MSG = "Responded with Status {} at {}";
	public static final String LOGGING_HANDLER_PROCESS_TIME_MSG = "Total processing time {} ms";
	public static final String INVALID_MARKET_FIELD_MSG = "market field should match ISO 3166-1 alpha-2 specification";
	public static final String INVALID_EMPTY_OR_BLANK_STRING_MSG = "cannot be empty or blank";
	public static final String ENTITY_NOT_FOUND_MSG = "Entity %s id::{%s} not found.";

	/**
	 * Miscellaneous
	 */
	public static final String FRONT_SLASH_DELIMITER = "/";
	public static final String COLON_WHITE_SPACE_DELIMITER = ", ";
	public static final String WHITE_SPACE_DELIMITER = " ";
	public static final String SEMI_COLON_DELIMITER = ";";
	public static final String CART_API_DESCRIPTION = "A public Restful Api that allows to manage the various cart resources.";
	public static final String ISO_3166_1_ALPHA_2_REGEX = "^[A-Z]{2}$";
	public static final String EMPTY_OR_BLANK_STRING_REGEX = "^(?!\\s*$).+";
	public static final String CARTS = "carts";
	public static final String CART = "cart";
	public static final String CART_ITEM = "cartItem";
	public static final String CART_ITEMS = "cartItems";
	public static final String ITEMS = "items";
	public static final String ITEM_STORAGE_DISPATCH_URI = "dispatch";


}
