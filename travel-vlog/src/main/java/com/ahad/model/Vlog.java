package com.ahad.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;

public class Vlog {
	
	private int id;
	
	@NotEmpty(message = "Please enter a location")
	private String location;
	
	@NotEmpty(message = "Please write some description")
	private String description;
	
	private List<String> imageUrl;
	
	private List<String> videoUrl;
	
	private User user;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<String> getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(List<String> imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public List<String> getVideoUrl() {
		return videoUrl;
	}
	
	public void setVideoUrl(List<String> videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Vlog [id=" + id + ", location=" + location + ", description=" + description  + ", imageUrl=" + imageUrl.size() + ", videoUrl=" + videoUrl.size() + ", user=" + user.getEmail() + "]";
	}
	
	
}
