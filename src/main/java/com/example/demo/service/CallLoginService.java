package com.example.demo.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.example.demo.api.ReqIdTokenBean;
import com.example.demo.api.ResIdTokenBean;
import com.example.demo.repository.CheckIdTokenRepository;

public class CallLoginService {

	/**
	 * IDトークン検証
	 * @param bean
	 * @return
	 */
	public ResIdTokenBean checkIdToken(ReqIdTokenBean bean) {

		// リクエスト処理
		CheckIdTokenRepository repository = new CheckIdTokenRepository();
		repository.get(bean);
		return responseAnalyze("");

	}

	/**
	 * レスポンス本文解析.
	 * @param body
	 * @return
	 */
	private ResIdTokenBean responseAnalyze(String body) {

		return new ResIdTokenBean();
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
