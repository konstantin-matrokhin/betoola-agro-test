package ru.kmatrokhin.betoolatest.company.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.kmatrokhin.betoolatest.SpringTestBase;
import ru.kmatrokhin.betoolatest.company.dao.Company;
import ru.kmatrokhin.betoolatest.company.dao.CompanyRepository;
import ru.kmatrokhin.betoolatest.openapi.model.CompanyDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CompanyServiceImplTest extends SpringTestBase {
  @SpyBean
  private CompanyServiceImpl companiesApi;

  @MockBean
  private CompanyRepository companyRepository;

  @Test
  void companiesCreateTest() {
    doReturn(testCompany())
        .when(companyRepository)
        .save(any(Company.class));
    final var testDTO = testCompanyDTO();

    final var responseEntity = companiesApi
        .companiesCreate(testDTO, Optional.empty());
    final var body = responseEntity.getBody();
    assertNotNull(body);

    verify(companyRepository, times(1))
        .save(
            argThat((company -> {
              assertEquals(company.getId(), body.getId());
              assertEquals(body.getPostalCode(), testDTO.getPostalCode());
              assertEquals(body.getCountry(), testDTO.getCountry());
              assertEquals(body.getFiscalId(), testDTO.getFiscalId());
              assertEquals(body.getAddress(), testDTO.getAddress());
              assertEquals(body.getName(), testDTO.getName());
              assertEquals(body.getCity(), testDTO.getCity());
              return true;
            }))
        );
  }

  @Test
  void companiesRetrieve() {
    final var company = testCompany();
    company.setId(UUID.randomUUID());

    doReturn(Optional.of(company))
        .when(companyRepository)
        .findByIdAndDeletedDateNull(any(UUID.class));

    companiesApi
        .companiesRetrieve(company.getId(), Optional.empty());
    verify(companyRepository, times(1)).findByIdAndDeletedDateNull(company.getId());
  }

  @Test
  void companiesDestroy() {
    final var uuid = UUID.randomUUID();
    final var company = spy(testCompany());
    company.setId(uuid);

    doReturn(Optional.of(company))
        .when(companyRepository)
        .findByIdAndDeletedDateNull(any(UUID.class));
    doReturn(testCompany())
        .when(companyRepository)
        .save(any(Company.class));

    final var companyDTOResponseEntity = companiesApi.companiesDestroy(uuid, Optional.empty());
    verify(companyRepository, times(1)).findByIdAndDeletedDateNull(uuid);
    verify(companyRepository, times(1)).save(company);
    verify(company, times(1)).setDeletedDate(any(LocalDateTime.class));

    assertNotNull(companyDTOResponseEntity.getBody());
  }

  @Test
  void companiesList() {
    final var companies = List.of(
        testCompany().setId(UUID.randomUUID()),
        testCompany().setId(UUID.randomUUID()),
        testCompany().setId(UUID.randomUUID())
    );

    doReturn(companies)
        .when(companyRepository)
        .findByDeletedDateNull();

    final var listResponseEntity = companiesApi.companiesList(Optional.empty(), Optional.empty());
    verify(companyRepository, times(1)).findByDeletedDateNull();
    verify(companyRepository, never()).findByNameContainsAndDeletedDateNull(anyString());

    assertNotNull(listResponseEntity.getBody());
    assertEquals(3, listResponseEntity.getBody().size());
    assertEquals(
        companies.stream().map(Company::getId).collect(Collectors.toSet()),
        listResponseEntity.getBody().stream().map(CompanyDTO::getId).collect(Collectors.toSet())
    );
  }

  @Test
  void companiesListByName() {
    final var companyCount = 50;
    final var companies = IntStream.range(0, companyCount)
        .mapToObj((obj) -> testCompany().setId(UUID.randomUUID()))
        .collect(Collectors.toList());

    doReturn(companies)
        .when(companyRepository)
        .findByNameContainsAndDeletedDateNull(any());

    final var responseEntity = companiesApi.companiesList(Optional.empty(), Optional.of("test"));

    verify(companyRepository, never()).findByDeletedDateNull();
    verify(companyRepository, times(1)).findByNameContainsAndDeletedDateNull(anyString());

    assertNotNull(responseEntity.getBody());
    assertEquals(companyCount, responseEntity.getBody().size());
  }

  @Test
  void companiesUpdate() {
  }
}
