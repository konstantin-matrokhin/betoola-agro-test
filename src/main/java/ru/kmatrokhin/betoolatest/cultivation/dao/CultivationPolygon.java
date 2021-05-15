package ru.kmatrokhin.betoolatest.cultivation.dao;

import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.List;

@Embeddable
@TypeDef(name = "json", typeClass = JsonType.class)
public class CultivationPolygon {
  @Column(name = "polygon_type")
  private String type;

  @Column(name = "polygon_coordinates")
  @Type(type = "json")
  private List<Double[]> coordinates;
}
