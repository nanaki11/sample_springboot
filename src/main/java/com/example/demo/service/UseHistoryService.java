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
import com.example.demo.entity.request.GetUseHistoryRequest;
import com.example.demo.entity.response.GetUseHistoryResponse;
import com.example.demo.entity.response.GetUseHistoryResponse.UseHistory;

/**
 * 利用履歴画面サービスクラス
 */
@Service
public class UseHistoryService {
	@Autowired
	ConfigReader configReader;

	UserInfoBean userInfoBean = new UserInfoBean();

	public List<UseHistory> getUseHistory() {
		RestTemplate restTemplate = new RestTemplate();

		//TODO AccessTokenはBeanごとにセッションから受け取る
		GetUseHistoryRequest prams = new GetUseHistoryRequest();
		prams.setClientId(configReader.getClientId());
		prams.setAccessToken(userInfoBean.getAccessToken());

		//TODO 統合dbの利用履歴取得apiから取得
		RequestEntity<GetUseHistoryRequest> request = RequestEntity
				.post(URI.create(configReader.getUseHistoryUrl()))
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_FORM_URLENCODED)
				.body(prams);

		return restTemplate.exchange(request, GetUseHistoryResponse.class).getBody().getUseHistorys();

	}

}
