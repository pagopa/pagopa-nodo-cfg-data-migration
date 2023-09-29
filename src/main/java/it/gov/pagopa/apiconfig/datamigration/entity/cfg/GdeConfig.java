package it.gov.pagopa.apiconfig.datamigration.entity.cfg;

import it.gov.pagopa.apiconfig.datamigration.entity.cfg.pk.GdeConfigPk;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "GDE_CONFIG")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@IdClass(GdeConfigPk.class)
public class GdeConfig {

    @Id
    @Column(name = "PRIMITIVA", nullable = false)
    private String primitiva;

    @Column(name = "TYPE", nullable = false)
    private String type;

    @Column(name = "EVENT_HUB")
    private Integer eventHubEnabled;

    @Column(name = "EVENT_HUB_PAYLOAD")
    private Integer eventHubPayloadEnabled;
}
