package com.ahad.service.vlog;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ahad.model.Vlog;

public interface VlogService {

	public void addVlog(Vlog vlog, CommonsMultipartFile[] images, CommonsMultipartFile[] videos, String email);
	public List<Vlog> getUserVlogs(String email);
	public Vlog getVlog(int vlogId);
}
