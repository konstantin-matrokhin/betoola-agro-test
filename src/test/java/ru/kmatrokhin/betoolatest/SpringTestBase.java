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
        .setName("test name");
  }

  public Cultivation testCultivation(Company company, CultivationPolygon cultivationPolygon) {
    return new Cultivation()
        .setCompany(company)
        .setName("test cultivation")
        .setAccountingYear(1900)
        .setCultivatedVariety("test variety")
        .setPolygon(cultivationPolygon);
  }

  public CompanyDTO testCompanyDTO() {
    final var companyDTO = new CompanyDTO();
    companyDTO.setAddress(JsonNullable.of("test address"));
    companyDTO.setCity(JsonNullable.of("test city"));
    companyDTO.setCountry("ru");
    companyDTO.setFiscalId("00000");
    companyDTO.setPostalCode("1111");
    return companyDTO;
  }

  public String testCompanyJSON() {
    return "{"
        + "\"name\": \"Betoola S.r.l.\","
        + "\"address\": \"Viale Sabotino 8/B\","
        + "\"postal_code\": \"46100\","
        + "\"city\": \"Mantova\","
        + "\"country\": \"IT\","
        + "\"fiscal_id\": \"02595650207\""
        + "}";
  }

  public String testCompanyJSONNoCountry() {
    return "{"
        + "\"name\": \"Betoola S.r.l.\","
        + "\"address\": \"Viale Sabotino 8/B\","
        + "\"postal_code\": \"46100\","
        + "\"city\": \"Mantova\","
        + "\"fiscal_id\": \"02595650207\""
        + "}";
  }

}
