package com.bpi.model.rs;

import java.util.Map;

import com.bpi.model.dto.CoindeskBpiDTO;
import com.bpi.model.dto.Time;

import lombok.Data;

@Data
public class Coindesk {
	
	Time time;

	String disclaimer;

	String chartName;

	Map<String, CoindeskBpiDTO> bpi;
	
}
