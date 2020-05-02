package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ContentFavorite;
import com.example.demo.entity.ContentFavoritePK;

@Repository
public interface ContentFavoriteRepository extends JpaRepository<ContentFavorite, ContentFavoritePK> {
	public List<ContentFavorite> findByCustomerId(long customerId);
}
