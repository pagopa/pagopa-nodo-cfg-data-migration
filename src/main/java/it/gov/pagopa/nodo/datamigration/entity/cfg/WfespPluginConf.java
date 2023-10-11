package it.gov.pagopa.nodo.datamigration.entity.cfg;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = "WFESP_PLUGIN_CONF")
@Builder(toBuilder = true)
public class WfespPluginConf {

    @Id
    @Column(name = "OBJ_ID", nullable = false)
    private Long id;

    @Column(name = "ID_SERV_PLUGIN", nullable = false, length = 35)
    private String idServPlugin;

    @Column(name = "PROFILO_PAG_CONST_STRING", length = 150)
    private String profiloPagConstString;

    @Column(name = "PROFILO_PAG_SOAP_RULE", length = 150)
    private String profiloPagSoapRule;

    @Column(name = "PROFILO_PAG_RPT_XPATH", length = 150)
    private String profiloPagRptXpath;

    @Column(name = "ID_BEAN")
    private String idBean;
}
