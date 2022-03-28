package com.gamgyul.gams.dto;

public class CustomerDTO {
	private String customer_idx;
	private String customer_email;
	private String customer_url;
	private String customer_name;
	private String customer_pb;
	private int customer_admin;
	
	
	
	
	public int getCustomer_admin() {
		return customer_admin;
	}
	public void setCustomer_admin(int customer_admin) {
		this.customer_admin = customer_admin;
	}
	public String getCustomer_pb() {
		return customer_pb;
	}
	public void setCustomer_pb(String customer_pb) {
		this.customer_pb = customer_pb;
	}
	public String getCustomer_idx() {
		return customer_idx;
	}
	public void setCustomer_idx(String customer_idx) {
		this.customer_idx = customer_idx;
	}
	public String getCustomer_email() {
		return customer_email;
	}
	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}
	public String getCustomer_url() {
		return customer_url;
	}
	public void setCustomer_url(String customer_url) {
		this.customer_url = customer_url;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	
	
	@Override
	public String toString() {
		return "CustomerDTO [customer_idx=" + customer_idx + ", customer_email=" + customer_email + ", customer_url="
				+ customer_url + ", customer_name=" + customer_name + ", customer_pb=" + customer_pb
				+ ", customer_admin=" + customer_admin + "]";
	}


	
	
	
	
}
