/**
 *
 */
package com.example.demo.form;

import lombok.Data;

/**
 * コミュニティ一覧画面のForm．
 *
 */
@Data
public class CommunityListForm {

	/**	コミュニティID */
	private int communityId;

	/**	コミュニティ名称 */
	private String communityName;

	/**	コミュニティ内容 */
	private String coment;

	/**	コミュニティ画像URL */
	private String communityImage;

	/**	外部サイトURL */
	private String externalLink;

	/**	参加判定 */
	private boolean isJoined;
}
