package BL;

import DAL.CategoriaDAL;
import ET.CategoriaET;
import java.util.ArrayList;

public class CategoriaBL {

    private CategoriaDAL dal;
    private LibroBL libroBL;
    private int maximo = 20;

    //Constructor que crea la DAL de categorias
    public CategoriaBL() {
        this.dal = new CategoriaDAL();
    }

    //Funcion que se va a encargar de recibir el LibroBL, se usa para bloquear la eliminacion de una categoria con libros
    public void setLibroBL(LibroBL libroBL) {
        this.libroBL = libroBL;
    }

    //Funcion que se va a encargar de validar y agregar una categoria
    public boolean agregar(int idCategoria, String nombreCategoria) throws Exception {

        if (nombreCategoria == null || nombreCategoria.isEmpty()) {
            throw new Exception("El nombre de la categoria no puede estar vacio.");
        }

        if (dal.obtenerTodos().size() >= maximo) {
            throw new Exception("Se alcanzo el maximo de " + maximo + " categorias.");
        }

        if (dal.buscar(idCategoria) != null) {
            throw new Exception("La categoria con ID " + idCategoria + " ya existe.");
        }

        CategoriaET categoria = new CategoriaET(idCategoria, nombreCategoria);
        dal.agregar(categoria);
        return true;
    }

    //Funcion que se va a encargar de buscar una categoria, si no existe devuelve null
    public CategoriaET buscar(int idCategoria) {
        return dal.buscar(idCategoria);
    }

    //Funcion que se va a encargar de listar todas las categorias
    public ArrayList<CategoriaET> listar() {
        return dal.obtenerTodos();
    }

    //Funcion que se va a encargar de validar y actualizar el nombre de una categoria
    public boolean actualizar(int idCategoria, String nuevoNombre) throws Exception {

        if (dal.buscar(idCategoria) == null) {
            throw new Exception("La categoria con ID " + idCategoria + " no existe.");
        }

        if (nuevoNombre == null || nuevoNombre.isEmpty()) {
            throw new Exception("El nombre de la categoria no puede estar vacio.");
        }

        return dal.editar(idCategoria, nuevoNombre);
    }

    //Funcion que se va a encargar de eliminar una categoria, no se permite si tiene libros
    public boolean eliminar(int idCategoria) throws Exception {

        if (dal.buscar(idCategoria) == null) {
            throw new Exception("La categoria con ID " + idCategoria + " no existe.");
        }

        if (libroBL != null && libroBL.existePorCategoria(idCategoria)) {
            throw new Exception("No se puede eliminar la categoria porque tiene libros.");
        }

        return dal.eliminar(idCategoria);
    }
}