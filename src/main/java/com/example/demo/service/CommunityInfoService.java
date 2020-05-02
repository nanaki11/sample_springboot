package com.example.demo.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CommunityInfo;
import com.example.demo.repository.CommunityInfoRepository;

@Service
public class CommunityInfoService {

	@Autowired
	private CommunityInfoRepository repository;

	public List<CommunityInfo> findAll() {
		return repository.findAll();
	}

	public List<CommunityInfo> orderByCommunityId(String lang, int isStaff, int age) {
		if (StringUtils.isEmpty(lang)) {
			lang = "ja";
		}

		return repository.orderByCommunityId(lang, isStaff, age);
	}
}
