package ru.kmatrokhin.betoolatest.company.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
  Optional<Company> findByIdAndDeletedDateNull(UUID id);

  @Query(value = "select * from company where name ilike '%' || :name || '%'", nativeQuery = true)
  List<Company> searchByName(String name);

  List<Company> findByDeletedDateNull();
}
