package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.example.demo.entity.Review;
import com.example.demo.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository repository;

	public List<Review> findByContentId(long contentId) {
		return repository.findByContentId(contentId);
	}

	public Review save(Review entity) {
		if(StringUtils.isEmpty(entity.getLang())) {
			entity.setLang("ja");
		}

		if(null == entity.getAddDate()) {
			entity.setAddDate(new Date());
		}

		return repository.save(entity);
	}
}
