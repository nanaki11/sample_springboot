package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CommunityInfo;
import com.example.demo.entity.CommunityInfoPK;

@Repository
public interface CommunityInfoRepository extends JpaRepository<CommunityInfo, CommunityInfoPK> {

	// コミュニティIDの昇順に取得
	@Query("select a from CommunityInfo a where lang = :lang and isStaff = :isStaff and ageUpperLimit >= :age and :age >= ageLowerLimit order by communityId")
	public List<CommunityInfo> orderByCommunityId(@Param("lang") String lang, @Param("isStaff") int isStaff, @Param("age") int age);
}
