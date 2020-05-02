package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Content;
import com.example.demo.entity.ContentPK;

@Repository
public interface ContentRepository extends JpaRepository<Content, ContentPK> {

	public List<Content> findByContentId(long contentId);

	//指定したcontent_typeのレコードを取得
	@Query("select a from Content a where content_type = :keyword order by  delivery_start_date")
	public List<Content> findByContentType(@Param("keyword") String keyword);

	//指定したcontent_typeを除いたレコードをContentから取得
	@Query("select a from Content a where content_type <> :keyword order by  delivery_start_date")
	public List<Content> findExceptByContentType(@Param("keyword") String keyword);

	@Query(value = "select a.* from Content a "
			+ "inner join Content_Favorite b on a.content_id = b.content_id "
			+ "where customer_id = :customerId", nativeQuery = true)
	public List<Content> findJoinFavorite2(@Param("customerId") long customerId);

	@Query("select a from Content a join a.contentFavorites b where customer_id = :customerId order by a.deliveryStartDate desc")
	public List<Content> findJoinFavorite(@Param("customerId") long customerId);
}
