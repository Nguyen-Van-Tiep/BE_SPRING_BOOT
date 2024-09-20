package com.example.demo.controller;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.model.request.CartItemRequest;
import com.example.demo.model.request.CartRequest;
import com.example.demo.model.response.ResponseData;
import com.example.demo.service.CartItemService;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1.0/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController extends BaseController{
    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/getCart")
    public ResponseEntity<ResponseData<Object>> getCart(@RequestBody CartRequest request) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(cartService.getCart(request)));
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseData<Object>> saveCart(@RequestBody CartRequest cartRequest) throws BusinessException {
        cartService.saveCart(cartRequest);
        return ResponseEntity.ok()
                .body(new ResponseData<>().success("Save Cart Success"));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseData<Object>> updateCartItem(@RequestBody CartItemRequest request) throws BusinessException {
        cartService.updateCartItem(request);
        return ResponseEntity.ok()
                .body(new ResponseData<>().success("Update CartItem Success"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseData<Object>> deleteCartItem(@PathVariable("id")Integer id  ) throws BusinessException {
        cartService.deleteCartItem(id);
        return ResponseEntity.ok()
                .body(new ResponseData<>().success("Delete CartItem Success"));
    }


}
