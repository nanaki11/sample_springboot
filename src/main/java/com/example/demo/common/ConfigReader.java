package com.example.demo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 設定ファイルパラメータ取得クラス
 */
@Component
@Data
public class ConfigReader {

	/** 利用履歴APIエンドポイント */
	@Value("${apiUrl.useHistoryUrl}")
	private String useHistoryUrl;

	/** 会員情報取得APIエンドポイント */
	@Value("${apiUrl.getMemberInfoUrl}")
	private String getMemberInfoUrl;

	/** メンバーシップ連携状態取得APIエンドポイント */
	@Value("${apiUrl.getMemberRelationUrl}")
	private String getMemberRelationUrl;

	/** ファミリーメンバーズ連携解除取得APIエンドポイント */
	@Value("${apiUrl.cancelRelationUrl}")
	private String cancelRelationUrl;

	/** クライアントID */
	@Value("${clientId}")
	private String clientId;

}
