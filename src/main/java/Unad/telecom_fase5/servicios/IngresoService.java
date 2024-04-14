package Unad.telecom_fase5.servicios;

import Unad.telecom_fase5.entity.Ingreso;
import Unad.telecom_fase5.entity.IngresoDTO;
import Unad.telecom_fase5.entity.Modelo;
import Unad.telecom_fase5.repository.IngresoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngresoService {
    public final IngresoRepository ingresoRepository;


    public IngresoService(IngresoRepository ingresoRepository) {
        this.ingresoRepository = ingresoRepository;
    }

    public Ingreso guardarIngreso(IngresoDTO ingresoDTO) {
        // Crear una instancia de Modelo a partir de ModeloDTO
        Ingreso ingreso = new Ingreso();
        ingreso.setNombre(ingresoDTO.getNombre());


        // Guardar el modelo en la base de datos y devolver la entidad guardada
        return ingresoRepository.save(ingreso);
    }

    public List<Ingreso> obtenerIngresos() {
        // Obtener todos los modelos de la base de datos
        return ingresoRepository.findAll();
    }

    public void eliminarTodosLosRegistrosDeIngreso() {
        ingresoRepository.deleteAll();
    }
}
