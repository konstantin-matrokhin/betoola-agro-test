package ru.kmatrokhin.betoolatest.company.model;

import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Component;
import ru.kmatrokhin.betoolatest.company.dao.Company;
import ru.kmatrokhin.betoolatest.openapi.model.CompanyDTO;

@Component
public class CompanyConverter {

  public Company createCompanyFromDTO(CompanyDTO companyDTO) {
    return new Company()
        .setCity(companyDTO.getCity().orElse(null))
        .setAddress(companyDTO.getAddress().orElse(null))
        .setCountry(companyDTO.getCountry())
        .setName(companyDTO.getName())
        .setFiscalId(companyDTO.getFiscalId())
        .setPostalCode(companyDTO.getPostalCode());
  }

  public CompanyDTO createDTOFromCompany(Company company) {
    CompanyDTO dto = new CompanyDTO();
    dto.setCity(JsonNullable.of(company.getCity()));
    dto.setAddress(JsonNullable.of(company.getAddress()));
    dto.setCountry(company.getCountry());
    dto.setName(company.getName());
    dto.setFiscalId(company.getFiscalId());
    dto.setPostalCode(company.getPostalCode());
    dto.setId(company.getId());
    return dto;
  }

}
