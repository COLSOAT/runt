package Unad.telecom_fase5.entity;

public class CobroRequest {
    private double monto;
    private String descripcion;
    // Agrega otros campos según sea necesario

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
