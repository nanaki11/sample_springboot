/**
 *
 */
package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * 地区マスタの主キー．
 *
 */
@Data
@Embeddable
public class FacilityAreaPK implements Serializable {

	/**	エリアCD */
	@Column(name = "area_cd")
	private String areaCd;

	/**	言語 */
	@Column(name = "lang")
	private String lang;
}
