package com.example.demo.api;

import lombok.Data;

/**
 * 共同DBサイトにIDトークン認証API要求を行う際にレスポンスで使用するBean
 *
 */
@Data
public class ResIdTokenBean {

	/** 共立ID */
	private String kyoritsuId;

	/** アクセストークン */
	private String accessToken;

	/** nonce */
	private String nonce;

}
