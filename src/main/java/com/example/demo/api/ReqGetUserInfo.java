package com.example.demo.api;

import lombok.Data;

/**
 * 会員情報取得用request bean
 *
 */
@Data
public class ReqGetUserInfo {

	/** クライアントID */
	String clientId;

	/** アクセストークン */
	String accessToken;

}
