package com.ahad.service.vlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ahad.dao.vlog.VlogDao;
import com.ahad.exception.VlogException;
import com.ahad.model.Vlog;


@Service
public class VlogServiceImpl implements VlogService{
	@Autowired
	private VlogDao vlogDao;
	
	@Override
	public void addVlog(Vlog vlog, CommonsMultipartFile[] images, CommonsMultipartFile[] videos, String email) {
		if((images.length==1 && images[0].getOriginalFilename().isEmpty()) || images.length == 0 ) {
			throw new VlogException("no image has been selected");
		}else if( (videos.length==1 && videos[0].getOriginalFilename().isEmpty()) || videos.length == 0 ) {
			throw new VlogException("no video has been selected");
		}
		else {
			try {
				vlogDao.addVlog(vlog, images, videos, email);
			}catch(Exception e) {
				System.out.println(e.fillInStackTrace());
				throw new VlogException("unknown error occured");
			}
		}
	}
	
}
