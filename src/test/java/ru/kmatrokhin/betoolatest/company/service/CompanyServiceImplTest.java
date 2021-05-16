package ru.kmatrokhin.betoolatest.company.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.kmatrokhin.betoolatest.SpringTestBase;
import ru.kmatrokhin.betoolatest.company.dao.Company;
import ru.kmatrokhin.betoolatest.company.dao.CompanyRepository;
import ru.kmatrokhin.betoolatest.company.model.CompanyConverter;
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

  @Autowired
  private CompanyConverter companyConverter;

  @Test
  void companiesCreateTest() {
    final var uuid = UUID.randomUUID();
    final var testDTO = testCompanyDTO();
    final var company = companyConverter.createCompanyFromDTO(testDTO);
    company.setId(uuid);
    doReturn(company)
        .when(companyRepository)
        .save(any(Company.class));

    final var responseEntity = companiesApi
        .companiesCreate(testDTO, Optional.empty());
    final var body = responseEntity.getBody();
    assertNotNull(body);
    assertNotNull(body.getId());

    verify(companyRepository, times(1))
        .save(
            argThat((cmp -> {
              assertEquals(body.getPostalCode(), cmp.getPostalCode());
              assertEquals(body.getCountry(), cmp.getCountry());
              assertEquals(body.getFiscalId(), cmp.getFiscalId());
              assertEquals(body.getAddress().orElse(null), cmp.getAddress());
              assertEquals(body.getName(), cmp.getName());
              assertEquals(body.getCity().orElse(null), cmp.getCity());
              return true;
            }))
        );

    assertEquals(uuid, body.getId());
  }

  @Test
  void companiesRetrieve() {
    final var company = testCompany();

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
        testCompany(),
        testCompany(),
        testCompany()
    );

    doReturn(companies)
        .when(companyRepository)
        .findByDeletedDateNull();

    final var listResponseEntity = companiesApi.companiesList(Optional.empty(), Optional.empty());
    verify(companyRepository, times(1)).findByDeletedDateNull();
    verify(companyRepository, never()).searchByName(anyString());

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
        .mapToObj((obj) -> testCompany())
        .collect(Collectors.toList());

    doReturn(companies)
        .when(companyRepository)
        .searchByName(any());

    final var responseEntity = companiesApi.companiesList(Optional.empty(), Optional.of("test"));

    verify(companyRepository, never()).findByDeletedDateNull();
    verify(companyRepository, times(1)).searchByName(anyString());

    assertNotNull(responseEntity.getBody());
    assertEquals(companyCount, responseEntity.getBody().size());
  }

  @Test
  void companiesUpdate() {
    final var uuid = UUID.randomUUID();
    final var company = testCompany().setId(uuid);

    doReturn(company)
        .when(companyRepository)
        .save(any(Company.class));
    doReturn(Optional.of(company))
        .when(companyRepository)
        .findByIdAndDeletedDateNull(uuid);

    final var createdResponse = companiesApi.companiesCreate(testCompanyDTO(), Optional.empty());
    final var createdBody = createdResponse.getBody();
    assertNotNull(createdBody);
    assertNotNull(createdBody.getId());

    createdBody.setId(null);
    createdBody.setFiscalId("000123");
    createdBody.setName("new name");

    final var updateResponseEntity = companiesApi
        .companiesUpdate(company.getId(), createdBody, Optional.empty());
    assertNotNull(updateResponseEntity.getBody());
    assertNotNull(updateResponseEntity.getBody().getId());

    verify(companyRepository, times(2)).save(any());
    verify(companyRepository, times(1)).save(
        argThat(updatedCompany -> "000123".equals(updatedCompany.getFiscalId())
            && "new name".equals(updatedCompany.getName()))
    );

    assertEquals(uuid, updateResponseEntity.getBody().getId());
  }
}
