package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import it.gov.pagopa.apiconfig.datamigration.entity.cfg.pk.ConfigurationKeysView;
import lombok.*;

import javax.persistence.*;

@IdClass(ConfigurationKeysView.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = "CONFIGURATION_KEYS")
@Builder(toBuilder = true)
public class ConfigurationKeys {

    @Id
    @Column(name = "CONFIG_CATEGORY", nullable = false, length = 255)
    private String configCategory;

    @Id
    @Column(name = "CONFIG_KEY", nullable = false, length = 255)
    private String configKey;

    @Column(name = "CONFIG_VALUE", nullable = false, length = 255)
    private String configValue;

    @Column(name = "CONFIG_DESCRIPTION", length = 255)
    private String configDescription;
}
