package ru.kmatrokhin.betoolatest.company.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kmatrokhin.betoolatest.SpringTestBase;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional
class CompanyRepositoryTest extends SpringTestBase {
  private final CompanyRepository companyRepository;

  @Test
  void saveCompanyTest() {
    final var company = testCompany();
    companyRepository.saveAndFlush(company);
    final var foundCompany = companyRepository.findById(company.getId());
    assertNotNull(foundCompany);
  }

  @Test
  void findByIdAndDeletedDateNullTest() {
    final var company = testCompany().setDeletedDate(LocalDateTime.now());
    companyRepository.saveAndFlush(company);
    assertTrue(companyRepository.findByIdAndDeletedDateNull(company.getId()).isEmpty());
  }

  @Test
  void findByDeletedDateNullTest() {
    final var companies = IntStream.range(0, 50).mapToObj(i -> {
      final var company = testCompany();
      if (i % 10 == 0) {
        company.setDeletedDate(LocalDateTime.now());
      }
      return company;
    }).collect(Collectors.toList());
    companyRepository.saveAll(companies);
    companyRepository.flush();
    final var byDeletedDateNull = companyRepository.findByDeletedDateNull();
    assertEquals(companyRepository.findAll().size(), 50);
    assertEquals(45, byDeletedDateNull.size());
  }
}