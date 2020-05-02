package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 会員情報Bean
 *
 */
@Data
public class UserInfoBean {
	// TODO 画面一覧より、使用が想定される項目のみを取得。必要な項目が不足していた場合は随時追加が必要。

	/** 業務ID */
	private int customerId;

	/** メンバーシップID */
	private String membershipId;

	/** 氏名漢字(姓) */
	private String lastNameKanji;

	/** 氏名漢字(名) */
	private String firstNameKanji;

	/** ニックネーム */
	private String nickname;

	/** メールアドレス1 */
	private String mailAddress1;

	/** メールアドレス2 */
	private String mailAddress2;

	/** 有料会員入会日 */
	private Date paidMemberStartDate;

	/** 会員種別 */
	private int memberType;

	/** 親会員種別 */
	private int parentMemberType;

	/** 有料会員最終更新日 */
	private Date paidMemberLastUpdateDate;

	/**	スタッフフラグ */
	private int isStaff;

	/**	癒湯自適フラグ */
	private int isYuyu;

	/**アクセストークン */
	private String accessToken;

	/**	会員年齢 */
	// TODO ログイン時に生年月日から割り出された値を設定
	private int age;

	/**	コミュニティ情報 */
	List<Community> community;

	/** コミュニティ情報オブジェクト */
	@Data
	public class Community {
		/** コミュニティ管理ID */
		private int id;

		/** コミュニティCD */
		private int communityCd;

		/** コミュニティランクCD */
		private int communityRankCd;
	}
}
