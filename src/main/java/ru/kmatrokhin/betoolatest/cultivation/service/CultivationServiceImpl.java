package ru.kmatrokhin.betoolatest.cultivation.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.kmatrokhin.betoolatest.openapi.api.CultivationsApiDelegate;
import ru.kmatrokhin.betoolatest.openapi.model.CultivationDTO;

import java.util.List;
import java.util.Optional;

@Service
public class CultivationServiceImpl implements CultivationsApiDelegate {

  @Override
  public ResponseEntity<List<CultivationDTO>> companiesCultivationsList(String companyId, Optional<String> X_CORRELATION_ID, Optional<String> name) {
    return CultivationsApiDelegate.super.companiesCultivationsList(companyId, X_CORRELATION_ID, name);
  }
}
