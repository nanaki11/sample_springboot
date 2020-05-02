package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

/**
 * 地区マスタのEntity．
 *
 */
@Entity
@Table(name = "facility_area")
@Data
@IdClass(FacilityAreaPK.class)
public class FacilityArea implements Serializable {
	private static final long serialVersionUID = 1L;

	/**	地区CD */
	@Id
	@Column(name = "area_cd")
	private String areaCd;

	/**	言語 */
	@Id
	@Column(name = "lang")
	private String lang;

	/**	表示順 */
	@Column(name = "disp_order")
	private Integer dispOrder;

	/**	地区名称 */
	@Column(name = "district_nm")
	private String districtNm;
}