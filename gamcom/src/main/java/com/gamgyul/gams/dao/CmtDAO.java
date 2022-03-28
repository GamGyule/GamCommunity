package com.gamgyul.gams.dao;

import java.util.List;

import com.gamgyul.gams.dto.CommentDTO;

public interface CmtDAO {
	public List<CommentDTO> getCmt(int board_idx);
	public void addCmt(CommentDTO cmt);
}
