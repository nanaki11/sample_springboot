package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

/**
 * 施設お気に入りのEntity．
 *
 */
@Entity
@Table(name = "facility_favorite")
@Data
@IdClass(FacilityFavoritePK.class)
public class FacilityFavorite implements Serializable {
	private static final long serialVersionUID = 1L;

	/**	業務ID */
	@Id
	@Column(name = "customer_id")
	private long customerId;

	/**	事業所CD */
	@Id
	@Column(name = "business_cd")
	private String businessCd;

}
