package com.example.demo.entity.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * プロフィール画像取得APIレスポンスの結果オブジェクト．
 *
 */
@Data
@JsonSerialize()
public class GetProfileimageResponse {
	/**	プロフィール画像 */
	private String image;
}
