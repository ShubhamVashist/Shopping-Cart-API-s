package com.shopping.cart.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.cart.commons.Exception.NotEnoughProductsInStockException;
import com.shopping.cart.model.Product;
import com.shopping.cart.service.CartService;

@RestController
@RequestMapping("shop/cart/")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	//Add an item to a Cart
	@RequestMapping(value = "addItem", method = RequestMethod.POST)
    public Product saveProduct(@Valid @RequestBody Product product){
        return cartService.saveProduct(product);
    }
	
	//Remove an item form Cart
	@RequestMapping(value = "removeItem/{id}", method = RequestMethod.DELETE)
    public void removeProduct(@PathVariable Integer id){
        cartService.removeProduct(id);
    }
	
	//Update the cart or quantity of item in cart
	@RequestMapping(value = "updateItem", method = RequestMethod.PUT)
    public Product updateProduct(@Valid @RequestBody Product product){
		return cartService.updateProduct(product);
    }
	
	//View the Cart
	@RequestMapping(value = "viewAllItems", method = RequestMethod.GET)
    public List<Product> viewCart(){
		return cartService.viewCart();
    }
	
	//Clear the Cart
	@RequestMapping(value = "clearAllItems", method = RequestMethod.DELETE)
    public void clearCart(){
		cartService.clearCart();
    }
	
	//Checkout the Cart
	@RequestMapping(value= "zcheckout", method = RequestMethod.GET)
    public ResponseEntity<String> checkout(){
		try {
			cartService.checkout();
			return new ResponseEntity<String>("Checked out successfully", HttpStatus.OK);
        } catch (NotEnoughProductsInStockException e) {
        	return new ResponseEntity<String>("Product not available with quantity asked", HttpStatus.NOT_ACCEPTABLE);
        }
        
    }

}
