/**
 *
 */
package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * 施設マスタの主キー．
 *
 */
@Data
@Embeddable
public class FacilityPK implements Serializable {

	/**	事業所CD */
	@Column(name = "business_cd")
	private String businessCd;

	/**	言語 */
	@Column(name = "lang")
	private String lang;
}
