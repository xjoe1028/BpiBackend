package com.bpi.model.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Bpi")
@Entity
public class BpiEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	@Schema(name = "id pk")
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long id;

	/**
	 * 貨幣名稱
	 */
	@Schema(description = "貨幣名稱")
	@Id
	@NotNull
	private String code;
	
	/**
	 * 貨幣中文名稱
	 */
	@Schema(description = "貨幣中文名稱")
	@Basic
	@Column
	@NotNull
	private String codeChineseName;
	
	/**
	 * 金錢格式 ex: $
	 */
	@Schema(description = "金錢符號")
	@Basic
	@Column
	private String symbol;
	
	/**
	 * 匯率 有千分位樣式 
	 */
	@Schema(description = "匯率(千分位,)")
	@Basic
	@Column
	private String rate; 
	
	/**
	 * 匯率 
	 */
	@Schema(description = "匯率")
	@Basic
	@Column
	private Double rateFloat;
	
	/**
	 * 描述
	 */
	@Schema(description = "描述")
	@Basic
	@Column
	private String description;
	
	/**
	 * 創建時間
	 */
	@Schema(description = "創建時間")
	@Basic
	@Column
	@NotNull
	private String created;
	
	/**
	 * 更新時間
	 */
	@Schema(description = "更新時間")
	@Basic
	@Column
	private String updated;
	
}
