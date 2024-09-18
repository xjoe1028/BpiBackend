package com.bpi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NewBpi {
	
	@Schema(description = "貨幣名稱")
	String code;
	
	@Schema(description = "貨幣中文名稱")
	String codeChineseName;
	
	@Schema(description = "匯率(千分位格式)")
	String rate;
	
}
