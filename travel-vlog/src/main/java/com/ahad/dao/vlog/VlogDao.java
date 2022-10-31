package com.ahad.dao.vlog;

import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ahad.model.Vlog;

public interface VlogDao {
	public void addVlog(Vlog vlog, CommonsMultipartFile[] images, CommonsMultipartFile[] videos, String email);

	public Map<String, Vlog> getUserVlogs(String email);

	public Vlog getVlog(int vlogId);

	public int rateVlog(int vlogId, int rating, String email);

	public Map<String, Object> getVlogRating(int vlogId);
}
