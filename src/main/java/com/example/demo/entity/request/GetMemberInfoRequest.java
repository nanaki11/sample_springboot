package com.example.demo.entity.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * 会員情報取得APIリクエストオブジェクト．
 *
 */
@Data
@JsonSerialize
public class GetMemberInfoRequest {

	/**	クライアントID */
	private String clientId;

	/**	アクセストークン */
	private String accessToken;
}
