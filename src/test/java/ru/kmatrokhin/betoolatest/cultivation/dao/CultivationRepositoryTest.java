package ru.kmatrokhin.betoolatest.cultivation.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kmatrokhin.betoolatest.SpringTestBase;
import ru.kmatrokhin.betoolatest.company.dao.CompanyRepository;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional
class CultivationRepositoryTest extends SpringTestBase {
  private final CompanyRepository companyRepository;
  private final CultivationRepository cultivationRepository;

  @Test
  void saveCultivationTest() {
    final var company = testCompany();
    companyRepository.saveAndFlush(company);
    assertNotNull(company.getId());

    final var coordinates = List.of(
        new Double[] {0D, 0D},
        new Double[] {1D, 1D},
        new Double[] {3D, 1000D},
        new Double[] {30000D, 100D}
    );

    final var cultivationPolygon = new CultivationPolygon()
        .setType("test type")
        .setCoordinates(coordinates);

    final var cultivation = testCultivation(company, cultivationPolygon);

    cultivationRepository.saveAndFlush(cultivation);
    final var found = cultivationRepository.findById(cultivation.getId()).orElseThrow();
    assertNotNull(found.getId());
  }
}