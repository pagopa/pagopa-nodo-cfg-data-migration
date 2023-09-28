package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import it.gov.pagopa.apiconfig.starter.util.NumericBooleanConverter;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Table(name = "INFORMATIVE_PA_MASTER")
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InformativePaMaster {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "ID_INFORMATIVA_PA", nullable = false, length = 35)
    private String idInformativaPa;

    @Column(name = "DATA_INIZIO_VALIDITA")
    private Timestamp dataInizioValidita;

    @Column(name = "DATA_PUBBLICAZIONE")
    private Timestamp dataPubblicazione;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_PA", nullable = false)
    @ToString.Exclude
    private Pa fkPa;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_BINARY_FILE")
    @ToString.Exclude
    private BinaryFile fkBinaryFile;

    @Column(name = "VERSIONE", length = 35)
    private String versione;

    @Column(name = "PAGAMENTI_PRESSO_PSP")
    @Convert(converter = NumericBooleanConverter.class)
    private Boolean pagamentiPressoPsp;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "fkInformativaPaMaster",
            cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<InformativePaDetail> details;
}