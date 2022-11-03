package com.ahad.model;

public class Comment {

	private int id;
	private String comment;
	private String commenterName;
	private String commenterImage;
	private Long time;

	public Comment(int id, String comment, String commenterName, String commenterImage, Long time) {
		super();
		this.id = id;
		this.comment = comment;
		this.commenterName = commenterName;
		this.commenterImage = commenterImage;
		this.time = time;
	}

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommenterName() {
		return commenterName;
	}

	public void setCommenterName(String commenterName) {
		this.commenterName = commenterName;
	}

	public String getCommenterImage() {
		return commenterImage;
	}

	public void setCommenterImage(String commenterImage) {
		this.commenterImage = commenterImage;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", comment=" + comment + ", commenterName=" + commenterName + ", commenterImage="
				+ commenterImage + ", time=" + time + "]";
	}

}
