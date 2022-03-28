package com.gamgyul.gams.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardDTO {
	private int board_idx;
	private String board_subject;
	private String board_content;
	private String customer_idx;
	private String board_user;
	private Date board_date;
	private int board_hit;
	private int board_vote;
	private String board_category;
	
	SimpleDateFormat kor_format = new SimpleDateFormat("yyyy년 M월 d일");
	SimpleDateFormat origin_format = new SimpleDateFormat("yyyy-MM-dd");
	
	public BoardDTO() {}

	public BoardDTO(int board_idx, String board_subject, String board_content, String customer_idx, String board_user,
			Date board_date, int board_hit, int board_vote, String board_category) {
		super();
		this.board_idx = board_idx;
		this.board_subject = board_subject;
		this.board_content = board_content;
		this.customer_idx = customer_idx;
		this.board_user = board_user;
		this.board_date = board_date;
		this.board_hit = board_hit;
		this.board_vote = board_vote;
		this.board_category = board_category;
	}

	public String getCustomer_idx() {
		return customer_idx;
	}
	public void setCustomer_idx(String customer_idx) {
		this.customer_idx = customer_idx;
	}
	public int getBoard_idx() {
		return board_idx;
	}
	public void setBoard_idx(int board_id) {
		this.board_idx = board_id;
	}
	public String getBoard_subject() {
		return board_subject;
	}
	public void setBoard_subject(String board_subject) {
		this.board_subject = board_subject;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public String getBoard_user() {
		return board_user;
	}
	public void setBoard_user(String board_user) {
		this.board_user = board_user;
	}
	public String getBoard_date() {
		return origin_format.format(board_date);
	}
	public void setBoard_date(Date board_date) {
		this.board_date = board_date;
	}
	public int getBoard_hit() {
		return board_hit;
	}
	public void setBoard_hit(int board_hit) {
		this.board_hit = board_hit;
	}
	public int getBoard_vote() {
		return board_vote;
	}
	public void setBoard_vote(int board_vote) {
		this.board_vote = board_vote;
	}
	
	public String getBoard_category() {
		return board_category;
	}

	public void setBoard_category(String board_category) {
		this.board_category = board_category;
	}


	@Override
	public String toString() {
		return "BoardDTO [board_idx=" + board_idx + ", board_subject=" + board_subject + ", board_content="
				+ board_content + ", customer_idx=" + customer_idx + ", board_user=" + board_user + ", board_date="
				+ board_date + ", board_hit=" + board_hit + ", board_vote=" + board_vote + ", board_category="
				+ board_category + "]";
	}
	
}
