package BL;

import DAL.UsuarioDAL;
import ET.UsuarioET;
import java.util.ArrayList;

public class UsuarioBL {

    private UsuarioDAL dal;
    private PrestamoBL prestamoBL;
    private int maximo = 20;

    //Constructor que crea la DAL de usuarios
    public UsuarioBL() {
        this.dal = new UsuarioDAL();
    }

    //Funcion que se va a encargar de recibir el PrestamoBL, se usa para bloquear la eliminacion de un usuario con prestamos
    public void setPrestamoBL(PrestamoBL prestamoBL) {
        this.prestamoBL = prestamoBL;
    }

    //Funcion que se va a encargar de validar y agregar un usuario
    public boolean agregar(int idUsuario, String nombreUsuario, String apellidoUsuario1, String apellidoUsuario2) throws Exception {

        validarTextos(nombreUsuario, apellidoUsuario1, apellidoUsuario2);

        if (dal.obtenerTodos().size() >= maximo) {
            throw new Exception("Se alcanzo el maximo de " + maximo + " usuarios.");
        }

        if (dal.buscar(idUsuario) != null) {
            throw new Exception("El usuario con ID " + idUsuario + " ya existe.");
        }

        UsuarioET usuario = new UsuarioET(idUsuario, nombreUsuario, apellidoUsuario1, apellidoUsuario2);
        dal.agregar(usuario);
        return true;
    }

    //Funcion que se va a encargar de validar que los textos del usuario no esten vacios
    private void validarTextos(String nombreUsuario, String apellidoUsuario1, String apellidoUsuario2) throws Exception {

        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            throw new Exception("El nombre del usuario no puede estar vacio.");
        }

        if (apellidoUsuario1 == null || apellidoUsuario1.isEmpty()) {
            throw new Exception("El primer apellido no puede estar vacio.");
        }

        if (apellidoUsuario2 == null || apellidoUsuario2.isEmpty()) {
            throw new Exception("El segundo apellido no puede estar vacio.");
        }
    }

    //Funcion que se va a encargar de buscar un usuario, si no existe devuelve null
    public UsuarioET buscar(int idUsuario) {
        return dal.buscar(idUsuario);
    }

    //Funcion que se va a encargar de listar todos los usuarios
    public ArrayList<UsuarioET> listar() {
        return dal.obtenerTodos();
    }

    //Funcion que se va a encargar de validar y actualizar los datos de un usuario
    public boolean actualizar(int idUsuario, String nombreUsuario, String apellidoUsuario1, String apellidoUsuario2) throws Exception {

        if (dal.buscar(idUsuario) == null) {
            throw new Exception("El usuario con ID " + idUsuario + " no existe.");
        }

        validarTextos(nombreUsuario, apellidoUsuario1, apellidoUsuario2);

        return dal.editar(idUsuario, nombreUsuario, apellidoUsuario1, apellidoUsuario2);
    }

    //Funcion que se va a encargar de eliminar un usuario, no se permite si tiene prestamos activos
    public boolean eliminar(int idUsuario) throws Exception {

        if (dal.buscar(idUsuario) == null) {
            throw new Exception("El usuario con ID " + idUsuario + " no existe.");
        }

        if (prestamoBL != null && prestamoBL.contarPorUsuario(idUsuario) > 0) {
            throw new Exception("No se puede eliminar el usuario porque tiene prestamos activos.");
        }

        return dal.eliminar(idUsuario);
    }
}