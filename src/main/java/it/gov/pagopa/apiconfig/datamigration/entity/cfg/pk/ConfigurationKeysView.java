package it.gov.pagopa.apiconfig.datamigration.entity.cfg.pk;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;

public class ConfigurationKeysView implements Serializable {

    private static final long serialVersionUID = -6198929539099423043L;

    @Id
    @Column(name = "CONFIG_CATEGORY", nullable = false)
    private String configCategory;

    @Id
    @Column(name = "CONFIG_KEY", nullable = false)
    private String configKey;
}
