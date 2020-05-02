/**
 *
 */
package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * コンテンツマスタの主キー．
 *
 */
@Data
@Embeddable
public class ContentPK implements Serializable {

	/**	コンテンツID */
	@Column(name = "content_id")
	private long contentId;

	/**	言語 */
	@Column(name = "lang")
	private String lang;
}
