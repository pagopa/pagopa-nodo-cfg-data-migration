package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Immutable;

@Table(name = "IBAN_VALIDI_PER_PA")
@Entity
@Immutable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString

public class IbanValidiPerPa {

    @Id
    @Column(name = "OBJ_ID")
    private Long objId;

    @Column(name = "FK_PA", nullable = false, insertable = false, updatable = false)
    private Long fkPa;

    @Column(name = "IBAN_ACCREDITO")
    private String ibanAccredito;

    @Column(name = "DATA_INIZIO_VALIDITA")
    private Timestamp dataInizioValidita;

    @Column(name = "DATA_PUBBLICAZIONE")
    private Timestamp dataPubblicazione;

    @Column(name = "RAGIONE_SOCIALE")
    private String ragioneSociale;

    @Column(name = "ID_MERCHANT")
    private String idMerchant;

    @Column(name = "ID_BANCA_SELLER")
    private String idBancaSeller;

    @Column(name = "CHIAVE_AVVIO")
    private String chiaveAvvio;

    @Column(name = "CHIAVE_ESITO")
    private String chiaveEsito;

    @Column(name = "MASTER_OBJ")
    private Long masterObj;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_PA", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Pa pa;
}