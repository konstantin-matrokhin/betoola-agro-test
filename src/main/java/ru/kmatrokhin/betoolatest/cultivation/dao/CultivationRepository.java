package ru.kmatrokhin.betoolatest.cultivation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CultivationRepository extends JpaRepository<Cultivation, UUID> {
  List<Cultivation> findByCompany_Id(UUID companyId);
  List<Cultivation> findByCompany_IdAndNameLike(UUID companyId, String name);
}
