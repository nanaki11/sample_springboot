/**
 *
 */
package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * コミュニティ情報マスタのEntity．
 *
 */
@Entity
@Table(name = "community")
@Data
public class Community implements Serializable {
	private static final long serialVersionUID = 1L;

	/**	コミュニティID */
	@Id
	@Column(name = "community_id")
	private int communityId;

	/**	表示順 */
	@Column(name = "display_order")
	private int displayOrder;

	/**	コミュニティ名称 */
	@Column(name = "community_name")
	private String communityName;

	/**	表示/非表示フラグ */
	@Column(name = "is_display")
	private Boolean isDisplay;
}
