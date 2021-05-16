package ru.kmatrokhin.betoolatest.openapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.kmatrokhin.betoolatest.SpringTestBase;
import ru.kmatrokhin.betoolatest.exception.ErrorCode;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@AutoConfigureMockMvc
class CompaniesApiTest extends SpringTestBase {
  private final MockMvc mockMvc;
  private final ObjectMapper objectMapper;

  @MockBean
  private CompaniesApiDelegate companiesApi;


  @Test
  @SneakyThrows
  void companiesCreate() {
    final var testDTO = testCompanyDTO();
    testDTO.setId(UUID.randomUUID());
    when(companiesApi.companiesCreate(any(), any()))
        .thenReturn(new ResponseEntity<>(testDTO, HttpStatus.OK));

    mockMvc.perform(post("/companies/")
        .content(objectMapper.writeValueAsString(testDTO))
        .contentType(MediaType.APPLICATION_JSON)
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").isString())
        .andExpect(jsonPath("$.fiscal_id").value(testDTO.getFiscalId()))
        .andReturn();
  }

  @Test
  @SneakyThrows
  void companiesCreateValidationFail() {
    final var testDTO = testCompanyDTO();
    testDTO.setId(UUID.randomUUID());
    testDTO.setCountry(null);

    when(companiesApi.companiesCreate(any(), any()))
        .thenReturn(new ResponseEntity<>(testDTO, HttpStatus.OK));

    mockMvc.perform(post("/companies/")
        .content(objectMapper.writeValueAsString(testDTO))
        .contentType(MediaType.APPLICATION_JSON)
    )
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.code").value(ErrorCode.OTHER_EXCEPTION.toString()))
        .andExpect(jsonPath("$.message").isString())
        .andReturn();

  }

  @Test
  @SneakyThrows
  void companiesDestroy() {
  }

  @Test
  @SneakyThrows
  void companiesList() {
//    doReturn(ResponseEntity.ok(List.of(
//        testCompanyDTO().setId(UUID.randomUUID()),
//        testCompanyDTO().setId(UUID.randomUUID()),
//        testCompanyDTO().setId(UUID.randomUUID())
//    ))).when(companiesApi).companiesList(anyString(), anyString()))
//    ;
//    mockMvc.perform(get("/companies/")
//        .content(testCompanyJSONNoCountry())
//        .contentType(MediaType.APPLICATION_JSON)
//    )
//        .andExpect(status().is4xxClientError())
//        .andExpect(jsonPath("$.code").value(ErrorCode.OTHER_EXCEPTION.toString()))
//        .andExpect(jsonPath("$.message").isString())
//        .andReturn();
  }

  @Test
  void companiesRetrieve() {
  }

  @Test
  void companiesUpdate() {
  }
}