package com.ahad.service.vlog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
				if(email == null) {
					email = "ahad15-12484@diu.edu.bd";
				}
				vlogDao.addVlog(vlog, images, videos, email);
			}catch(Exception e) {
				System.out.println(e.fillInStackTrace());
				throw new VlogException("unknown error occured");
			}
		}
	}

	@Override
	public List<Vlog> getUserVlogs(String email) {
		Map<String, Vlog> vlogMap = vlogDao.getUserVlogs(email);
		List<Vlog> userVlogs = new ArrayList<Vlog>(vlogMap.size());
		if(vlogMap!=null) {
			for(String key : vlogMap.keySet()) {
				Vlog vlog = vlogMap.get(key);
				userVlogs.add(vlog);
				System.out.println(vlog);
				System.out.println(vlog.getImageUrl().get(0));
				System.out.println(vlog.getVideoUrl().get(0)+"\n\n\n");
			}
		}
		return userVlogs;
	}
	
}