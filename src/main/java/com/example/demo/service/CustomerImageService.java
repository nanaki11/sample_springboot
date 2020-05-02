package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CustomerImage;
import com.example.demo.entity.response.GetMemberRelationResponse.MemberRelation;
import com.example.demo.repository.CustomerImageRepository;

@Service
public class CustomerImageService {

	@Autowired
	private CustomerImageRepository repository;

	public CustomerImage findByCustomerId(long customerId) {
		return repository.findByCustomerId(customerId);
	}

	/**
	 * ファミリーメンバーズ連携イメージ取得
	 * @param メンバー連携リスト
	 * @return メンバー連携イメージマップ
	 */
	public Map<Long, String> getRelationImageMap(List<MemberRelation> memberRelationList) {
		Map<Long, String> relationImageMap = new HashMap<Long, String>();
		for (MemberRelation memberRelation : memberRelationList) {
			relationImageMap.put(memberRelation.getCustomerId(),
					repository.findByCustomerId(memberRelation.getCustomerId()).getImageUrl());
		}
		return relationImageMap;

	}
}
