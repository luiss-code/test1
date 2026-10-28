package ET;

import java.io.Serializable;

public class UsuarioET implements Serializable {

    private int idUsuario;
    private String nombreUsuario;
    private String apellidoUsuario1;
    private String apellidoUsuario2;

    public UsuarioET(int idUsuario, String nombreUsuario,
            String apellidoUsuario1, String apellidoUsuario2) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario1 = apellidoUsuario1;
        this.apellidoUsuario2 = apellidoUsuario2;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario1() {
        return apellidoUsuario1;
    }

    public void setApellidoUsuario1(String apellidoUsuario1) {
        this.apellidoUsuario1 = apellidoUsuario1;
    }

    public String getApellidoUsuario2() {
        return apellidoUsuario2;
    }

    public void setApellidoUsuario2(String apellidoUsuario2) {
        this.apellidoUsuario2 = apellidoUsuario2;
    }

    @Override
    public String toString() {
        return "ID Usuario: " + idUsuario
                + " | Nombre: " + nombreUsuario + " "
                + apellidoUsuario1 + " " + apellidoUsuario2;
    }
}