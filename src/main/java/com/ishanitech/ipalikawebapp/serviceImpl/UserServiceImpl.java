package com.ishanitech.ipalikawebapp.serviceImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishanitech.ipalikawebapp.dto.*;
import com.ishanitech.ipalikawebapp.service.RestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ishanitech.ipalikawebapp.configs.properties.RestApiProperties;
import com.ishanitech.ipalikawebapp.service.UserService;
import com.ishanitech.ipalikawebapp.utilities.HttpUtils;


import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class UserServiceImpl implements UserService {
	private final RestTemplate restTemplate;
	private final RestApiProperties restApiProperties;
	private final String USER_BASE_URL = "/user";
	@Autowired
	private RestClientService restClientService;
	public UserServiceImpl(RestTemplate restTemplate, RestApiProperties restApiProperties) {
		this.restTemplate = restTemplate;
		this.restApiProperties = restApiProperties;
	}

	@Override
	public Response<?> addUser(UserRegistrationDTO user, String token) {
		String template = String.format("%s", USER_BASE_URL);
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<UserRegistrationDTO> requestEntity = HttpUtils.createRequestEntity(HttpMethod.POST, user, MediaType.APPLICATION_JSON, token, url);
		restTemplate.exchange(requestEntity, String.class);
		return new Response<String>("Successful");
	}

	@Override
	public void deleteUser(int userId, String token) {
		String template =  USER_BASE_URL + "{userId}";
		String url = HttpUtils.createRequestUrl(restApiProperties, template, Collections.singletonMap("userId", userId));
		RequestEntity requestEntity = HttpUtils.createRequestEntity(HttpMethod.DELETE, MediaType.APPLICATION_JSON, token, url);
		restTemplate.exchange(requestEntity, String.class);
	}

	@Override
	public void changePassword(String newPassword, int userId, String token) {
		Map<String, Object> urlVariables = new HashMap<>();
		urlVariables.put("userId", userId);
		urlVariables.put("password", "password");
		String template =  USER_BASE_URL + "/{userId}/{password}";
		String url = HttpUtils.createRequestUrl(restApiProperties, template, urlVariables);
		RequestEntity<String> requestEntity = HttpUtils.createRequestEntity(HttpMethod.PUT, newPassword, MediaType.APPLICATION_JSON, token, url);
		restTemplate.exchange(requestEntity, String.class).getBody();
	}

	@Override
	public void updateUserInfoByUserId(Map<String, Object> updates, int userId, String token) {
		/*
		 * restTemplate.patchForObject("http://localhost:8888/user/" + userId,
		 * HttpUtils.createRequestEntityWithHeadersAndToken(updates,
		 * MediaType.APPLICATION_JSON, token), String.class);
		 */
		RequestEntity<Map<String, Object>> requestEntity = HttpUtils.createRequestEntity(HttpMethod.PATCH, updates, MediaType.APPLICATION_JSON, token, "http://localhost:8888/user/" + userId);
		restTemplate.exchange(requestEntity, String.class);
	}

	@Override
	public void disableUser(int userId, String token) {
		/*
		 * restTemplate.put("http://localhost:8888/user/" + userId + "/disable",
		 * HttpUtils.createRequestEntityWithHeadersAndToken(null,
		 * MediaType.APPLICATION_JSON, token));
		 */
		RequestEntity requestEntity = HttpUtils.createRequestEntity(HttpMethod.PUT, MediaType.APPLICATION_JSON, token, "http://localhost:8888/user/" + userId + "/disable");
		restTemplate.exchange(requestEntity, String.class).getBody();
	}

	@Override
	public Response<List<RoleDTO>> getAllRoles(String token) {
		
		/*
		 * return restTemplate.exchange("http://localhost:8888/role/", HttpMethod.GET,
		 * HttpUtils.createRequestEntityWithHeadersAndToken(null,
		 * MediaType.APPLICATION_JSON, token), Response.class).getBody();
		 */
		
		RequestEntity requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, token, "http://localhost:8888/role/");
		ParameterizedTypeReference<Response<List<RoleDTO>>> responseType = new ParameterizedTypeReference<Response<List<RoleDTO>>>(){};
		return restTemplate.exchange(requestEntity, responseType).getBody();
	}

	@Override
	public Response<List<UserDTO>> getAllUserInfo(String token) {
		String template = String.format("%s", USER_BASE_URL);
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<List<UserDTO>> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, token, url);
		ParameterizedTypeReference<Response<List<UserDTO>>> responseType = new ParameterizedTypeReference<Response<List<UserDTO>>>() {};
		Response<List<UserDTO>> userList = restTemplate.exchange(requestEntity, responseType).getBody();
		return userList;
	}

	@Override
	public Map<String, Boolean> checkPotentialDuplicateColumns(Map<String, String> params, String token) {
		String template = String.format("%s/%s",USER_BASE_URL, "duplicate");
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<Map<String, String>> requestEntity = HttpUtils.createRequestEntity(HttpMethod.POST, params, MediaType.APPLICATION_JSON, "xyz", url);
		ParameterizedTypeReference<Response<Map<String, Boolean>>> responseType = new ParameterizedTypeReference<Response<Map<String,Boolean>>>() {
		};
		
		Response<Map<String, Boolean>> result = restTemplate.exchange(requestEntity, responseType).getBody();
		return result.getData();
	}
	
	public Response<UserDTO> getUserInfoByUserId(int userId, String token) {
		String template = String.format("%s/{userId}", USER_BASE_URL);
		Map<String, Object> urlVariables = new HashMap<>();
		urlVariables.put("userId", userId);
		String url = HttpUtils.createRequestUrl(restApiProperties, template, urlVariables);
		RequestEntity<UserDTO> requestEntity = HttpUtils.createRequestEntity(HttpMethod.GET, MediaType.APPLICATION_JSON, token, url);
		ParameterizedTypeReference<Response<UserDTO>> responseType = new ParameterizedTypeReference<Response<UserDTO>>() {};
		Response<UserDTO> userList = restTemplate.exchange(requestEntity, responseType).getBody();
		return userList;
	}
	
	@Override
	public void updateUserInfoByUserIdByAdmin(Map<String, Object> updates, int userId, String token) {
		/*
		 * restTemplate.patchForObject("http://localhost:8888/user/" + userId,
		 * HttpUtils.createRequestEntityWithHeadersAndToken(updates,
		 * MediaType.APPLICATION_JSON, token), String.class);
		 */
		RequestEntity<Map<String, Object>> requestEntity = HttpUtils.createRequestEntity(HttpMethod.PATCH, updates, MediaType.APPLICATION_JSON, token, "http://localhost:8888/user/edit/" + userId);
		restTemplate.exchange(requestEntity, String.class);
	}
	
	@Override
	public void changePasswordByAdmin(String newPassword, int userId, String token) {
		Map<String, Object> urlVariables = new HashMap<>();
		urlVariables.put("userId", userId);
		urlVariables.put("password", "pass-reset-admin");
		String template =  USER_BASE_URL + "/{userId}/{password}";
		String url = HttpUtils.createRequestUrl(restApiProperties, template, urlVariables);
		RequestEntity<String> requestEntity = HttpUtils.createRequestEntity(HttpMethod.PUT, newPassword, MediaType.APPLICATION_JSON, token, url);
		restTemplate.exchange(requestEntity, String.class).getBody();
	}

	@Override
	public  Response<?> registerNormalUser(UserRegistrationDTO user, String token) {
//		String template = String.format("%s/normal/register", USER_BASE_URL);
//		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
//		RequestEntity<UserRegistrationDTO> requestEntity = HttpUtils.createRequestEntity(HttpMethod.POST, user, MediaType.APPLICATION_JSON, token, url);
//		restTemplate.exchange(requestEntity, String.class);

		String template = String.format("%s/normal/register", USER_BASE_URL);
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<UserRegistrationDTO> requestEntity = HttpUtils.createRequestEntity(HttpMethod.POST, user, MediaType.APPLICATION_JSON, "xyz", url);
		restTemplate.exchange(requestEntity, String.class);

		return new Response<String>("Successful");
	}

	@Override
	public Response<?> activateUserAccount(String token) {
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType responseType = objectMapper.getTypeFactory().constructParametricType(Response.class, List.class);
		Response<?> response = null;
		response = restClientService.getData("/user/activateUser?token=" + token, responseType);
		return response;
	}

	@Override
	public Response<?> checkDuplicateEmail(String email) {
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType responseType = objectMapper.getTypeFactory().constructParametricType(Response.class, Boolean.class);
		Response<?> response = null;
		response =restClientService.getData("/forgot-password/checkDuplicateEmail?email=" + email, responseType);
		return response;
	}

	@Override
	public Response<?> sendPasswordResetEmail(String email) {
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType responseType = objectMapper.getTypeFactory().constructParametricType(Response.class, String.class);
		Response<?> response = null;
		response = (Response<?>) restClientService.getData("/forgot-password/reset-user?email=" + email, responseType);
		return response;
	}

	@Override
	public Response<?> verifyPasswordResetToken(String token) {
		ObjectMapper objectMapper = new ObjectMapper();
		JavaType responseType = objectMapper.getTypeFactory().constructParametricType(Response.class, Integer.class);
		Response<?> response = restClientService.getData("/forgot-password/pw-reset-token-verify?token=" + token, responseType);
		log.info("The response is ->"+response.getData());
		return response;
	}

	@Override
	public ResponseEntity<?> updateUserPassword(Integer userId, String password) {

		String template = String.format("forgot-password/update-password/%s", userId);
		ResponseEntity<?> removeResponse;
		String url = HttpUtils.createRequestUrl(restApiProperties, template, null);
		RequestEntity<?> requestEntity = HttpUtils.createRequestEntity(HttpMethod.POST, password, MediaType.APPLICATION_JSON, null, url);
		removeResponse = restTemplate.exchange(requestEntity, String.class);
		return removeResponse;

	}
}
