package com.gamgyul.gams.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.gamgyul.gams.dto.BoardDTO;

@Repository
public class BoardDAOImple implements BoardDAO {
	
	@Inject
	SqlSession sqlSession;

	public List<BoardDTO> getList(int page, String category) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		page = (page-1)*20;
		map.put("page", page);
		map.put("board_category", category);
		return sqlSession.selectList("board.getList",map);
	}

	public int getCmtCnt(int board_idx) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.getCmtCnt", board_idx);
	}

	public BoardDTO getPost(int board_idx) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.getPost",board_idx);
	}

	public void postHit(int board_idx) {
		// TODO Auto-generated method stub
		sqlSession.update("board.postHit", board_idx);
	}

	public void writePost(BoardDTO board, String table) {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("board", table);
		
		map.put("board_subject", board.getBoard_subject());
		map.put("board_content", board.getBoard_content());
		map.put("customer_idx", board.getCustomer_idx());
		map.put("board_user", board.getBoard_user());
		map.put("board_date", "now()");
		map.put("board_hit", "0");
		map.put("board_vote", "0");
		
		sqlSession.insert("board.writePost", map);
		
	}

	public void addVote(String board_idx) {
		// TODO Auto-generated method stub	
		sqlSession.update("board.addVote",board_idx);
	}

	public String getVote(String board_idx) {
		// TODO Auto-generated method stub		
		return sqlSession.selectOne("board.getVote", board_idx);
	}

	public int delPost(int board_idx, String customer_idx, int customer_admin) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("board_idx", board_idx);
		map.put("customer_idx", customer_idx);
		map.put("customer_admin", customer_admin);
		return sqlSession.delete("board.delPost",map);
	}

	public List<String> delImg(int board_idx, String board) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("board", board);
		map.put("board_idx", board_idx);
		
		return null;
		
	}

	public int getNextId() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board.getNextAI");
	}

}
