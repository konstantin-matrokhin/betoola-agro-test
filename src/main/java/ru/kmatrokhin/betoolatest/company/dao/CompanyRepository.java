package ru.kmatrokhin.betoolatest.company.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
  Optional<Company> findByIdAndDeletedDateNull(UUID id);

  List<Company> findByNameContains(String name);
}
