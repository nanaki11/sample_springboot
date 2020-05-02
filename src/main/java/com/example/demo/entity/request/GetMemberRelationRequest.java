package com.example.demo.entity.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * メンバーシップ連携状態取得APIリクエストオブジェクト．
 *
 */
@Data
@JsonSerialize
public class GetMemberRelationRequest {

	/**	クライアントID */
	private String clientId;

	/**	アクセストークン */
	private String accessToken;

	/**	言語コード */
	private String lang;
}
