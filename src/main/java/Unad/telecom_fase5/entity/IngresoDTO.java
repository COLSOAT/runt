package Unad.telecom_fase5.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class IngresoDTO {
    @Getter
    @Setter
    @NotEmpty
    private Long id;

    @Getter
    @Setter
    @NotNull
    private String nombre;
}
