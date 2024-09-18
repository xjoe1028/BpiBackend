package com.bpi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoindeskBpiDTO {

	@Schema(description = "貨幣名稱")
	String code;
	
	@Schema(description = "金錢符號")
	String symbol;

	@Schema(description = "描述")
	String description;
	
	@Schema(description = "匯率(千分位格式)")
	String rate;
	
	@Schema(description = "匯率")
	BigDecimal rateFloat;

}
