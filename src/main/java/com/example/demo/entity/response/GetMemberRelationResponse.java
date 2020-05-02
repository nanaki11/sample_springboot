package com.example.demo.entity.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * メンバーシップ連携状態取得APIレスポンスの結果オブジェクト．
 *
 */
@Data
@JsonSerialize
public class GetMemberRelationResponse {
	/**	 メンバーシップ連携リスト */
	private List<MemberRelation> relation;

	/**
	 * メンバーシップ連携オブジェクト．
	 */
	@Data
	@AllArgsConstructor
	public class MemberRelation {
		private long relationId;
		private long customerId;
		private String name;
		private Date birthDate;
		private String relationStatus;
	}
}
