package com.example.demo.service.implement;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.*;
import com.example.demo.model.request.CartItemRequest;
import com.example.demo.model.request.CartRequest;
import com.example.demo.repository.*;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CartServiceImplement implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Override
    public List<CartItem> getCart(CartRequest request) throws BusinessException {
        User user = userRepository.findUserByUsername(request.getUsername());
        Cart cart = new Cart();
        cart = cartRepository.findByIdUser(user);
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        return cartItems;
    }

    @Override
    public void saveCart(CartRequest cartRequest) throws BusinessException {
//        System.out.println(cartRequest.getUsername() +", "+ cartRequest.getDetailID());
        Cart cart = new Cart();
        User user = userRepository.findUserByUsername(cartRequest.getUsername());
        cart = cartRepository.findByIdUser(user);
        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            cart = cartRepository.save(newCart);
        }
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        ProductDetail productDetail = productDetailsRepository.findById(cartRequest.getDetailID()).get();
        boolean itemExists = false;
        for (CartItem item : cartItems) {
            if (item.getProductDetail().equals(productDetail)) {
                // Nếu đã tồn tại, tăng số lượng lên 1
                item.setQuantity(item.getQuantity() + 1);
                cartItemRepository.save(item); // Lưu thay đổi vào cơ sở dữ liệu
                itemExists = true;
                break;
            }
        }
        // Nếu productDetail chưa tồn tại, tạo mới và thêm vào danh sách
        if (!itemExists) {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProductDetail(productDetail);
            newItem.setQuantity(1);
            cartItemRepository.save(newItem); // Lưu phần tử mới vào cơ sở dữ liệu
        }
    }

    @Override
    public CartItem updateCartItem(CartItemRequest request) throws BusinessException {
        CartItem cartItem = cartItemRepository.findById(request.getId()).get();
        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @Override
    public void deleteCartItem(Integer id) throws BusinessException {
        cartItemRepository.deleteById(id);
    }


}
