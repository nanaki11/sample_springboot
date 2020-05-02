package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

/**
 * 施設マスタのEntity．
 *
 */
@Entity
@Table(name = "facility")
@Data
@IdClass(FacilityPK.class)
public class Facility implements Serializable {
	private static final long serialVersionUID = 1L;

	/**	事業所CD */
	@Id
	@Column(name = "business_cd")
	private String businessCd;

	/**	言語 */
	@Id
	@Column(name = "lang")
	private String lang;

	/**	施設名称 */
	@Column(name = "facility_name")
	private String facilityName;

	/**	カテゴリCD */
	@Column(name = "category_cd")
	private String categoryCd;

	/**	地区CD */
	@Column(name = "area_cd")
	private String areaCd;

	/**	都道府県CD */
	@Column(name = "prefectures_cd")
	private String prefecturesCd;

	/**	都市名 */
	@Column(name = "city_name")
	private String cityName;

	/**	施設郵便番号 */
	@Column(name = "postal_code")
	private String postalCode;

	/**	施設住所 */
	@Column(name = "address")
	private String address;

	/**	施設予約電話番号 */
	@Column(name = "reservation_phone")
	private String reservationPhone;

	/**	施設電話番号 */
	@Column(name = "phone")
	private String phone;

	/**	施設メールアドレス */
	@Column(name = "mail_address")
	private String mailAddress;

	/**	ホテルサイトURL */
	@Column(name = "detailed_url")
	private String detailedUrl;

	/**	予約サイトURL */
	@Column(name = "reservationUrl")
	private String reservation_url;

	/**	ネットショップURL */
	@Column(name = "shop_url")
	private String shopUrl;

	/**	施設ロゴ画像URL */
	@Column(name = "facility_logo_image_url")
	private String facilityLogoImageUrl;

	/**	施設画像URL１ */
	@Column(name = "facility_image_url_1")
	private String facilityImageUrl1;

	/**	施設画像URL２ */
	@Column(name = "facility_image_url_2")
	private String facilityImageUrl2;

	/**	施設画像URL３ */
	@Column(name = "facility_image_url_3")
	private String facilityImageUrl3;

	/**	施設画像URL４ */
	@Column(name = "facility_image_url_4")
	private String facilityImageUrl4;

	/**	施設画像URL５ */
	@Column(name = "facility_image_url_5")
	private String facilityImageUrl5;

	/**	施設画像URL６ */
	@Column(name = "facility_image_url_6")
	private String facilityImageUrl6;

	/**	施設画像URL７ */
	@Column(name = "facility_image_url_7")
	private String facilityImageUrl7;

	/**	施設画像URL８ */
	@Column(name = "facility_image_url_8")
	private String facilityImageUrl8;

	/**	施設画像URL９ */
	@Column(name = "facility_image_url_9")
	private String facilityImageUrl9;

	/**	施設画像URL１０ */
	@Column(name = "facility_image_url_10")
	private String facilityImageUrl10;

	/**	施設画像URL１１ */
	@Column(name = "facility_image_url_11")
	private String facilityImageUrl11;

	/**	施設画像URL１２ */
	@Column(name = "facility_image_url_12")
	private String facilityImageUrl12;

	/**	施設画像URL１３ */
	@Column(name = "facility_image_url_13")
	private String facilityImageUrl13;

	/**	施設画像URL１４ */
	@Column(name = "facility_image_url_14")
	private String facilityImageUrl14;

	/**	施設画像URL１５ */
	@Column(name = "facility_image_url_15")
	private String facilityImageUrl15;

	/**	施設画像タイトル１ */
	@Column(name = "facility_image_title_1")
	private String facilityImageTitle1;

	/**	施設画像タイトル２ */
	@Column(name = "facility_image_title_2")
	private String facilityImageTitle2;

	/**	施設画像タイトル３ */
	@Column(name = "facility_image_title_3")
	private String facilityImageTitle3;

	/**	施設画像タイトル４ */
	@Column(name = "facility_image_title_4")
	private String facilityImageTitle4;

	/**	施設画像タイトル５ */
	@Column(name = "facility_image_title_5")
	private String facilityImageTitle5;

	/**	施設画像タイトル６ */
	@Column(name = "facility_image_title_6")
	private String facilityImageTitle6;

	/**	施設画像タイトル７ */
	@Column(name = "facility_image_title_7")
	private String facilityImageTitle7;

	/**	施設画像タイトル８ */
	@Column(name = "facility_image_title_8")
	private String facilityImageTitle8;

	/**	施設画像タイトル９ */
	@Column(name = "facility_image_title_9")
	private String facilityImageTitle9;

	/**	施設画像タイトル１０ */
	@Column(name = "facility_image_title_10")
	private String facilityImageTitle10;

	/**	施設画像タイトル１１ */
	@Column(name = "facility_image_title_11")
	private String facilityImageTitle11;

	/**	施設画像タイトル１２ */
	@Column(name = "facility_image_title_12")
	private String facilityImageTitle12;

	/**	施設画像タイトル１３ */
	@Column(name = "facility_image_title_13")
	private String facilityImageTitle13;

	/**	施設画像タイトル１４ */
	@Column(name = "facility_image_title_14")
	private String facilityImageTitle14;

	/**	施設画像タイトル１５ */
	@Column(name = "facility_image_title_15")
	private String facilityImageTitle15;

	/**	緯度 */
	@Column(name = "latitude")
	private float latitude;

	/**	経度 */
	@Column(name = "longitude")
	private float longitude;
}
