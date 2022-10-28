package com.ahad.service.vlog;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ahad.model.Vlog;

public interface VlogService {

	public void addVlog(Vlog vlog, CommonsMultipartFile[] images, CommonsMultipartFile[] videos, String email);
}
