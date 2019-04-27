package com.shopping.cart.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.shopping.cart.commons.Exception.NotEnoughProductsInStockException;
import com.shopping.cart.commons.Exception.CustomException;
import com.shopping.cart.commons.constants.CommonConstants;
import com.shopping.cart.entity.ProductEntity;
import com.shopping.cart.entity.ProductsAvailableEntity;
import com.shopping.cart.model.Product;
import com.shopping.cart.repo.ProductRepo;
import com.shopping.cart.repo.ProductsAvailableRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartService {
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ProductsAvailableRepo productsAvailableRepo;
	
	public Product saveProduct(Product product) {
		Optional<ProductEntity> optionalProductEntity = productRepo.findById(product.getId());
		if(optionalProductEntity.isPresent()) {
			throw new CustomException(HttpStatus.NOT_ACCEPTABLE, CommonConstants.RESOURCE_STATUS_NOT_ACCEPTED, "Product already there in Cart", new NoSuchElementException());
		}
		ProductEntity productEntity = new ProductEntity();
		BeanUtils.copyProperties(product, productEntity);
		productEntity = productRepo.save(productEntity);
		BeanUtils.copyProperties(productEntity, product);
		return product;
	}

	public void removeProduct(Integer id) {
		productRepo.deleteById(id);
	}

	public Product updateProduct(Product product) {
		Optional<ProductEntity> optionalProductEntity = productRepo.findById(product.getId());
		if(!optionalProductEntity.isPresent()) {
			throw new CustomException(HttpStatus.NOT_FOUND, CommonConstants.RESOURCE_STATUS_NOT_FOUND, "Product doesn't exist in cart.", new NoSuchElementException());
		}
		ProductEntity productEntity = new ProductEntity();
		BeanUtils.copyProperties(product, productEntity);
		productEntity = productRepo.save(productEntity);
		BeanUtils.copyProperties(productEntity, product);
		return product;
	}

	public List<Product> viewCart() {
		List<ProductEntity> productEntityList = productRepo.findAll();
		List<Product> productList = new ArrayList<>();
		if(productEntityList != null) {
			productEntityList.forEach(productEntity -> {
				Product product = new Product();
				BeanUtils.copyProperties(productEntity, product);
				productList.add(product);
			});
		}
		return productList;
	}

	public void clearCart() {
		productRepo.deleteAll();
	}

	public void checkout() throws NotEnoughProductsInStockException  {
			List<Product> productList = viewCart();
			if(productList != null) {
				productList.forEach(product -> {
					Optional<ProductsAvailableEntity> optionalProAvaible = productsAvailableRepo.findById(product.getId());
					if(!optionalProAvaible.isPresent()) {
						throw new CustomException(HttpStatus.NOT_FOUND, CommonConstants.RESOURCE_STATUS_NOT_FOUND, "Product doesn't exist in cart.", new NoSuchElementException());
					}
					ProductsAvailableEntity proAvaible = new ProductsAvailableEntity();
					BeanUtils.copyProperties(optionalProAvaible, product);
						if(product.getQuantity() > proAvaible.getQuantity())
							try {
								throw new NotEnoughProductsInStockException(proAvaible);
							} catch (NotEnoughProductsInStockException e) {
								e.printStackTrace();
							}	
				});
			}
			productRepo.deleteAll();	// Clear the cart after Checkout
	}
	
}
