package com.ahad.dao.vlog;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ahad.model.Vlog;

public interface VlogDao {
	public void addVlog(Vlog vlog, CommonsMultipartFile[] images, CommonsMultipartFile[] videos, String email);
}
