package com.bpi.model.rs;

import java.util.Map;

import com.bpi.model.dto.CoindeskBpiDTO;
import com.bpi.model.dto.Time;

import lombok.Data;

@Data
public class Coindesk {
	
	private Time time;
	
	private String disclaimer;
	
	private String chartName;
	
	private Map<String, CoindeskBpiDTO> bpi;
	
}
