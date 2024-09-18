package com.bpi.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.bpi.cconstant.ErrorCode;
import com.bpi.model.assembler.BpiAssembler;
import com.bpi.model.dto.CoindeskBpiDTO;
import com.bpi.model.dto.NewBpi;
import com.bpi.model.entity.BpiEntity;
import com.bpi.model.rq.BpiRq;
import com.bpi.model.rs.ApiResponse;
import com.bpi.model.rs.BpiRs;
import com.bpi.model.rs.Coindesk;
import com.bpi.model.rs.NewBpiRs;
import com.bpi.repository.BpiRepository;
import com.bpi.util.BpiRsUtil;
import com.bpi.util.DateUtil;
import com.bpi.util.JsonUtils;
import com.bpi.util.NumberUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BpiService {

	// JPA
	final BpiRepository bpiRepository;
	
	// mapStruct
	final BpiAssembler bpiAssembler;
	
	/**
	 * select all
	 */
	public ApiResponse<List<BpiRs>> findAll() {
		List<BpiEntity> bpiList = bpiRepository.findAll();

		if (bpiList.isEmpty())
			return BpiRsUtil.getFailed(ErrorCode.SELECT_EMPTY);

		return BpiRsUtil.getSuccess(bpiAssembler.entityListToListRs(bpiList));
	}

	/**
	 * select by code(pk)
	 */
	public ApiResponse<BpiRs> findBpiByPk(String code) {
		Optional<BpiEntity> bpi = bpiRepository.findById(code);
		
		if (!bpi.isPresent()) 
			return BpiRsUtil.getFailed(ErrorCode.SELECT_EMPTY);
		
		return BpiRsUtil.getSuccess(bpiAssembler.entityToRs(bpi.get()));
	}
	
	/**
	 * select by codeChineseName
	 */
	public ApiResponse<BpiRs> findBpiByCodeChineseName(String codeChineseName) {
		Optional<BpiEntity> bpi = bpiRepository.findByCodeChineseName(codeChineseName);
		
		if (!bpi.isPresent())
			return BpiRsUtil.getFailed(ErrorCode.SELECT_EMPTY);

		return BpiRsUtil.getSuccess(bpiAssembler.entityToRs(bpi.get()));
	}
	
	/**
	 * 查詢 where code = ? and codeChineseName = ?
	 */
	public ApiResponse<BpiRs> findBpiByCodeAndCodeChineseName(String code, String codeChineseName) {
		Optional<BpiEntity> bpi = bpiRepository.findByCodeAndCodeChineseName(code, codeChineseName);
		
		if (!bpi.isPresent()) 
			return BpiRsUtil.getFailed(ErrorCode.SELECT_EMPTY);

		return BpiRsUtil.getSuccess(bpiAssembler.entityToRs(bpi.get()));
	}

	/**
	 * 新增
	 */
	public ApiResponse<BpiRs> addBpi(BpiRq rq) {
		Optional<BpiEntity> bpi = bpiRepository.findById(rq.getCode());
		
		if (bpi.isPresent()) 
			return BpiRsUtil.getFailed(ErrorCode.INSERT_FAILED_PK_ONLY);

		BpiEntity entity = bpiAssembler.toEntity(rq);
		entity.setRate(NumberUtil.fmtMicrometer(String.valueOf(rq.getRateFloat()))); // 千分位格式化
		entity.setCreated(DateUtil.getNowDate());
		return BpiRsUtil.getSuccess(bpiAssembler.entityToRs(bpiRepository.save(entity)));
	}

	/**
	 * 修改
	 */
	public ApiResponse<BpiRs> updateBpi(BpiRq rq) {
		Optional<BpiEntity> oldBpi = bpiRepository.findById(rq.getOldCode());
		BpiEntity entity = bpiAssembler.toEntity(rq);
		entity.setRate(NumberUtil.fmtMicrometer(String.valueOf(rq.getRateFloat()))); // 千分位格式化

		if (!oldBpi.isPresent()) {
			log.info("原幣別資料不存在，直接做新增");
			entity.setCreated(DateUtil.getNowDate());
			entity = bpiRepository.save(entity);
		} else {
			log.info("原幣別資料已存在，直接做修改");
			entity.setUpdated(DateUtil.getNowDate());
			entity.setCreated(oldBpi.get().getCreated());
			bpiRepository.updateBpi(entity, rq.getOldCode());
		}

		return BpiRsUtil.getSuccess(bpiAssembler.entityToRs(entity));
	}
	
	/**
	 * Delete entity
	 */
	public ApiResponse<BpiRs> deleteBpi(String code) {
		Optional<BpiEntity> bpi = bpiRepository.findById(code);

		if (!bpi.isPresent())
			return BpiRsUtil.getFailed(ErrorCode.DELETE_FAILED_DATA_NOT_EXIST);

		bpiRepository.delete(bpi.get());
		return BpiRsUtil.getSuccess(bpiAssembler.entityToRs(bpi.get()));
	}

	/**
	 * 呼叫 url 後 return 更新時間,幣別,幣別中文名稱,利率
	 * 
	 * @param jsonStr
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws ParseException
	 */
	public NewBpiRs transform(String jsonStr) {
		Coindesk coindesk = JsonUtils.getObject(jsonStr, Coindesk.class);
		log.info("coindesk: {}", coindesk);

		List<BpiEntity> allBpis = Optional.of(bpiRepository.findAll()).orElseGet(ArrayList::new);

		assert coindesk != null;
		Map<String, NewBpi> bpisMap = coindesk.getBpi().values().stream()
			.map(b -> transformNewBpi(allBpis, b))
			.collect(Collectors.toMap(NewBpi::getCode, Function.identity(), (v1, v2) -> v2));
		
		log.info("bpiMap: {}", bpisMap);
		
		return NewBpiRs.builder()
			.bpisData(bpisMap)
			.updated(DateUtil.updatedFormat(coindesk.getTime().getUpdatedISO().substring(0,19)))
			.build();
	}
	
	private NewBpi transformNewBpi(List<BpiEntity> allBpis, CoindeskBpiDTO cbDto) {
		String codeChineseName = allBpis.stream()
			.filter(ab -> StringUtils.equals(ab.getCode(), cbDto.getCode()))
			.findFirst().map(BpiEntity::getCodeChineseName).orElse("");
		return NewBpi.builder()
			.code(cbDto.getCode())
			.codeChineseName(codeChineseName)
			.rate(cbDto.getRate())
			.build();
	}
	
}
