package com.example.demo.entity.response;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 会員コミュニティ一覧取得APIレスポンスの結果オブジェクト．
 *
 */
@Data
@JsonSerialize
public class GetCommunityResponse {
	/**	コミュニティ情報リスト */
	private List<Community> community;

	/**
	 * コミュニティ情報オブジェクト．
	 */
	@Data
	@AllArgsConstructor
	public class Community {
		/**	コミュニティID */
		private int id;
		/**	コミュニティ名称 */
		private String name;
		/**	コミュニティ内容 */
		private String content;
		/**	コミュニティ画像 */
		private String image;
		/**	外部URL */
		private String externalUrl;
	}
}
