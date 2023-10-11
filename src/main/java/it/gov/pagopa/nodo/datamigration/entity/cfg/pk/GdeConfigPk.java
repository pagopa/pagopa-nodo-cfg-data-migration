package it.gov.pagopa.nodo.datamigration.entity.cfg.pk;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GdeConfigPk implements Serializable {

    private static final long serialVersionUID = -1229997088232760041L;

    private String primitiva;
    private String type;
}
