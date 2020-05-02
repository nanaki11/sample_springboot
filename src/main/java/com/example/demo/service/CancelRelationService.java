package com.example.demo.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.common.ConfigReader;
import com.example.demo.entity.UserInfoBean;
import com.example.demo.entity.request.CancelRelationRequest;
import com.example.demo.entity.response.ApiResponse;

/**
 * 利用履歴画面サービスクラス
 */
@Service
public class CancelRelationService {
	@Autowired
	ConfigReader configReader;

	UserInfoBean userInfoBean = new UserInfoBean();

	public ApiResponse cancelRelation(long relationId) {
		RestTemplate restTemplate = new RestTemplate();

		//TODO AccessTokenはBeanごとにセッションから受け取る
		CancelRelationRequest prams = new CancelRelationRequest();
		prams.setClientId(configReader.getClientId());
		prams.setAccessToken(userInfoBean.getAccessToken());
		prams.setRelationId(relationId);

		//TODO 統合dbの利用履歴取得apiから取得
		RequestEntity<CancelRelationRequest> request = RequestEntity
				.post(URI.create(configReader.getUseHistoryUrl()))
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_FORM_URLENCODED)
				.body(prams);

		return new ApiResponse(restTemplate.exchange(request, ApiResponse.class).getBody());

	}

}
