package ru.kmatrokhin.betoolatest.cultivation.dao;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import ru.kmatrokhin.betoolatest.company.dao.Company;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "cultivation")
@DynamicInsert
@DynamicUpdate
@Accessors(chain = true)
public class Cultivation {
  @Id
  @GeneratedValue
  private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "accounting_year")
  private Integer accountingYear;

  @Column(name = "cultivated_variety")
  private String cultivatedVariety;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;

  @Column(name = "created_date")
  private LocalDateTime createdDate;

  @Column(name = "modified_date")
  private LocalDateTime modifiedDate;

  @Embedded
  private CultivationPolygon polygon;
}
