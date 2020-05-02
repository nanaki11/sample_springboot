package com.example.demo.entity.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * メンバーシップ連携解除APIリクエストオブジェクト．
 *
 */
@Data
@JsonSerialize
public class CancelRelationRequest {

	private String clientId;
	private String accessToken;
	private long relationId;

}
