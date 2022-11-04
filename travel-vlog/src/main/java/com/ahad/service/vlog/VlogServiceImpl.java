package com.ahad.service.vlog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ahad.dao.vlog.VlogDao;
import com.ahad.exception.VlogException;
import com.ahad.model.Comment;
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
	
	@Override
	public List<Vlog> getUserVlogs(String email, String query) {
		System.out.println("get user vlog:"+email+"-----"+query);
		Map<String, Vlog> vlogMap = vlogDao.getUserVlogs(email, query);
		System.out.println("get user filter vlogs: "+vlogMap.keySet());
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

	@Override
	public Vlog getVlog(int vlogId) {
		return vlogDao.getVlog(vlogId);
	}

	@Override
	public String rateVlog(int vlogId, int rating, String email) {
		try {
			vlogDao.rateVlog(vlogId,rating,email);
			return "Your rating has been stored succesfully";
		}catch(Exception e) {
			return "You have already rate this vlog";
		}
	}

	@Override
	public Map<String, Object> getVlogRating(int vlogId) {
		Map<String,Object> ratingMap = vlogDao.getVlogRating(vlogId);
		if(ratingMap.get("avg_rating") == null) {
			ratingMap.put("avg_rating", "N/A");
		}
		return ratingMap;
	}

	@Override
	public Map<String, Object> getUserVlogRating(String email) {
		Map<String,Object> userVlogRating = vlogDao.getUserVlogRating(email);
		if(userVlogRating.get("vlog_avg_rating") == null) {
			userVlogRating.put("vlog_avg_rating", "N/A");
		}
		return userVlogRating;
	}

	@Override
	public void deleteVlog(int vlogId) {
		vlogDao.deleteVlog(vlogId);
	}

	@Override
	public String saveComment(String comment, int vlogId, String email) {
		if(vlogDao.saveComment(comment, vlogId, email)>=1) {
			return "Comment added";
		}else {
			return "comment not added";
		}
	}

	@Override
	public List<Comment> getVlogComments(int vlogId) {
		List<Comment> comments = vlogDao.getVlogComments(vlogId);
		if(comments == null)
			comments = new ArrayList<Comment>();
		return comments;
	}
	
}