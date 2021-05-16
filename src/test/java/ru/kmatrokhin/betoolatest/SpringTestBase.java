package ru.kmatrokhin.betoolatest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kmatrokhin.betoolatest.company.dao.Company;
import ru.kmatrokhin.betoolatest.cultivation.dao.Cultivation;
import ru.kmatrokhin.betoolatest.cultivation.dao.CultivationPolygon;

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

}
