package it.gov.pagopa.nodo.datamigration.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor//(access = AccessLevel.PRIVATE)
@ToString
@Slf4j
public class DataMigrationDetails implements Serializable {

    private DataMigrationStatus intermediariPa;

    private DataMigrationStatus pa;

    private DataMigrationStatus stazioni;

    private DataMigrationStatus paStazioniPa;

    private DataMigrationStatus codifiche;

    private DataMigrationStatus codifichePa;

    private DataMigrationStatus binaryFile;

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

    private DataMigrationStatus wfespPluginConf;

    private DataMigrationStatus ftpServers;

    private DataMigrationStatus pdd;

    private DataMigrationStatus gdeConfig;

    private DataMigrationStatus quadratureSched;


    public DataMigrationDetails(String jsonContent) {
        try {
            DataMigrationDetails details = new ObjectMapper().readValue(jsonContent, DataMigrationDetails.class);
            this.setIntermediariPa(details.getIntermediariPa());
            this.setPa(details.getPa());
            this.setStazioni(details.getStazioni());
            this.setPaStazioniPa(details.getPaStazioniPa());
            this.setCodifiche(details.getCodifiche());
            this.setCodifichePa(details.getCodifichePa());
            this.setBinaryFile(details.getBinaryFile());
            this.setInformativeContoAccreditoMaster(details.getInformativeContoAccreditoMaster());
            this.setInformativeContoAccreditoDetail(details.getInformativeContoAccreditoDetail());
            this.setInformativePaMaster(details.getInformativePaMaster());
            this.setInformativePaDetail(details.getInformativePaDetail());
            this.setInformativePaFasce(details.getInformativePaFasce());
            this.setIntermediariPsp(details.getIntermediariPsp());
            this.setPsp(details.getPsp());
            this.setCanaliNodo(details.getCanaliNodo());
            this.setCanali(details.getCanali());
            this.setTipiVersamento(details.getTipiVersamento());
            this.setCanaleTipoVersamento(details.getCanaleTipoVersamento());
            this.setPspCanaleTipoVersamento(details.getPspCanaleTipoVersamento());
            this.setDizionarioMetadati(details.getDizionarioMetadati());
            this.setCdiMaster(details.getCdiMaster());
            this.setCdiDetail(details.getCdiDetail());
            this.setCdiFasciaCostoServizio(details.getCdiFasciaCostoServizio());
            this.setCdiInformazioniServizio(details.getCdiInformazioniServizio());
            this.setCdiPreferences(details.getCdiPreferences());
            this.setElencoServizi(details.getElencoServizi());
            this.setCdsCategorie(details.getCdsCategorie());
            this.setCdsSoggetto(details.getCdsSoggetto());
            this.setCdsServizio(details.getCdsServizio());
            this.setCdsSoggettoServizio(details.getCdsSoggettoServizio());
            this.setConfigurationKeys(details.getConfigurationKeys());
            this.setWfespPluginConf(details.getWfespPluginConf());
            this.setFtpServers(details.getFtpServers());
            this.setPdd(details.getPdd());
            this.setGdeConfig(details.getGdeConfig());
            this.setQuadratureSched(details.getQuadratureSched());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
