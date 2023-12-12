package it.gov.pagopa.nodo.datamigration.entity.cfg;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Immutable;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "IBAN_MASTER")
@Entity
@Immutable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class IbanMaster {

  public enum IbanStatus {
    ENABLED,
    DISABLED,
    NA
  }

  @Id
  @Column(name = "OBJ_ID")
  private Long objId;

  @Column(name = "FK_PA", nullable = false, insertable = false, updatable = false)
  private Long fkPa;

  @Column(name = "FK_IBAN", nullable = false, insertable = false, updatable = false)
  private Long fkIban;

  @Column(name = "STATE", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private IbanStatus ibanStatus = IbanStatus.NA;

  @Column(name = "INSERTED_DATE", nullable = false)
  private Timestamp insertedDate;

  @Column(name = "VALIDITY_DATE", nullable = false)
  private Timestamp validityDate;

  @Column(name = "DESCRIPTION")
  private String description;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "FK_PA", nullable = false)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Pa pa;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "FK_IBAN", nullable = false)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Iban iban;

  @ToString.Exclude
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "fkIbanMaster")
  @EqualsAndHashCode.Exclude
  private List<IbanAttributesMaster> ibanAttributesMasters;
}
