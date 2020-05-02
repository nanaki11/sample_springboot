package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Community;
import com.example.demo.repository.CommunityRepository;

@Service
public class CommunityService {
	private CommunityRepository repository;

	public CommunityService(CommunityRepository repository) {
		this.repository = repository;
	}

	public List<Community> findAll() {
		return repository.findAll();
	}

	public Page<Community> findAll(PageRequest of) {
		return repository.findAll(of);
	}
}
