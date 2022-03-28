package com.gamgyul.gams.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.gamgyul.gams.dto.CommentDTO;

@Repository
public class CmtDAOImple implements CmtDAO {

	@Inject
	SqlSession sqlSession;
	
	public List<CommentDTO> getCmt(int board_idx) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("comment.getCmt",board_idx);
	}

	public void addCmt(CommentDTO cmt) {
		// TODO Auto-generated method stub
		System.out.println(cmt.getBoard_idx());
		System.out.println(cmt.getCustomer_idx());
		sqlSession.insert("comment.addCmt",cmt);
	}

}
