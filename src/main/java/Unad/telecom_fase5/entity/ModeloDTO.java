package Unad.telecom_fase5.entity;

import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ModeloDTO {

    @Getter
    @Setter
    @NotEmpty
    private Long id;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String tipoDocumento;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String numeroIdentificacion;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    private String placaVehiculo;







}
