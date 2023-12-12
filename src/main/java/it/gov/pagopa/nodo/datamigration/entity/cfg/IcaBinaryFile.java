package it.gov.pagopa.nodo.datamigration.entity.cfg;

import jakarta.persistence.Lob;
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


@Table(name = "ICA_BINARY_FILE")
@Entity
@Immutable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class IcaBinaryFile {

  @Id
  @Column(name = "OBJ_ID")
  private Long objId;

  @Column(name = "ID_DOMINIO", nullable = false)
  private String idDominio;

  @Column(name = "FILE_CONTENT")
  @Lob
  @ToString.Exclude
  private byte[] fileContent;

  @Column(name = "FILE_HASH")
  @Lob
  @ToString.Exclude
  private byte[] fileHash;

  @Column(name = "FILE_SIZE", nullable = false)
  private Long fileSize;
}
