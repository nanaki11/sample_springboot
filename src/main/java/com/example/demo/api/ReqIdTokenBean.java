package com.example.demo.api;

import lombok.Data;

/**
 * 共同DBサイトにIDトークン認証API要求を行う際にリクエストで使用するBean
 *
 */
@Data
public class ReqIdTokenBean {

	/** クライアントID */
	private String clientId;

	/** IDトークン */
	private String idt;

}
