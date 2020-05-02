/**
 *
 */
package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

/**
 * コンテンツお気に入りのEntity．
 *
 */
@Entity
@Table(name = "content_favorite")
@IdClass(ContentFavoritePK.class)
@Data
public class ContentFavorite implements Serializable {
	private static final long serialVersionUID = 1L;

	/**	業務ID */
	@Id
	@Column(name = "customer_id")
	private long customerId;

	/**	コンテンツID */
	@Id
	@Column(name = "content_id")
	private long contentId;

	/**	言語 */
	@Column(name = "lang")
	private String lang;

}
