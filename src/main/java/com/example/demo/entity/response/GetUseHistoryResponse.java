package com.example.demo.entity.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 利用履歴取得APIレスポンスの結果オブジェクト．
 *
 */
@Data
@JsonSerialize
public class GetUseHistoryResponse {
	/**	 利用履歴リスト */
	private List<UseHistory> useHistorys;

	/**
	 * 利用履歴オブジェクト．
	 */
	@Data
	@AllArgsConstructor
	public class UseHistory {
		private long id;
		private String useDiv;
		private String hotelDiv;
		private String dormitoryDiv;
		private String officeCd;
		private String propertyCd;
		private Date checkInDate;
		private Date useDate;
		private long totalAmount;
	}
}
