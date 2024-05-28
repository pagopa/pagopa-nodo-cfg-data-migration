package it.gov.pagopa.nodo.datamigration.entity.cfg;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.Immutable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;
import java.util.List;

@Table(name = "IBAN")
@Entity
@Immutable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Iban {

  @Id
  @Column(name = "OBJ_ID")
  private Long objId;

  @Column(name = "IBAN", nullable = false, unique = true)
  private String iban;

  @Column(name = "FISCAL_CODE", nullable = false)
  private String fiscalCode;

  @Column(name = "DUE_DATE", nullable = false)
  private Timestamp dueDate;

  @Column(name = "DESCRIPTION")
  @Comment("Not currently involved in business logic. For future use.")
  @Builder.Default
  private String description = "iban";

  @ToString.Exclude
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "fkIban")
  @EqualsAndHashCode.Exclude
  private List<IbanMaster> ibanMasters;

  @Column(name = "RAGIONE_SOCIALE")
  private String ragioneSociale;
}
