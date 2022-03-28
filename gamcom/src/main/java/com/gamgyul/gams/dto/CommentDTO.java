package com.gamgyul.gams.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.gamgyul.gams.util.TIME_MAXIMUM;

public class CommentDTO {
	private int cmt_idx;
	private int board_idx;
	private String customer_idx;
	private String customer_name;
	private String customer_url;
	private String cmt_contents;
	private Date cmt_date;
	private int cmt_vote;
	
	SimpleDateFormat kor_format = new SimpleDateFormat("yyyy년 M월 d일 E");
	SimpleDateFormat origin_format = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public CommentDTO() {}
	public CommentDTO(int cmt_idx, int board_idx, String customer_idx, String customer_name, String customer_url,
			String cmt_contents, Date cmt_date, int cmt_vote) {
		super();
		this.cmt_idx = cmt_idx;
		this.board_idx = board_idx;
		this.customer_idx = customer_idx;
		this.customer_name = customer_name;
		this.customer_url = customer_url;
		this.cmt_contents = cmt_contents;
		this.cmt_date = cmt_date;
		this.cmt_vote = cmt_vote;
	}
	public int getCmt_idx() {
		return cmt_idx;
	}
	public void setCmt_idx(int cmt_idx) {
		this.cmt_idx = cmt_idx;
	}
	public int getBoard_idx() {
		return board_idx;
	}
	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}
	public String getCustomer_idx() {
		return customer_idx;
	}
	public void setCustomer_idx(String customer_idx) {
		this.customer_idx = customer_idx;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCustomer_url() {
		return customer_url;
	}
	public void setCustomer_url(String customer_url) {
		this.customer_url = customer_url;
	}
	public String getCmt_contents() {
		return cmt_contents;
	}
	public void setCmt_contents(String cmt_contents) {
		this.cmt_contents = cmt_contents;
	}
	public String getCmt_date() {
		
		return TIME_MAXIMUM.ConvertTime(cmt_date);
	}
	public void setCmt_date(Date cmt_date) {
		this.cmt_date = cmt_date;
	}
	public int getCmt_vote() {
		return cmt_vote;
	}
	public void setCmt_vote(int cmt_vote) {
		this.cmt_vote = cmt_vote;
	}
	
	@Override
	public String toString() {
		return cmt_idx + "!@##@!" + board_idx + "!@##@!" + customer_idx + "!@##@!" + customer_name + "!@##@!" + customer_url + "!@##@!"+ cmt_contents + "!@##@!" + getCmt_date() + "!@##@!" + cmt_vote;
	}
	
	
	
	
	
}
