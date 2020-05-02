package com.example.demo.entity.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * コンテンツレビューコメント取得APIレスポンスの結果オブジェクト．
 *
 */
@Data
@JsonSerialize
public class GetReviewResponse {
	/**	レビュー情報リスト */
	private List<Review> review;

	/**
	 * レビュー情報オブジェクト．
	 */
	@Data
	@AllArgsConstructor
	public class Review {
		/**	レビューID */
		private long id;
		/**	ニックネーム */
		private String nickname;
		/**	コメント内容 */
		private String coment;
		/**	コメント年月日 */
		@JsonFormat(pattern = "yyyy/MM/dd")
		private Date postalCd;
	}
}
