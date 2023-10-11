cat template/DELETE_ALL_DATA.sql \
    data/quadrature_sched.sql \
    data/intermediari_pa.sql \
    data/pa.sql \
    > results/00_INSERT_ALL_DATA.sql

cat data/stazioni.sql \
    data/pa_stazione_pa.sql \
    data/codifiche.sql \
    data/codifiche_pa.sql \
    > results/01_INSERT_ALL_DATA.sql

cat data/binary_file.sql \
    > results/02_INSERT_ALL_DATA.sql

cat data/informative_conto_accredito_master.sql \
    data/informative_conto_accredito_detail.sql \
    data/informative_pa_master.sql \
    data/informative_pa_detail.sql \
    data/informative_pa_fasce.sql \
    > results/03_INSERT_ALL_DATA.sql

cat data/intermediari_psp.sql \
    data/psp.sql \
    data/wfesp_plugin_conf.sql \
    data/canali_nodo.sql \
    data/canali.sql \
    > results/04_INSERT_ALL_DATA.sql

cat data/tipi_versamento.sql \
    data/canale_tipo_versamento.sql \
    data/psp_canale_tipo_versamento.sql \
    data/dizionario_metadati.sql \
    > results/05_INSERT_ALL_DATA.sql

cat data/cdi_master.sql \
    data/cdi_detail.sql \
    data/cdi_fascia_costo_servizio.sql \
    data/cdi_informazioni_servizio.sql \
    > results/06_INSERT_ALL_DATA.sql

cat data/cdi_preferences.sql \
    data/elenco_servizi.sql \
    data/cds_categorie.sql \
    data/cds_soggetto.sql \
    data/cds_servizio.sql \
    data/cds_soggetto_servizio.sql \
    > results/07_INSERT_ALL_DATA.sql

cat data/configuration_keys.sql \
    data/ftp_servers.sql \
    data/pdd.sql \
    data/gde_config.sql \
    template/SEQUENCES.sql \
    > results/08_INSERT_ALL_DATA.sql
