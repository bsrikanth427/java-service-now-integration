package com.servicenow.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class Utils {

	public static String getValue(String jsonStr) throws JsonMappingException, JsonProcessingException {
		if (!StringUtils.isEmpty(jsonStr)) {
			JSONObject json = new JSONObject(jsonStr);
			return json.getString("value");
		}
		return null;

	}

	public static Date convertToDate(String dateString) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
	}

	public static Map<String, String> currentDateTimeMap() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateTime = format.format(new Date());
		String[] dateArray = currentDateTime.split(" ");
		Map<String, String> map = new HashMap<String, String>();
		map.put("date", "2020-09-09");
		map.put("time", "12:12:12");
		return map;
	}

	public static long calculateLapsedTime(Date createdTime) {
		long diffInMillies = Math.abs(new Date().getTime() - createdTime.getTime());
		long diffInMinutes = diffInMillies / (60 * 1000) % 60;
		return diffInMinutes;
	}
}
