package ru.kmatrokhin.betoolatest.cultivation.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.kmatrokhin.betoolatest.SpringTestBase;
import ru.kmatrokhin.betoolatest.company.dao.CompanyRepository;
import ru.kmatrokhin.betoolatest.cultivation.dao.CultivationRepository;
import ru.kmatrokhin.betoolatest.cultivation.model.CultivationConverter;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
class CultivationServiceImplTest extends SpringTestBase {
  @SpyBean
  private CultivationServiceImpl cultivationService;

  @MockBean
  private CultivationRepository cultivationRepository;

  @MockBean
  private CompanyRepository companyRepository;

  private final CultivationConverter cultivationConverter;

  @Test
  void companiesCultivationsList() {
    final var company = testCompany();

    final var cultivation = testCultivation(company, testCultivationPolygon());

    doReturn(Optional.of(company))
        .when(companyRepository)
        .findByIdAndDeletedDateNull(any());

    doReturn(List.of(cultivation))
        .when(cultivationRepository)
        .findByCompany_Id(any());

    cultivationService.companiesCultivationsList(company.getId().toString(), Optional.empty(), Optional.empty());
    verify(companyRepository, times(1)).findByIdAndDeletedDateNull(eq(company.getId()));
    verify(cultivationRepository, times(1)).findByCompany_Id(eq(company.getId()));
  }

  @Test
  void companiesOrganizationUnitsFieldsCultivationsCreate() {
    final var company = testCompany();
    final var cultivation = testCultivation(company, testCultivationPolygon());
    final var dto = cultivationConverter.createDTOFromCultivation(cultivation);

    doReturn(cultivation)
        .when(cultivationRepository)
        .save(eq(cultivation));
    doReturn(Optional.of(company))
        .when(companyRepository)
        .findByIdAndDeletedDateNull(any());

    cultivationService.companiesOrganizationUnitsFieldsCultivationsCreate(
        company.getId().toString(), dto, Optional.empty()
    );

    verify(companyRepository, times(1)).findByIdAndDeletedDateNull(company.getId());
    verify(cultivationRepository, times(1)).save(cultivation);
  }
}