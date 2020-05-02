package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Content;
import com.example.demo.entity.ContentPK;
import com.example.demo.repository.ContentRepository;

@Service
public class ContentService {

	// 日付フォーマット
	public static final String DATE_FORMAT = "yyyy/MM/dd";

	@Autowired
	private ContentRepository repository;

	public List<Content> findAll() {
		return repository.findAll();
	}

	public Page<Content> findAll(PageRequest of) {
		return repository.findAll(of);
	}

	public Optional<Content> findById(long contentId, String lang) {
		if (StringUtils.isEmpty(lang)) {
			lang = "ja";
		}
		ContentPK pk = new ContentPK();
		pk.setContentId(contentId);
		pk.setLang(lang);

		return repository.findById(pk);
	}

	public Content save(Content entity) {

		Date sysDate = new Date();
		if (null == entity.getDeliveryStartDate()) {
			entity.setDeliveryStartDate(sysDate);
		}
		if (null == entity.getDeliveryEndDate()) {
			entity.setDeliveryEndDate(sysDate);
		}

		return repository.save(entity);
	}

	public Content update(Content entity) {
		return repository.save(entity);
	}

	public void delete(Content entity) {
		if (StringUtils.isEmpty(entity.getLang())) {
			entity.setLang("ja");
		}

		repository.delete(entity);
	}

	public List<Content> findByContentType(String contentType) {
		return repository.findByContentType(contentType);
	}

	public List<Content> findExceptByContentType(String contentType) {
		return repository.findExceptByContentType(contentType);
	}

	public List<Content> findJoinFavorite(long customerId) {
		return repository.findJoinFavorite(customerId);
	}

}
