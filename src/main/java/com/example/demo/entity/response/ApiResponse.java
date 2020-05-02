package com.example.demo.entity.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * APIレスポンス共通項目．
 *
 */
@Data
@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
	public static final int STATE_OK = 0;
	public static final int STATE_ERROR = 1;

	/**	正常 */
	public ApiResponse(Object result) {
		this.status = STATE_OK;
		this.result = result;
	}

	/**	エラー */
	public ApiResponse(ApiError error) {
		this.status = STATE_ERROR;
		this.error = error;
	}

	/**	処理結果ステータス */
	private int status;

	/**	エラーオブジェクト */
	private ApiError error;

	/**	結果オブジェクト */
	private Object result;

	@Data
	public static class ApiError {
		/**	エラーID */
		private String id;

		/**	エラーコード */
		private String code;

		/**	エラーメッセージ */
		private String message;

		/**	エラー詳細 */
		private Object detail;

	}
}
