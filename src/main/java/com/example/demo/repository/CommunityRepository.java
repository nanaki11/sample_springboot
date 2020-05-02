package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Community;

public interface CommunityRepository extends JpaRepository<Community, Integer> {

}
