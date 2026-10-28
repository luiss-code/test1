package BL;

import DAL.LibroDAL;
import ET.LibroET;
import java.util.ArrayList;

public class LibroBL {

    private LibroDAL dal;
    private CategoriaBL categoriaBL;
    private PrestamoBL prestamoBL;
    private int maximo = 20;
    private int maximoExistencias = 20;

    //Constructor que crea la DAL de libros y se conecta con CategoriaBL
    public LibroBL(CategoriaBL categoriaBL) {
        this.categoriaBL = categoriaBL;
        this.dal = new LibroDAL();
        this.categoriaBL.setLibroBL(this);
    }

    //Funcion que se va a encargar de recibir el PrestamoBL, se usa para bloquear la eliminacion de un libro con prestamos
    public void setPrestamoBL(PrestamoBL prestamoBL) {
        this.prestamoBL = prestamoBL;
    }

    //Funcion que se va a encargar de validar y agregar un libro
    public boolean agregar(int idLibro, int idCategoria, String nombreLibro, int existencias, String nombreAutor, String apellido1, String apellido2) throws Exception {

        validarTextos(nombreLibro, nombreAutor, apellido1, apellido2);

        if (dal.obtenerTodos().size() >= maximo) {
            throw new Exception("Se alcanzo el maximo de " + maximo + " libros.");
        }

        if (dal.buscar(idLibro) != null) {
            throw new Exception("El libro con ID " + idLibro + " ya existe.");
        }

        if (categoriaBL.buscar(idCategoria) == null) {
            throw new Exception("La categoria con ID " + idCategoria + " no existe, primero debe crearla.");
        }

        if (existencias < 1 || existencias > maximoExistencias) {
            throw new Exception("Las existencias deben estar entre 1 y " + maximoExistencias + ".");
        }

        LibroET libro = new LibroET(idLibro, idCategoria, nombreLibro, existencias, nombreAutor, apellido1, apellido2);
        dal.agregar(libro);
        return true;
    }

    //Funcion que se va a encargar de validar que los textos del libro no esten vacios
    private void validarTextos(String nombreLibro, String nombreAutor, String apellido1, String apellido2) throws Exception {

        if (nombreLibro == null || nombreLibro.isEmpty()) {
            throw new Exception("El nombre del libro no puede estar vacio.");
        }

        if (nombreAutor == null || nombreAutor.isEmpty()) {
            throw new Exception("El nombre del autor no puede estar vacio.");
        }

        if (apellido1 == null || apellido1.isEmpty()) {
            throw new Exception("El primer apellido del autor no puede estar vacio.");
        }

        if (apellido2 == null || apellido2.isEmpty()) {
            throw new Exception("El segundo apellido del autor no puede estar vacio.");
        }
    }

    //Funcion que se va a encargar de buscar un libro, si no existe devuelve null
    public LibroET buscar(int idLibro) {
        return dal.buscar(idLibro);
    }

    //Funcion que se va a encargar de listar todos los libros
    public ArrayList<LibroET> listar() {
        return dal.obtenerTodos();
    }

    //Funcion que se va a encargar de validar y actualizar los datos de un libro
    public boolean actualizar(int idLibro, int idCategoria, String nombreLibro, int existencias, String nombreAutor, String apellido1, String apellido2) throws Exception {

        if (dal.buscar(idLibro) == null) {
            throw new Exception("El libro con ID " + idLibro + " no existe.");
        }

        validarTextos(nombreLibro, nombreAutor, apellido1, apellido2);

        if (categoriaBL.buscar(idCategoria) == null) {
            throw new Exception("La categoria con ID " + idCategoria + " no existe.");
        }

        if (existencias < 0 || existencias > maximoExistencias) {
            throw new Exception("Las existencias deben estar entre 0 y " + maximoExistencias + ".");
        }

        return dal.editar(idLibro, idCategoria, nombreLibro, existencias, nombreAutor, apellido1, apellido2);
    }

    //Funcion que se va a encargar de eliminar un libro, no se permite si tiene prestamos activos
    public boolean eliminar(int idLibro) throws Exception {

        if (dal.buscar(idLibro) == null) {
            throw new Exception("El libro con ID " + idLibro + " no existe.");
        }

        if (prestamoBL != null && prestamoBL.existePorLibro(idLibro)) {
            throw new Exception("No se puede eliminar el libro porque tiene prestamos activos.");
        }

        return dal.eliminar(idLibro);
    }

    //Funcion que se va a encargar de restar una existencia cuando se presta un libro
    public void restarExistencia(int idLibro) throws Exception {

        LibroET libro = dal.buscar(idLibro);

        if (libro == null) {
            throw new Exception("El libro con ID " + idLibro + " no existe.");
        }

        if (libro.getExistencias() <= 0) {
            throw new Exception("El libro no tiene existencias disponibles.");
        }

        dal.actualizarExistencias(idLibro, libro.getExistencias() - 1);
    }

    //Funcion que se va a encargar de sumar una existencia cuando se devuelve un libro, sin superar el maximo
    public void sumarExistencia(int idLibro) throws Exception {

        LibroET libro = dal.buscar(idLibro);

        if (libro == null) {
            throw new Exception("El libro con ID " + idLibro + " no existe.");
        }

        if (libro.getExistencias() < maximoExistencias) {
            dal.actualizarExistencias(idLibro, libro.getExistencias() + 1);
        }
    }

    //Funcion que se va a encargar de verificar si hay libros con esa categoria
    public boolean existePorCategoria(int idCategoria) {
        return dal.existePorCategoria(idCategoria);
    }
}