package Unad.telecom_fase5.servicios;

import Unad.telecom_fase5.entity.Modelo;
import Unad.telecom_fase5.entity.ModeloDTO;
import Unad.telecom_fase5.repository.ModeloRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeloService {
    public final ModeloRepository modeloRepository;


    public ModeloService(ModeloRepository modeloRepository) {
        this.modeloRepository = modeloRepository;
    }

    public Modelo guardarModelo(ModeloDTO modeloDTO) {
        // Crear una instancia de Modelo a partir de ModeloDTO
        Modelo modelo = new Modelo();
        modelo.setTipoDocumento(modeloDTO.getTipoDocumento());
        modelo.setNumeroIdentificacion(modeloDTO.getNumeroIdentificacion());
        modelo.setPlacaVehiculo(modeloDTO.getPlacaVehiculo());


        // Guardar el modelo en la base de datos y devolver la entidad guardada
        return modeloRepository.save(modelo);
    }

    public List<Modelo> obtenerTodosLosModelos() {
        // Obtener todos los modelos de la base de datos
        return modeloRepository.findAll();
    }
    public void eliminarModelos() {
        modeloRepository.deleteAll();
    }
}
