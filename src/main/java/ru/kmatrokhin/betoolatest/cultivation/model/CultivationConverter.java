package ru.kmatrokhin.betoolatest.cultivation.model;

import org.springframework.stereotype.Component;
import ru.kmatrokhin.betoolatest.company.dao.Company;
import ru.kmatrokhin.betoolatest.cultivation.dao.Cultivation;
import ru.kmatrokhin.betoolatest.cultivation.dao.CultivationPolygon;
import ru.kmatrokhin.betoolatest.openapi.model.CultivationDTO;
import ru.kmatrokhin.betoolatest.openapi.model.GeometryFieldDTO;

import javax.validation.ValidationException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CultivationConverter {

  public Cultivation createCultivationFromDTO(CultivationDTO cultivationDTO, Company company) {
    return new Cultivation()
        .setName(cultivationDTO.getName())
        .setAccountingYear(cultivationDTO.getAccountingYear())
        .setCultivatedVariety(cultivationDTO.getCultivatedVariety())
        .setCompany(company)
        .setPolygon(createPolygonFromDTO(cultivationDTO.getPolygon()))
        .setModifiedDate(cultivationDTO.getModified().atZone(ZoneId.of("UTC")).toLocalDateTime())
        .setCreatedDate(cultivationDTO.getCreated().atZone(ZoneId.of("UTC")).toLocalDateTime());
  }

  public CultivationDTO createDTOFromCultivation(Cultivation cultivation) {
    final var cultivationDTO = new CultivationDTO();
    cultivationDTO.setName(cultivation.getName());
    cultivationDTO.setAccountingYear(cultivation.getAccountingYear());
    cultivationDTO.setCultivatedVariety(cultivation.getCultivatedVariety());
    cultivationDTO.setPolygon(createDTOFromPolygon(cultivation.getPolygon()));
    cultivationDTO.setModified(cultivation.getModifiedDate().toInstant(ZoneOffset.UTC));
    cultivationDTO.setCreated(cultivation.getCreatedDate().toInstant(ZoneOffset.UTC));
    cultivationDTO.setId(cultivation.getId());
    return cultivationDTO;
  }

  public CultivationPolygon createPolygonFromDTO(GeometryFieldDTO geometryFieldDTO) {
    final var coordinates = geometryFieldDTO.getCoordinates()
        .stream()
        .peek(coordinateDTO -> {
          if (coordinateDTO.size() != 2) {
            throw new ValidationException("coordinate must contain both lnt and lat, but got " + coordinateDTO);
          }
        })
        .map(coordinateDTO -> new Double[] {
            Double.valueOf(coordinateDTO.get(0)),
            Double.valueOf(coordinateDTO.get(1))
        }).collect(Collectors.toList());

    return new CultivationPolygon()
        .setType(geometryFieldDTO.getType())
        .setCoordinates(coordinates);
  }

  public GeometryFieldDTO createDTOFromPolygon(CultivationPolygon polygon) {
    final var polygonDTO = new GeometryFieldDTO();
    final var coordinates = polygon.getCoordinates()
        .stream()
        .map(coordinate -> List.of(coordinate[0].floatValue(), coordinate[1].floatValue()))
        .collect(Collectors.toList());

    polygonDTO.setType(polygon.getType());
    polygonDTO.setCoordinates(coordinates);
    return polygonDTO;
  };

}
