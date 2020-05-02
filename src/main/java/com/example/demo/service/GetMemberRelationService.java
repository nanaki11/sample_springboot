package com.example.demo.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.common.ConfigReader;
import com.example.demo.entity.UserInfoBean;
import com.example.demo.entity.request.GetMemberRelationRequest;
import com.example.demo.entity.response.GetMemberRelationResponse;
import com.example.demo.entity.response.GetMemberRelationResponse.MemberRelation;

/**
 * メンバーシップ連携状態取得サービスクラス
 */
@Service
public class GetMemberRelationService {
	@Autowired
	ConfigReader configReader;

	UserInfoBean userInfoBean = new UserInfoBean();

	/**
	 * メンバーシップ連携状態取得処理
	 */
	public List<MemberRelation> getMemberRelation() {
		RestTemplate restTemplate = new RestTemplate();

		//TODO AccessTokenはBeanごとにセッションから受け取る
		//TODO lang入ったん固定値
		GetMemberRelationRequest prams = new GetMemberRelationRequest();
		prams.setClientId(configReader.getClientId());
		prams.setAccessToken(userInfoBean.getAccessToken());
		prams.setLang("ja");

		//TODO 統合dbの会員情報取得取得apiから取得
		RequestEntity<GetMemberRelationRequest> request = RequestEntity
				.post(URI.create(configReader.getGetMemberRelationUrl()))
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_FORM_URLENCODED)
				.body(prams);

		return restTemplate.exchange(request, GetMemberRelationResponse.class).getBody().getRelation();

	}
}
