package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CustomerImage;

@Repository
public interface CustomerImageRepository extends JpaRepository<CustomerImage, Long> {
	public CustomerImage findByCustomerId(long customerId);
}
