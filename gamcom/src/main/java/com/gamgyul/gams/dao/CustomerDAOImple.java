package com.gamgyul.gams.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.gamgyul.gams.dto.CustomerDTO;

@Repository
public class CustomerDAOImple implements CustomerDAO {
	
	@Inject
	SqlSession sqlSession;

	public int getCustCnt(String customer_idx, String customer_pb) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("customer_idx", customer_idx);
		map.put("customer_pb", customer_pb);
		return sqlSession.selectOne("customer.getCustCnt", map);
	}

	public int addCust(CustomerDTO customer) {
		// TODO Auto-generated method stub
		return sqlSession.insert("customer.addCust", customer);
	}

}
