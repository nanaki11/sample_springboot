package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * コンテンツレビューのEntity．
 *
 */
@Entity
@Table(name = "review")
@Data
public class Review implements Serializable {
	private static final long serialVersionUID = 1L;

	/**	レビューID */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private long reviewId;

	/**	コンテンツID */
	@Column(name = "content_id")
	private long contentId;

	/**	言語 */
	@Column(name = "lang")
	private String lang;

	/**	作成日時 */
	@Column(name = "add_date")
	private Date addDate;

	/**	ニックネーム */
	@Column(name = "nickname")
	private String nickname;

	/**	コメント内容 */
	@Column(name = "coment")
	private String coment;

	/**	表示/非表示フラグ */
	@Column(name = "is_display")
	private boolean isDisplay;

	/**	掲載不可日 */
	@Column(name = "not_posted_date")
	private Date notPostedDate;

	/**	プロフィール画像 */
	@Column(name = "profile_url")
	private String profileUrl;
}
