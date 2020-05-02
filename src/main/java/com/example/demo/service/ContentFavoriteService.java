package com.example.demo.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ContentFavorite;
import com.example.demo.repository.ContentFavoriteRepository;

@Service
public class ContentFavoriteService {

	@Autowired
	private ContentFavoriteRepository repository;

	public List<ContentFavorite> findByCustomerId(long customerId) {
		return repository.findByCustomerId(customerId);
	}

	public ContentFavorite save(ContentFavorite entity) {
		if(StringUtils.isEmpty(entity.getLang())) {
			entity.setLang("ja");
		}

		return repository.save(entity);
	}

	public void delete(ContentFavorite entity) {
		repository.delete(entity);
	}
}
