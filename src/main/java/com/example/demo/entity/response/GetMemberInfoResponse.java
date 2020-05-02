package com.example.demo.entity.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 会員情報取得APIレスポンスの結果オブジェクト．
 *
 */
@Data
@JsonSerialize
public class GetMemberInfoResponse {
	/**
	 * 会員情報オブジェクト．
	 */
	private long customerId;
	private String membershipId;
	private String mailAddress1;
	private String mailAddress2;
	private long receiveMailFlag1;
	private long receiveMailFlag2;
	private String lastNameKanji;
	private String firstNameKanji;
	private String lastNameKana;
	private String firstNameKana;
	private String lastNameRomaji;
	private String firstNameRomaji;
	private String nickname;
	private String imageUrl;
	private long completeRate;
	private long completeFlag;
	private String phoneNumber;
	private String mobilePhoneNumber;
	private String sex;
	private Date birthDate;
	private String country;
	private String lang;
	private String residenceCountry;
	private String postcode;
	private String prefectureCd;
	private String address1;
	private String address2;
	private String companyNameKanji;
	private String companyNameKana;
	private int memberType;
	private Date paidMemberStartDate;
	private Date paidMemberLastUpdateDate;
	private long parentMemberType;
	private long serviceMemberType;
	private List<String> subscriptionCdList;
	private String dpointMemberNo;
	private String dpointCardNo;
	private int staffFlag;
	private int yuyuFlag;
	private List<Community> community;

	/**
	 * コミュニティ情報オブジェクト．
	 */
	@Data
	@AllArgsConstructor
	public class Community {
		private long id;
		private long communityCd;
		private long communityRankCd;
	}
}
