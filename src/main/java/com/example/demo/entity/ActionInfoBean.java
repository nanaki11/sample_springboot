package com.example.demo.entity;

import java.util.Map;

import lombok.Data;

@Data
public class ActionInfoBean {

	String accessUrl;
	String method;
	Map<String, String[]> parameter;

}
