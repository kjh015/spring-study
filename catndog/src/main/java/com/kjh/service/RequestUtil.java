package com.kjh.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestUtil {
	public static String restRequest(String requestUrl, String name) {

		// 보낼 파라메터 셋팅(file name)
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("id", name);

		// 헤더셋팅
		HttpHeaders headers = new HttpHeaders();
		headers.add("accept", "text/plain;charset=UTF-8");

		// 파라메터와 헤더 합치기
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
		// RestTemplate 초기화
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, entity, String.class);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode root;
		JsonNode target = null;
		;
		try {
			root = mapper.readTree(response.getBody());
			target = root.path("result");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return target.asText(); // 문자로 된 확률 값을 리턴
	}
}
