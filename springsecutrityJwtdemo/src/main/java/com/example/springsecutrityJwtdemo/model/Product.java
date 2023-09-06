package com.example.springsecutrityJwtdemo.model;

import java.util.Objects;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity

@Table(name="product")
	public class Product {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private int id;
		private String name;
		private int price;
		private int quantity;
		public Product() {
			
		}
		public Product(int id, String name, int price, int quantity) {
			super();
			this.id = id;
			this.name = name;
			this.price = price;
			this.quantity = quantity;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		@Override
		public String toString() {
			return "Product [id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + "]";
		}
		@Override
		public int hashCode() {
			return Objects.hash(id, name, price, quantity);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Product other = (Product) obj;
			return id == other.id && Objects.equals(name, other.name) && price == other.price
					&& quantity == other.quantity;
		}
		

}
