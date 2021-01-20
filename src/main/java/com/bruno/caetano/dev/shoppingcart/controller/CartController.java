package com.bruno.caetano.dev.shoppingcart.controller;

import com.bruno.caetano.dev.shoppingcart.entity.model.Cart;
import com.bruno.caetano.dev.shoppingcart.entity.model.CartItem;
import com.bruno.caetano.dev.shoppingcart.entity.request.in.CreateCartRequest;
import com.bruno.caetano.dev.shoppingcart.entity.request.in.UpdateCartRequest;
import com.bruno.caetano.dev.shoppingcart.entity.response.out.CreateCartResponse;
import com.bruno.caetano.dev.shoppingcart.entity.response.out.GetCartItemResponse;
import com.bruno.caetano.dev.shoppingcart.entity.response.out.GetCartResponse;
import com.bruno.caetano.dev.shoppingcart.entity.response.out.UpdateCartResponse;
import com.bruno.caetano.dev.shoppingcart.enums.CartStatus;
import com.bruno.caetano.dev.shoppingcart.service.CartItemService;
import com.bruno.caetano.dev.shoppingcart.service.CartService;
import com.bruno.caetano.dev.shoppingcart.utils.annotation.ServiceOperation;
import com.bruno.caetano.dev.shoppingcart.utils.properties.ShoppingCartProperties;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bruno.caetano.dev.shoppingcart.utils.constant.ShoppingCartConstant.*;
import static org.springframework.hateoas.IanaLinkRelations.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(CARTS)
@Tag(name = CARTS, description = CART_API_DESCRIPTION)
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final ModelMapper modelMapper;
    private final ShoppingCartProperties shoppingCartProperties;


    @GetMapping
    @ServiceOperation(GET_CARTS_SERVICE_OPERATION)
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List the carts",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = GetCartResponse.class)))}
    )})
    public ResponseEntity<PagedModel<GetCartResponse>> getCarts(@RequestParam(name = "status", required = false) String status, Pageable pageRequest) {
        log.trace(GET_CARTS_MSG);
        CartStatus cartStatus = CartStatus.fromName(status);
        Page<Cart> cartPage = cartService.findAll(Cart.builder().status(cartStatus).build(), pageRequest);
        log.info(GET_CARTS_COUNT_MSG, cartPage.getNumberOfElements(), cartPage.getTotalElements());
        return ResponseEntity.ok().body(buildPagedModel(cartPage, pageRequest));
    }

    @GetMapping("/{id}")
    @ServiceOperation(GET_CART_SERVICE_OPERATION)
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get a single cart",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GetCartResponse.class))}
    )})
    public ResponseEntity<GetCartResponse> getCart(@PathVariable("id") String id) {
        log.trace(GET_CART_MSG);
        Cart cart = cartService.findBydId(id);
        log.info(GET_CART_RESULT_MSG, id);
        GetCartResponse response = modelMapper.map(cart, GetCartResponse.class);
        response.setTotal(cartService.calculateCartTotal(cart));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @ServiceOperation(CREATE_CART_SERVICE_OPERATION)
    public ResponseEntity<CreateCartResponse> createCart(@RequestBody @Valid CreateCartRequest request) {
        log.trace(CREATE_CART_MSG);
        Cart cart = cartService.save(modelMapper.map(request, Cart.class));
        log.info(CREATE_CART_RESULT_MSG, cart.getId());
        return ResponseEntity.created(URI.create(
                String.join(FRONT_SLASH_DELIMITER, shoppingCartProperties.getServletContextPath(), CART, cart.getId())))
                .body(modelMapper.map(cart, CreateCartResponse.class));
    }

    @PatchMapping("/{id}")
    @ServiceOperation(UPDATE_CART_SERVICE_OPERATION)
    public ResponseEntity<UpdateCartResponse> updateCart(@PathVariable("id") String id, @RequestBody @Valid UpdateCartRequest request) {
        log.trace(UPDATE_CART_MSG, id);
        request.setId(id);
        Cart cart = cartService.update(modelMapper.map(request, Cart.class));
        log.info(UPDATE_CART_RESULT_MSG, id);
        return ResponseEntity.ok(modelMapper.map(cart, UpdateCartResponse.class));
    }

    @DeleteMapping("/{id}")
    @ServiceOperation(DELETE_CART_SERVICE_OPERATION)
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable("id") String id) {
        log.trace(DELETE_CART_MSG, id);
        cartService.deleteById(id);
        log.info(DELETE_CART_RESULT_MSG, id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}/" + ITEMS)
    @ServiceOperation(GET_CART_ITEMS_SERVICE_OPERATION)
     public ResponseEntity<PagedModel<GetCartItemResponse>> listCartItems(@PathVariable("id") String cartUid, Pageable pageRequest) {
        Page<CartItem> cartItemPage = cartItemService.findAll(CartItem.builder().cart(Cart.builder().id(cartUid).build()).build(), pageRequest);
        log.info(GET_CARTS_COUNT_MSG, cartItemPage.getNumberOfElements(), cartItemPage.getTotalElements());
        return ResponseEntity.ok().body(buildCartItemPagedModel(cartItemPage, pageRequest));
    }

    @GetMapping("/{cart-uid}/"+ ITEMS + "/{item-uid}")
    @ServiceOperation(GET_CART_ITEM_SERVICE_OPERATION)
    public ResponseEntity<EntityModel<GetCartItemResponse>> getCartItem(@PathVariable("cart-uid") String cartUid,
                                                                        @PathVariable("item-uid") String itemUid) {
        GetCartItemResponse item = modelMapper.map(cartService.getCartItem(cartUid, itemUid), GetCartItemResponse.class);
        EntityModel model = EntityModel.of(item,
                Link.of(String.join(FRONT_SLASH_DELIMITER, shoppingCartProperties.itemStorageApiUrl, String.valueOf(itemUid)))
                        .withRel("details"));
        return new ResponseEntity<>(model,
                HttpStatus.OK);
    }

    @PutMapping("/{id}/" + ITEMS)
    @ServiceOperation(ADD_CART_ITEM_SERVICE_OPERATION)
    public ResponseEntity<HttpStatus> addCartItem(@PathVariable("id") String cartUid) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{cart-uid}/"+ ITEMS  +"/{item-uid}")
    @ServiceOperation(REMOVE_CART_ITEM_SERVICE_OPERATION)
    public ResponseEntity<GetCartItemResponse> removeCartItem(@PathVariable("cart-uid") Long cartUid,
                                                              @PathVariable("item-uid") Long cartItemUid) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private PagedModel<GetCartItemResponse> buildCartItemPagedModel(Page<CartItem> cartPage, Pageable pageRequest) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        UriComponentsBuilder baseUri = ServletUriComponentsBuilder.fromServletMapping(request)
                .path(request.getRequestURI());

        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            for (String value : entry.getValue()) {
                baseUri.queryParam(entry.getKey(), value);
            }
        }

        UriComponentsBuilder original = baseUri;

        List<GetCartItemResponse> getItemResponseList = cartPage.getContent().stream()
                .map(c ->  modelMapper.map(c, GetCartItemResponse.class)).collect(Collectors.toList());
        PagedModel<GetCartItemResponse> getItemResponsePagedModel = PagedModel.of(getItemResponseList,
                new PagedModel.PageMetadata(cartPage.getSize(), cartPage.getNumber(), cartPage.getTotalElements(),
                        cartPage.getTotalPages()));
        UriComponentsBuilder selfBuilder = replacePageParams(original, cartPage.getPageable());
        getItemResponsePagedModel.add(Link.of(selfBuilder.toUriString()).withSelfRel());
        if (cartPage.hasNext()) {
            UriComponentsBuilder nextBuilder = replacePageParams(original, cartPage.nextPageable());
            getItemResponsePagedModel.add(Link.of(nextBuilder.toUriString()).withRel(NEXT));
        }
        if (cartPage.hasPrevious()) {
            UriComponentsBuilder previousBuilder = replacePageParams(original, cartPage.previousPageable());
            getItemResponsePagedModel.add(Link.of(previousBuilder.toUriString()).withRel(PREVIOUS));
        }
        if (!cartPage.isFirst()) {
            UriComponentsBuilder firstBuilder = replacePageParams(original,
                    PageRequest.of(0, pageRequest.getPageSize(), pageRequest.getSort()));
            getItemResponsePagedModel.add(Link.of(firstBuilder.toUriString()).withRel(FIRST));
        }
        if (!cartPage.isLast()) {
            UriComponentsBuilder firstBuilder = replacePageParams(original,
                    PageRequest.of(cartPage.getTotalPages() - 1, pageRequest.getPageSize(), pageRequest.getSort()));
            getItemResponsePagedModel.add(Link.of(firstBuilder.toUriString()).withRel(LAST));
        }
        return getItemResponsePagedModel;
    }


    private PagedModel<GetCartResponse> buildPagedModel(Page<Cart> cartPage, Pageable pageRequest) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        UriComponentsBuilder baseUri = ServletUriComponentsBuilder.fromServletMapping(request)
                .path(request.getRequestURI());

        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            for (String value : entry.getValue()) {
                baseUri.queryParam(entry.getKey(), value);
            }
        }

        UriComponentsBuilder original = baseUri;

        List<GetCartResponse> getItemResponseList = cartPage.getContent().stream()
                .map(c -> {
                    GetCartResponse response = modelMapper.map(c, GetCartResponse.class);
                    response.setTotal(cartService.calculateCartTotal(c));
                    return response;
                })
                .collect(Collectors.toList());
        PagedModel<GetCartResponse> getItemResponsePagedModel = PagedModel.of(getItemResponseList,
                new PagedModel.PageMetadata(cartPage.getSize(), cartPage.getNumber(), cartPage.getTotalElements(),
                        cartPage.getTotalPages()));
        UriComponentsBuilder selfBuilder = replacePageParams(original, cartPage.getPageable());
        getItemResponsePagedModel.add(Link.of(selfBuilder.toUriString()).withSelfRel());
        if (cartPage.hasNext()) {
            UriComponentsBuilder nextBuilder = replacePageParams(original, cartPage.nextPageable());
            getItemResponsePagedModel.add(Link.of(nextBuilder.toUriString()).withRel(NEXT));
        }
        if (cartPage.hasPrevious()) {
            UriComponentsBuilder previousBuilder = replacePageParams(original, cartPage.previousPageable());
            getItemResponsePagedModel.add(Link.of(previousBuilder.toUriString()).withRel(PREVIOUS));
        }
        if (!cartPage.isFirst()) {
            UriComponentsBuilder firstBuilder = replacePageParams(original,
                    PageRequest.of(0, pageRequest.getPageSize(), pageRequest.getSort()));
            getItemResponsePagedModel.add(Link.of(firstBuilder.toUriString()).withRel(FIRST));
        }
        if (!cartPage.isLast()) {
            UriComponentsBuilder firstBuilder = replacePageParams(original,
                    PageRequest.of(cartPage.getTotalPages() - 1, pageRequest.getPageSize(), pageRequest.getSort()));
            getItemResponsePagedModel.add(Link.of(firstBuilder.toUriString()).withRel(LAST));
        }
        return getItemResponsePagedModel;
    }

    private UriComponentsBuilder replacePageParams(UriComponentsBuilder original, Pageable page) {
        UriComponentsBuilder builder = original.cloneBuilder();
        builder.replaceQueryParam("page", page.getPageNumber());
        builder.replaceQueryParam("size", page.getPageSize());
        return builder;
    }
}