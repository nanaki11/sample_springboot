package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Facility;
import com.example.demo.entity.join.FacilityFavoriteArea;
import com.example.demo.repository.FacilityRepository;

@Service
public class FacilityService {

	@Autowired
	private FacilityRepository repository;

	public List<Facility> findByBusinessCd(String businessCd) {
		return repository.findByBusinessCd(businessCd);
	}

	public Facility save(Facility entity) {
		return repository.save(entity);
	}

	public void delete(Facility entity) {
		repository.delete(entity);
	}

	public List<FacilityFavoriteArea> findJoinFavorite(long customerId, List<String> categoryCdListd) {
		return repository.findJoinFavorite(customerId, categoryCdListd);
	}
}
