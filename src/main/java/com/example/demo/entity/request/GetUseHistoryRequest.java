package com.example.demo.entity.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * 利用履歴取得APIリクエストオブジェクト．
 *
 */
@Data
@JsonSerialize
public class GetUseHistoryRequest {

	private String clientId;
	private String accessToken;
	private String order;
	private long limit;
	private long offset;
}
