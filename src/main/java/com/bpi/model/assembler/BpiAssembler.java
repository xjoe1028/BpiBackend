package com.bpi.model.assembler;

import java.util.List;

import org.mapstruct.Mapper;

import com.bpi.model.entity.BpiEntity;
import com.bpi.model.rq.BpiRq;
import com.bpi.model.rs.BpiRs;

@Mapper(componentModel = "spring")
public interface BpiAssembler {
	
	BpiEntity toEntity(BpiRq rq);
	
	BpiRs entityToRs(BpiEntity entity);
	
	List<BpiRs> entityListToListRs(List<BpiEntity> entitys);
	
}
