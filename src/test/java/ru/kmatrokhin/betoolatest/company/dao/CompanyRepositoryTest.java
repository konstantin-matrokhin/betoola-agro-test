package ru.kmatrokhin.betoolatest.company.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kmatrokhin.betoolatest.SpringTestBase;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
class CompanyRepositoryTest extends SpringTestBase {
  private final CompanyRepository companyRepository;

  @Test
  void saveCompanyTest() {
    final var company = testCompany();
    companyRepository.saveAndFlush(company);
    final var foundCompany = companyRepository.findById(company.getId());
    assertNotNull(foundCompany);
  }
}