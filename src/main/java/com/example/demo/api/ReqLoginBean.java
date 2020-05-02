package com.example.demo.api;

import java.io.Serializable;

import lombok.Data;

/**
 * 共同DBサイトにログイン要求を行う際に使用するBean
 *
 */
@Data
public class ReqLoginBean implements Serializable {

	/** クライアントID */
	private String cid;

	/** CSRF対策用ワンタイムトークン */
	private String state;

	/** リプレイアタック対策用ワンタイムトークン */
	private String nonce;

	/** 対応言語（省略時は日本語となる。日本語設定固定） */
	private String lang;

}
