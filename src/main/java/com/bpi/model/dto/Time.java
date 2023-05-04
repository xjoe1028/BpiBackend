package com.bpi.model.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Time implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ex: Oct 6, 2021 10:58:00 UTC
	 */
	@Schema(description = "updated 修改日期")
	private String updated;
	
	/**
	 * ex: 2021-10-06T10:58:00+00:00
	 */
	@Schema(description = "updatedISO 修改日期ISO")
	private String updatedISO;
	
	/**
	 * 原時區+一小時
	 * ex: Oct 6, 2021 at 11:58 BST
	 */
	@Schema(description = "updateduk 修改日期")
	private String updateduk;

}
