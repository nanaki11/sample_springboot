package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.FacilityFavorite;
import com.example.demo.entity.FacilityFavoritePK;

@Repository
public interface FacilityFavoriteRepository extends JpaRepository<FacilityFavorite, FacilityFavoritePK> {
	public List<FacilityFavorite> findByCustomerId(long customerId);
}
