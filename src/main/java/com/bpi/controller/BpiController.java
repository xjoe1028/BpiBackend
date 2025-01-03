package com.bpi.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bpi.model.RqType;
import com.bpi.model.rq.BpiRq;
import com.bpi.model.rs.ApiResponse;
import com.bpi.model.rs.BpiRs;
import com.bpi.model.rs.Coindesk;
import com.bpi.model.rs.NewBpiRs;
import com.bpi.service.BpiService;
import com.bpi.util.BpiRsUtil;
import com.bpi.util.JsonUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*") // 跨域的問題
@RequestMapping(value = "/api/bpi", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@RestController
public class BpiController {

	public static final String COINDESK_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

	final BpiService bpiService;

	final RestTemplate restTemplate;

	@Operation(summary = "查詢所有幣別")
	@GetMapping("/findAllBpis")
	public ApiResponse<List<BpiRs>> findAllBpis() {
		return bpiService.findAll();
	}

	@Operation(summary = "查詢單一幣別")
	@GetMapping("/findBpi/code")
	public ApiResponse<BpiRs> findBpiByPk(
		@Parameter(name = "code", description = "英文幣別", required = true, in = ParameterIn.QUERY, schema = @Schema(implementation = String.class))
		@RequestParam(name = "code", defaultValue = "") String code) {
		return bpiService.findBpiByPk(code);
	}

	@Operation(summary = "查詢單一幣別")
	@GetMapping("/findBpi/codeChineseName")
	public ApiResponse<BpiRs> findBpiByCodeChineseName(
		@Parameter(name = "codeChineseName", description = "中文幣別", required = true, in = ParameterIn.QUERY, schema = @Schema(implementation = String.class))
		@RequestParam(name = "codeChineseName", defaultValue = "") String codeChineseName) {
		return bpiService.findBpiByCodeChineseName(codeChineseName);
	}

	@Operation(summary = "新增幣別")
	@RqType(BpiRq.class)
	@PostMapping("/addBpi")
	public ApiResponse<BpiRs> addBpi(@RequestBody BpiRq rq) {
		return bpiService.addBpi(rq);
	}

	@Operation(summary = "修改幣別")
	@RqType(BpiRq.class)
	@PutMapping("/updateBpi")
	public ApiResponse<BpiRs> updateBpi(@RequestBody BpiRq rq) {
		return bpiService.updateBpi(rq);
	}

	@Operation(summary = "刪除幣別")
	@RqType(BpiRq.class)
	@DeleteMapping("/deleteBpi/code")
	public ApiResponse<BpiRs> deleteBpi(@RequestBody BpiRq rq) {
		return bpiService.deleteBpi(rq.getCode());
	}

	@Operation(summary = "呼叫外部coindesk API")
	@GetMapping("/call/coindesk")
	public ApiResponse<Coindesk> callCoindeskAPI() {
		Coindesk coindesk = JsonUtils.getObject(restTemplate.getForObject(COINDESK_URL, String.class), Coindesk.class);
		log.info("coindesk : {}", coindesk);
		return BpiRsUtil.getSuccess(coindesk);
	}

	@Operation(summary = "呼叫外部coindesk API 後進行資料處理 return")
	@GetMapping("/call/coindesk/transform")
	public ApiResponse<NewBpiRs> transformNewBpi() {
		String jsonStr = restTemplate.getForObject(COINDESK_URL, String.class);
		log.info("call coindesk api res : {}", jsonStr);
		return BpiRsUtil.getSuccess(bpiService.transform(jsonStr));
	}

}
