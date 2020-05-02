package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * コミュニティ付帯情報マスタの主キー．
 *
 */
@Data
@Embeddable
public class CommunityInfoPK implements Serializable {

	/**	コミュニティID */
	@Column(name = "community_id")
	private int communityId;

	/**	言語 */
	@Column(name = "lang")
	private String lang;
}
