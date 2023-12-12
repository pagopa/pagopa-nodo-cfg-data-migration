package it.gov.pagopa.nodo.datamigration.entity.cfg;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "IBAN_ATTRIBUTES")
@Entity
@Immutable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class IbanAttributes {

  @Id
  @Column(name = "OBJ_ID")
  private Long objId;

  @Column(name = "ATTRIBUTE_NAME", nullable = false, unique = true)
  private String attributeName;

  @Column(name = "ATTRIBUTE_DESCRIPTION", nullable = false)
  private String attributeDescription;
}
