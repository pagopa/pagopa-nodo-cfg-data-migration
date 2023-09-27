package it.gov.pagopa.apiconfig.datamigration.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class DataMigrationDetails implements Serializable {

    private DataMigrationStatus intermediariPa;

    private DataMigrationStatus pa;

    private DataMigrationStatus stazioni;

    private DataMigrationStatus paStazioniPa;

    private DataMigrationStatus codifiche;

    private DataMigrationStatus codifichePa;

    private DataMigrationStatus binaryFile;

    private DataMigrationStatus ibanValidiPerPa;

    private DataMigrationStatus informativeContoAccreditoMaster;

    private DataMigrationStatus informativeContoAccreditoDetail;

    private DataMigrationStatus informativePaMaster;

    private DataMigrationStatus informativePaDetail;

    private DataMigrationStatus informativePaFasce;

    private DataMigrationStatus intermediariPsp;

    private DataMigrationStatus psp;

    private DataMigrationStatus canaliNodo;

    private DataMigrationStatus canali;

    private DataMigrationStatus tipiVersamento;

    private DataMigrationStatus canaleTipoVersamento;

    private DataMigrationStatus pspCanaleTipoVersamento;

    private DataMigrationStatus dizionarioMetadati;

    private DataMigrationStatus cdiMaster;

    private DataMigrationStatus cdiDetail;

    private DataMigrationStatus cdiFasciaCostoServizio;

    private DataMigrationStatus cdiInformazioniServizio;

    private DataMigrationStatus cdiPreferences;

    private DataMigrationStatus elencoServizi;

    private DataMigrationStatus cdsCategorie;

    private DataMigrationStatus cdsSoggetto;

    private DataMigrationStatus cdsServizio;

    private DataMigrationStatus cdsSoggettoServizio;

    private DataMigrationStatus configurationKeys;

    private DataMigrationStatus wfespPluginConn;

    private DataMigrationStatus ftpServer;

    private DataMigrationStatus pdd;

    private DataMigrationStatus gdeConfig;

    private DataMigrationStatus quadratureSched;
}
