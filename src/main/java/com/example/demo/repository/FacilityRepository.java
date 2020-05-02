package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Facility;
import com.example.demo.entity.FacilityPK;
import com.example.demo.entity.join.FacilityFavoriteArea;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, FacilityPK> {
	public List<Facility> findByBusinessCd(String businessCd);

	@Query(value = "select a.business_cd as business_cd from Facility a "
			+ "inner join Facility_Favorite b on a.business_cd = b.business_cd "
			+ "inner join Facility_Area c on a.area_cd = c.area_cd "
			+ "where b.customer_id = :customerId and a.category_cd in :categoryCdListd ", nativeQuery = true)
	public List<FacilityFavoriteArea> findJoinFavorite(@Param("customerId") Long customerId,
			@Param("categoryCdListd") List<String> categoryCdListd);
}
