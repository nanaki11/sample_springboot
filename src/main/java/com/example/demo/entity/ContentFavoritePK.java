/**
 *
 */
package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * コンテンツお気に入りの主キー．
 *
 */
@Data
@Embeddable
public class ContentFavoritePK implements Serializable {

	/**	業務ID */
	@Column(name = "customer_id")
	private long customerId;

	/**	コンテンツID */
	@Column(name = "content_id")
	private long contentId;
}
