package com.bpi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bpi.model.entity.BpiEntity;

@Repository
public interface BpiRepository extends JpaRepository<BpiEntity, String> {
	
	public BpiEntity findByCode(String code);
	
	public Optional<BpiEntity> findByCodeChineseName(String codeChineseName);
	
	public Optional<BpiEntity> findByCodeAndCodeChineseName(String code, String codeChineseName);
   
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE BpiEntity SET code = :#{#bpi.code} , codeChineseName = :#{#bpi.codeChineseName}, symbol = :#{#bpi.symbol}, rate = :#{#bpi.rate} , rateFloat = :#{#bpi.rateFloat}, description = :#{#bpi.description}, created = :#{#bpi.created}, updated = :#{#bpi.updated} WHERE code = :oldCode")
	@Transactional
	public int updateBpi(@Param("bpi") BpiEntity bpi, @Param("oldCode") String oldCode);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE BpiEntity SET rate = :rate , rateFloat = :rateFloat, updated = :updated WHERE code = :code")
	@Transactional
	public int updateBpiRateByCode(@Param("rate") String rate, @Param("rateFloat") Double rateFloat , @Param("code") String code, @Param("updated") String updated);
	
	@Modifying
	@Query("DELETE FROM BpiEntity WHERE code = :code")
	@Transactional
	public int deleteBpiByCode(@Param("code") String code);
	
}
