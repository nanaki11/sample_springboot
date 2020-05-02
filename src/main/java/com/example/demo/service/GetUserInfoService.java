package com.example.demo.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.example.demo.api.ReqGetUserInfo;
import com.example.demo.api.ResGetUserInfo;
import com.example.demo.entity.UserInfoBean;
import com.example.demo.repository.GetUserInfoRepository;

public class GetUserInfoService {

	/**
	 * 会員情報取得
	 * @param bean
	 * @return
	 */
	public UserInfoBean getUserInfo(ReqGetUserInfo bean) {

		// request処理
		GetUserInfoRepository repository = new GetUserInfoRepository();
		String ret = repository.get(bean);

		return responseAnalyze("");

	}

	/**
	 * 会員情報取得本文解析
	 * @param body
	 * @return
	 */
	private ResGetUserInfo responseAnalyze(String body) {

		return new ResGetUserInfo();
	}

	private static int TOKEN_LENGTH = 16;

	public String getRandom() {

		byte token[] = new byte[TOKEN_LENGTH];
		StringBuffer sb = new StringBuffer();
		SecureRandom sr = null;
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
			sr.nextBytes(token);
			for (int i = 0; i < token.length; i++) {
				sb.append(String.format("%02x", token[i]));
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return sb.toString();

	}

}
