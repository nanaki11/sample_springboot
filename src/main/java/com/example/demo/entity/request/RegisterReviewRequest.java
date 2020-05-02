package com.example.demo.entity.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * コンテンツレビューコメント登録APIリクエストオブジェクト．
 *
 */
@Data
@JsonSerialize
public class RegisterReviewRequest {

	/**	クライアントID */
	private String clientId;

	/**	コンテンツID */
	private String contentId;

	/**	コンテンツレビュー情報 */
	private Review review;

	/**
	 *  コンテンツレビュー情報オブジェクト．
	 */
	@Data
	public class Review {
		/**	ニックネーム */
		private String nickname;
		/**	コメント内容 */
		private String coment;
	}
}
