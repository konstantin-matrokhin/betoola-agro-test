package ru.kmatrokhin.betoolatest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kmatrokhin.betoolatest.company.dao.Company;
import ru.kmatrokhin.betoolatest.cultivation.dao.Cultivation;
import ru.kmatrokhin.betoolatest.cultivation.dao.CultivationPolygon;
import ru.kmatrokhin.betoolatest.openapi.model.CompanyDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public abstract class SpringTestBase {

  public Company testCompany() {
    return new Company()
        .setCountry("RU")
        .setAddress("test")
        .setCity("test city")
        .setPostalCode("00000")
        .setFiscalId("1111")
        .setName("test name")
        .setId(UUID.randomUUID());
  }

  public Cultivation testCultivation(Company company, CultivationPolygon cultivationPolygon) {
    return new Cultivation()
        .setCompany(company)
        .setName("test cultivation")
        .setAccountingYear(1900)
        .setCultivatedVariety("test variety")
        .setCreatedDate(LocalDateTime.now())
        .setModifiedDate(LocalDateTime.now())
        .setPolygon(cultivationPolygon);
  }

  public CultivationPolygon testCultivationPolygon() {
    return new CultivationPolygon()
        .setType("test type")
        .setCoordinates(List.of(
            new Double[] {0D, 0D},
            new Double[] {1D, 3000D},
            new Double[] {52412.123D, 545.32D}
        ));
  }

  public CompanyDTO testCompanyDTO() {
    final var companyDTO = new CompanyDTO();
    companyDTO.setAddress(JsonNullable.of("test address"));
    companyDTO.setCity(JsonNullable.of("test city"));
    companyDTO.setCountry("ru");
    companyDTO.setFiscalId("00000");
    companyDTO.setPostalCode("1111");
    companyDTO.setName("test name");
    return companyDTO;
  }

}
