package com.ahad.service.vlog;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ahad.model.Comment;
import com.ahad.model.Vlog;

public interface VlogService {

	public void addVlog(Vlog vlog, CommonsMultipartFile[] images, CommonsMultipartFile[] videos, String email);
	public List<Vlog> getAllVlogs(int pageNo);
	public List<Vlog> getUserVlogs(String email);
	public List<Vlog> getUserVlogs(String email, String query);
	public Vlog getVlog(int vlogId);
	public String rateVlog(int vlogId, int rating, String email);
	public Map<String, Object> getVlogRating(int vlogId);
	public Map<String,Object> getUserVlogRating(String email);
	public void deleteVlog(int vlogId);
	public String saveComment(String comment, int vlogId, String email);
	public List<Comment> getVlogComments(int vlogId);
}
