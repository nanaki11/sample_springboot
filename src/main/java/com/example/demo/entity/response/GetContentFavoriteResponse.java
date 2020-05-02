package com.example.demo.entity.response;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * コンテンツお気に入り取得APIレスポンスの結果オブジェクト．
 *
 */
@Data
@JsonSerialize
public class GetContentFavoriteResponse {
	/**	お気に入り情報リスト */
	private List<Favorite> favorite;

	/**
	 * お気に入り情報オブジェクト．
	 */
	@Data
	@AllArgsConstructor
	public class Favorite {
		/**	コンテンツID */
		private long contentId;

	}
}
