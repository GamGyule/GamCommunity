package com.gamgyul.gams.dao;

import java.util.List;

import com.gamgyul.gams.dto.BoardDTO;

public interface BoardDAO {
	public List<BoardDTO> getList(int page, String category);
	public int getCmtCnt(int board_idx);
	public BoardDTO getPost(int board_idx);
	public void postHit(int board_idx);
	public void writePost(BoardDTO board, String table);
	public void addVote(String board_idx);
	public String getVote(String board_idx);
	public int delPost(int board_idx, String customer_idx, int customer_admin);
	public List<String> delImg(int board_idx, String board);
	public int getNextId();
}
