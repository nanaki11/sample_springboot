/**
 *
 */
package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * 施設お気に入りの主キー．
 *
 */
@Data
@Embeddable
public class FacilityFavoritePK implements Serializable {

	/**	業務ID */
	@Column(name = "customer_id")
	private long customerId;

	/**	事業所CD */
	@Column(name = "business_cd")
	private String businessCd;
}
