package com.gamgyul.gams.dao;

import com.gamgyul.gams.dto.CustomerDTO;

public interface CustomerDAO {
	public int getCustCnt(String customer_idx, String customer_pb);
	public int addCust(CustomerDTO customer);
}
