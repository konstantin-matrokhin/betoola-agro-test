package ru.kmatrokhin.betoolatest.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.kmatrokhin.betoolatest.company.dao.Company;
import ru.kmatrokhin.betoolatest.company.dao.CompanyRepository;
import ru.kmatrokhin.betoolatest.company.model.CompanyConverter;
import ru.kmatrokhin.betoolatest.exception.EntityNotFoundException;
import ru.kmatrokhin.betoolatest.exception.ErrorCode;
import ru.kmatrokhin.betoolatest.openapi.api.CompaniesApiDelegate;
import ru.kmatrokhin.betoolatest.openapi.model.CompanyDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompaniesApiDelegate {
  private final CompanyRepository companyRepository;
  private final CompanyConverter companyConverter;

  @Override
  public ResponseEntity<CompanyDTO> companiesCreate(CompanyDTO companyDTO, Optional<String> X_CORRELATION_ID) {
    final var company = companyConverter.createCompanyFromDTO(companyDTO);
    final var saved = companyRepository.save(company);
    return ResponseEntity.ok(companyConverter.createDTOFromCompany(saved));
  }

  @Override
  public ResponseEntity<CompanyDTO> companiesRetrieve(UUID id, Optional<String> X_CORRELATION_ID) {
    final var company = getCompany(id);
    return ResponseEntity.ok(companyConverter.createDTOFromCompany(company));
  }

  @Override
  public ResponseEntity<CompanyDTO> companiesDestroy(UUID id, Optional<String> X_CORRELATION_ID) {
    final var company = getCompany(id);
    company.setDeletedDate(LocalDateTime.now());
    final var saved = companyRepository.save(company);
    return ResponseEntity.ok(companyConverter.createDTOFromCompany(saved));
  }

  @Override
  public ResponseEntity<List<CompanyDTO>> companiesList(Optional<String> X_CORRELATION_ID, Optional<String> name) {
    final var companies = name.isPresent()
        ? companyRepository.findByNameContainsAndDeletedDateNull(name.get())
        : companyRepository.findByDeletedDateNull();

    final var companyDTOs = companies.stream()
        .map(companyConverter::createDTOFromCompany)
        .collect(Collectors.toList());

    return ResponseEntity.ok(companyDTOs);
  }

  @Override
  public ResponseEntity<CompanyDTO> companiesUpdate(UUID id, CompanyDTO companyDTO, Optional<String> X_CORRELATION_ID) {
    final var company = getCompany(id);
    companyConverter.updateCompanyFieldsFromDTO(company, companyDTO);
    final var saved = companyRepository.save(company);
    return ResponseEntity.ok(companyConverter.createDTOFromCompany(saved));
  }

  private Company getCompany(UUID id) {
    return companyRepository.findByIdAndDeletedDateNull(id)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMPANY_NOT_FOUND, "Company not found"));
  }
}
