package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 * コンテンツマスタのEntity．
 *
 */
@Entity
@Table(name = "content")
@Data
@IdClass(ContentPK.class)
public class Content implements Serializable {
	private static final long serialVersionUID = 1L;

	/**	コンテンツID */
	@Id
	@Column(name = "content_id")
	private long contentId;

	/**	言語 */
	@Id
	@Column(name = "lang")
	private String lang;

	/**	コンテンツ種別 */
	@Column(name = "content_type")
	private String contentType;

	/**	カテゴリID */
	@Column(name = "category_id")
	private int categoryId;

	/**	コンテンツタイトル */
	@Column(name = "content_title")
	private String contentTitle;

	/**	コンテンツ内容 */
	private String content;

	/**	コンテンツ画像URL */
	@Column(name = "content_image_url")
	private String contentImageUrl;

	/**	配信開始日 */
	@Column(name = "delivery_start_date")
	private Date deliveryStartDate;

	/**	配信終了日 */
	@Column(name = "delivery_end_date")
	private Date deliveryEndDate;

	/**	表示/非表示フラグ */
	@Column(name = "is_display")
	private Boolean isDisplay;

	@OneToMany
	@JoinColumns({
			@JoinColumn(name = "content_id", referencedColumnName = "content_id", insertable = false, updatable = false),
			@JoinColumn(name = "lang", referencedColumnName = "lang", insertable = false, updatable = false)
	})
	private List<ContentFavorite> contentFavorites;

}
