
package com.shopping.cart.repo;

import org.springframework.stereotype.Repository;

import com.shopping.cart.commons.repo.BaseRepository;
import com.shopping.cart.entity.ProductsAvailableEntity;

@Repository
public interface ProductsAvailableRepo extends BaseRepository<ProductsAvailableEntity, Integer> {

}