package ru.kmatrokhin.betoolatest.company.dao;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.kmatrokhin.betoolatest.cultivation.dao.Cultivation;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "company")
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
public class Company {
  @Id
  @GeneratedValue
  private UUID id;

  @Column(name = "address")
  private String address;

  @Column(name = "city")
  private String city;

  @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
  private Set<Cultivation> cultivations;

  @Column(name = "country")
  private String country;

  @Column(name = "fiscal_id")
  private String fiscalId;

  @Column(name = "name")
  private String name;

  @Column(name = "postal_code")
  private String postalCode;

  @Column(name = "deleted_date")
  private LocalDateTime deletedDate;
}
