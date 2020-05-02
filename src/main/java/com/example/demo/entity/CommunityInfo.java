/**
 *
 */
package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

/**
 * コミュニティ付帯情報マスタのEntity．
 *
 */
@Entity
@Table(name = "community_info")
@Data
@IdClass(CommunityInfoPK.class)
public class CommunityInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**	コミュニティID */
	@Id
	@Column(name = "community_id")
	private int communityId;

	/**	言語 */
	@Id
	@Column(name = "lang")
	private String lang;

	/**	コミュニティ名称 */
	@Column(name = "community_name")
	private String communityName;

	/**	スタッフフラグ */
	@Column(name = "is_staff")
	private int isStaff;

	/**	癒湯自適フラグ */
	@Column(name = "is_yuyu")
	private int isYuyu;

	/**	表示対象年齢上限 */
	@Column(name = "age_upper_limit")
	private int ageUpperLimit;

	/**	表示対象年齢下限 */
	@Column(name = "age_lower_limit")
	private int ageLowerLimit;

	/**	コミュニティ内容 */
	@Column(name = "coment")
	private String coment;

	/**	コミュニティ画像URL */
	@Column(name = "community_image")
	private String communityImage;

	/**	外部サイトURL */
	@Column(name = "external_link")
	private String externalLink;
}
