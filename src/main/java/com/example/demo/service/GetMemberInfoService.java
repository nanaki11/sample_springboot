package com.example.demo.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.common.ConfigReader;
import com.example.demo.entity.UserInfoBean;
import com.example.demo.entity.request.GetMemberInfoRequest;
import com.example.demo.entity.response.GetMemberInfoResponse;

/**
 * 会員情報取得サービスクラス
 */
@Service
public class GetMemberInfoService {
	@Autowired
	ConfigReader configReader;

	UserInfoBean userInfoBean = new UserInfoBean();

	/**
	 * 会員情報取得処理
	 */
	public GetMemberInfoResponse getMemberInfo() {
		RestTemplate restTemplate = new RestTemplate();

		//TODO AccessTokenはBeanごとにセッションから受け取る
		GetMemberInfoRequest prams = new GetMemberInfoRequest();
		prams.setClientId(configReader.getClientId());
		prams.setAccessToken(userInfoBean.getAccessToken());

		//TODO 統合dbの会員情報取得取得apiから取得
		RequestEntity<GetMemberInfoRequest> request = RequestEntity
				.post(URI.create(configReader.getGetMemberInfoUrl()))
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_FORM_URLENCODED)
				.body(prams);

		return restTemplate.exchange(request, GetMemberInfoResponse.class).getBody();

	}
}
