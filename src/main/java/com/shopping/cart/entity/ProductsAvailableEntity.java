package com.shopping.cart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "AVAILABLE_PRODUCTS")
public class ProductsAvailableEntity {
		
		@Id
	    @Column(name = "product_id")
	    private Integer id;

	    @Column(name = "description")
	    private String description;

	    @Column(name = "quantity", nullable = false)
	    @Min(value = 0, message = "*Quantity has to be non negative number")
	    private Integer quantity;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

}
