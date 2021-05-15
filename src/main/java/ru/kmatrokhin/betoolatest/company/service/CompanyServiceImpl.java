package ru.kmatrokhin.betoolatest.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.kmatrokhin.betoolatest.company.dao.CompanyRepository;
import ru.kmatrokhin.betoolatest.company.model.CompanyConverter;
import ru.kmatrokhin.betoolatest.openapi.api.CompaniesApiDelegate;
import ru.kmatrokhin.betoolatest.openapi.model.CompanyDTO;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompaniesApiDelegate {
  private final CompanyRepository companyRepository;
  private final CompanyConverter companyConverter;

  @Override
  public ResponseEntity<CompanyDTO> companiesCreate(CompanyDTO companyDTO, Optional<String> X_CORRELATION_ID) {
    final var company = companyConverter.createCompanyFromDTO(companyDTO);
    companyRepository.save(company);
    return ResponseEntity.ok(companyConverter.createDTOFromCompany(company));
  }

  @Override
  public ResponseEntity<CompanyDTO> companiesRetrieve(UUID id, Optional<String> X_CORRELATION_ID) {
    final var company = companyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    return ResponseEntity.ok(companyConverter.createDTOFromCompany(company));
  }
}
