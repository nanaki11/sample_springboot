/**
 *
 */
package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * プロフィール画像のEntity．
 *
 */
@Entity
@Table(name = "customer_image")
@Data
public class CustomerImage implements Serializable {
	private static final long serialVersionUID = 1L;

	/**	業務ID */
	@Id
	@Column(name = "customer_id")
	private long customerId;

	/**	画像URL */
	@Column(name = "image_url")
	private String imageUrl;
}
