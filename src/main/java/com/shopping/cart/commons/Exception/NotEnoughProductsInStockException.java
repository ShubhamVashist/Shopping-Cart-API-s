package com.shopping.cart.commons.Exception;

import com.shopping.cart.entity.ProductsAvailableEntity;

public class NotEnoughProductsInStockException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE = "Not enough products in stock";

    public NotEnoughProductsInStockException() {
        super(DEFAULT_MESSAGE);
    }

    public NotEnoughProductsInStockException(ProductsAvailableEntity productsAvailable) {
        super(String.format("Not enough %s products in stock. Only %d left", productsAvailable.getDescription(), productsAvailable.getQuantity()));
    }

}
