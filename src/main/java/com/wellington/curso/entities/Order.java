package com.wellington.curso.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellington.curso.entities.enums.OrderStatus;

@Entity
@Table(name = "tb_order")
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH::mm:ss'Z'", timezone = "GMT")
	Instant moment;
	
	Integer orderStatus;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private User client;
	
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
	private Payment payment;

	@OneToMany(mappedBy = "id.order")
	private Set<OrderItem> items = new HashSet<>();
	
	public Set<OrderItem> getItems(){
		return items;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public OrderStatus getOrderStatus() {
		return OrderStatus.valueOf(orderStatus);
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		if(orderStatus != null) {
			this.orderStatus = orderStatus.getCode();
		}
	}
	
	@JsonIgnore
	public Payment getPayment() {
		return payment;
	}
	
	public void SetPayment(Payment payment) {
		this.payment = payment;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(id, other.id);
	}

	public Order(Long id, Instant moment, OrderStatus orderStatus, User client) {
		super();
		this.id = id;
		this.moment = moment;
		this.client = client;
		setOrderStatus(orderStatus);
		
	}
	
	public Order() {
		
	}
	
	public Double getTotal() {
		
		Double total = 0.0;
		
		for(OrderItem x : items) {
			total += x.getSubTotal();
		}
		return total;
	}
	
}
