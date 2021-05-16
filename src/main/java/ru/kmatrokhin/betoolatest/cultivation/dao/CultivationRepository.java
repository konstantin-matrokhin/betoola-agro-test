package ru.kmatrokhin.betoolatest.cultivation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CultivationRepository extends JpaRepository<Cultivation, UUID> {
  List<Cultivation> findByCompany_Id(UUID companyId);

  @Query(
      value = "select * from cultivation"
          + "  where company_id = :companyId and name ilike '%' || :name || '%'",
      nativeQuery = true
  )
  List<Cultivation> searchByCompanyAndName(UUID companyId, String name);
}
