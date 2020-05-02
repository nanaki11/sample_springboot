package com.example.demo.entity.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * コンテンツ情報登録APIリクエストオブジェクト．
 *
 */
@Data
@JsonSerialize
public class RegisterContentRequest {

	/**	クライアントID */
	private String clientId;

	/**	言語コード */
	private String lang;

	/**	コンテンツID */
	private String contentId;

	/**	コンテンツ種別 */
	private String type;

	/**	コミュニティID */
	private String communityId;

	/**	コンテンツタイトル */
	private String title;

	/**	コンテンツ内容 */
	private String contents;

	/**	コンテンツ画像 */
	private String image;

	/**	配信開始日 */
	private String deliveryStartDate;

	/**	配信終了日 */
	private String deliveryEndDate;
}
