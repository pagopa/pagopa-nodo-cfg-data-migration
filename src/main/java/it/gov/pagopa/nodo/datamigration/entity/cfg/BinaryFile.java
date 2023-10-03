package it.gov.pagopa.nodo.datamigration.entity.cfg;

import lombok.*;

import jakarta.persistence.*;

@Table(name = "BINARY_FILE")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class BinaryFile {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "FILE_CONTENT", nullable = false)
    @ToString.Exclude
    private byte[] fileContent;

    @Column(name = "FILE_HASH", nullable = false)
    @ToString.Exclude
    private byte[] fileHash;

    @Column(name = "FILE_SIZE", nullable = false)
    private Long fileSize;

    @Column(name = "SIGNATURE_TYPE", length = 30)
    private String signatureType;

    @Lob
    @Column(name = "XML_FILE_CONTENT")
    private String xmlFileContent;
}
