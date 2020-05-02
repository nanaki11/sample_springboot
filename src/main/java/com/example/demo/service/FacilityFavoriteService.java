package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.FacilityFavorite;
import com.example.demo.repository.FacilityFavoriteRepository;

@Service
public class FacilityFavoriteService {

	@Autowired
	private FacilityFavoriteRepository repository;

	public List<FacilityFavorite> findByCustomerId(long customerId) {
		return repository.findByCustomerId(customerId);
	}

	public FacilityFavorite save(FacilityFavorite entity) {
		return repository.save(entity);
	}

	public void delete(FacilityFavorite entity) {
		repository.delete(entity);
	}
}
