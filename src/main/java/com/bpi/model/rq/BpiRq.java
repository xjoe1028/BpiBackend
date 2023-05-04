package com.bpi.model.rq;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(description = "幣別 API Request")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BpiRq extends BaseRq {

	/**
	 * 貨幣名稱
	 */
	@Schema(description = "貨幣名稱")
	@NotBlank(message = "code must be not empty")
	public String code;

	/**
	 * 貨幣中文名稱
	 */
	@Schema(description = "貨幣中文名稱")
	@NotBlank(message = "codeChineseName must be not empty")
	private String codeChineseName;

	/**
	 * 金錢格式 ex: $
	 */
	@Schema(description = "金錢符號")
	private String symbol;

	/**
	 * 匯率
	 */
	@Schema(description = "匯率")
	@NotNull(message = "rateFloat must be not empty")
	private Double rateFloat;

	/**
	 * 描述
	 */
	@Schema(description = "描述")
	private String description;

	/**
	 * 匯率(千分位格式)
	 */
	@Schema(description = "匯率(千分位格式)")
	private String rate;

	/**
	 * 舊幣別 for update用
	 */
	@Schema(description = "舊幣別")
	private String oldCode;

	/**
	 * 創建時間
	 */
	@Schema(description = "創建時間")
	private String created;

	/**
	 * 更新時間
	 */
	@Schema(description = "更新時間")
	private String updated;

	@Builder
	public BpiRq(String code, String codeChineseName, String symbol, Double rateFloat, String description) {
		this.code = code;
		this.codeChineseName = codeChineseName;
		this.symbol = symbol;
		this.rateFloat = rateFloat;
		this.description = description;
	}

}
