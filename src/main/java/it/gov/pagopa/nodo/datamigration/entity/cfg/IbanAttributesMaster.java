package it.gov.pagopa.nodo.datamigration.entity.cfg;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Table(name = "IBAN_ATTRIBUTES_MASTER")
@Entity
@Immutable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class IbanAttributesMaster {

  @Id
  @Column(name = "OBJ_ID")
  private Long objId;

  @Column(name = "FK_IBAN_MASTER", nullable = false, insertable = false, updatable = false)
  private Long fkIbanMaster;

  @Column(name = "FK_IBAN_ATTRIBUTE", nullable = false, insertable = false, updatable = false)
  private Long fkAttribute;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "FK_IBAN_MASTER", nullable = false)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private IbanMaster ibanMaster;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "FK_IBAN_ATTRIBUTE", nullable = false)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private IbanAttributes ibanAttributes;
}
