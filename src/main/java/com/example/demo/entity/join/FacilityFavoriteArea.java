package com.example.demo.entity.join;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacilityFavoriteArea {

	@Id
	/**	事業所CD */
	private String business_cd;

	/*	*//**	言語 */
	/*
	
	private String lang;
	
	*//**	施設名称 */
	/*
	
	private String facility_Name;
	
	*//**	カテゴリCD */
	/*
	
	private String category_Cd;
	
	*//**	地区CD */
	/*
	
	private String area_Cd;
	
	*//**	都道府県CD */
	/*
	
	private String prefectures_CCd;
	
	*//**	都市名 */
	/*
	
	private String city_Name;
	
	*//**	施設郵便番号 */
	/*
	
	private String postal_Code;
	
	*//**	施設住所 */
	/*
	
	private String address;
	
	*//**	施設予約電話番号 */
	/*
	
	private String reservation_Phone;
	
	*//**	施設電話番号 */
	/*
	
	private String phone;
	
	*//**	施設メールアドレス */
	/*
	
	private String mail_Address;
	
	*//**	ホテルサイトURL */
	/*
	
	private String detailed_Url;
	
	*//**	予約サイトURL */
	/*
	
	private String reservation_url;
	
	*//**	ネットショップURL */
	/*
	
	private String shop_Url;
	
	*//**	施設ロゴ画像URL */
	/*
	
	private String facilityLogoImage_Url;
	
	*//**	施設画像URL１ */
	/*
	
	private String facilityImage_Url1;
	
	*//**	施設画像URL２ */
	/*
	
	private String facilityImage_Url2;
	
	*//**	施設画像URL３ */
	/*
	
	private String facilityImage_Url3;
	
	*//**	施設画像URL４ */
	/*
	
	private String facilityImage_Url4;
	
	*//**	施設画像URL５ */
	/*
	
	private String facilityImage_Url5;
	
	*//**	緯度 */
	/*
	
	private float latitude;
	
	*//**	経度 */
	/*
	
	private float longitude;
	
	//FacilityFavorite
	*//**	業務ID *//*
					private long customer_Id;
					
					//FacilityArea
					private Integer disp_Order;
					
					private String district_Nm;*/

}
