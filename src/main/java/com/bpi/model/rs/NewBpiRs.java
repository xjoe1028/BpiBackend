package com.bpi.model.rs;

import java.util.Map;

import com.bpi.model.dto.NewBpi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewBpiRs {

	private String updated;

	private Map<String, NewBpi> bpisData;

}
