package ET;

import java.io.Serializable;

public class PrestamoET implements Serializable {

    private int idPrestamo;
    private int idUsuario;
    private int idLibro;
    private int fechaInicio;
    private int fechaDevolucion;

    public PrestamoET(int idPrestamo, int idUsuario, int idLibro,
            int fechaInicio, int fechaDevolucion) {
        this.idPrestamo = idPrestamo;
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
        this.fechaInicio = fechaInicio;
        this.fechaDevolucion = fechaDevolucion;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public int getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(int fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(int fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    @Override
    public String toString() {
        return "ID Prestamo: " + idPrestamo
                + "\nID Usuario: " + idUsuario
                + "\nID Libro: " + idLibro
                + "\nFecha inicio: " + fechaInicio
                + "\nFecha limite de devolucion: " + fechaDevolucion;
    }
}
