package com.shopping.cart.repo;

import org.springframework.stereotype.Repository;

import com.shopping.cart.commons.repo.BaseRepository;
import com.shopping.cart.entity.ProductEntity;

@Repository
public interface ProductRepo extends BaseRepository<ProductEntity, Integer> {

}
