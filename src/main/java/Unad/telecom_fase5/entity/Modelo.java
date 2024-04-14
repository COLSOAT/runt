package Unad.telecom_fase5.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "modelo")
public class Modelo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @NotNull
    @Column(name = "tipoDocumento")
    private String tipoDocumento;

    @Getter
    @Setter
    @NotNull
    @Column(name = "numeroIdentificacion")
    private String numeroIdentificacion;

     @Getter
    @Setter
    @NotNull
    @Column(name = "placaVehiculo")
    private String placaVehiculo;



}
