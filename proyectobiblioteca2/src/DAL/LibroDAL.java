package DAL;

import ET.LibroET;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class LibroDAL {

    //Carpeta donde se guardan los archivos .dat, si no se guarda en C se debe cambiar la ruta
    private String carpeta = "C:\\Biblioteca";

    //Archivo donde se guardan los libros
    private String archivo = carpeta + "\\libros.dat";

    private ArrayList<LibroET> libros;

    //Constructor que crea la carpeta, valida el archivo y carga la lista en memoria
    public LibroDAL() {
        crearCarpeta();
        validarArchivo();
        libros = cargar();
    }

    //Funcion que se va a encargar de crear la carpeta donde se guardan los archivos
    private void crearCarpeta() {
        File directorio = new File(carpeta);
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Carpeta " + carpeta + " creada correctamente.");
            } else {
                System.out.println("ERROR: No se pudo crear la carpeta " + carpeta);
            }
        }
    }

    //Funcion que se va a encargar de validar que el archivo sea creado sin problemas
    private void validarArchivo() {
        File archivoDAT = new File(archivo);

        //Si el archivo no existe, se crea
        if (!archivoDAT.exists()) {
            System.out.println("El archivo libros.dat no existe.");
            System.out.println("Creando archivo...");
            crearArchivo();
            return;
        }

        //Se verifica que sea realmente un archivo
        if (!archivoDAT.isFile()) {
            System.out.println("ERROR: libros.dat no es un archivo valido.");
            crearArchivo();
            return;
        }

        //Se verifica que pueda leerse y que contenga una lista
        try {
            FileInputStream archivoEntrada = new FileInputStream(archivoDAT);
            ObjectInputStream objetoEntrada = new ObjectInputStream(archivoEntrada);
            Object objeto = objetoEntrada.readObject();
            objetoEntrada.close();
            archivoEntrada.close();

            if (!(objeto instanceof ArrayList)) {
                System.out.println("ERROR: libros.dat no contiene una lista valida.");
                crearArchivo();
                return;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: libros.dat esta danado o no tiene un formato valido.");
            System.out.println("Se creara nuevamente el archivo.");
            crearArchivo();
        }
    }

    //Funcion que se va a encargar de crear el archivo con una lista vacia
    private void crearArchivo() {
        ArrayList<LibroET> listaVacia = new ArrayList<>();
        try {
            FileOutputStream archivoSalida = new FileOutputStream(archivo);
            ObjectOutputStream objetoSalida = new ObjectOutputStream(archivoSalida);
            objetoSalida.writeObject(listaVacia);
            objetoSalida.close();
            archivoSalida.close();
            System.out.println("Archivo libros.dat creado correctamente.");
        } catch (IOException e) {
            System.out.println("ERROR al crear libros.dat: " + e.getMessage());
        }
    }

    //Funcion que se va a encargar de guardar la lista completa en el archivo
    private void guardar() {
        try {
            FileOutputStream archivoSalida = new FileOutputStream(archivo);
            ObjectOutputStream objetoSalida = new ObjectOutputStream(archivoSalida);
            objetoSalida.writeObject(libros);
            objetoSalida.close();
            archivoSalida.close();
        } catch (IOException e) {
            System.out.println("ERROR al guardar los libros: " + e.getMessage());
        }
    }

    //Funcion que se va a encargar de cargar la lista desde el archivo al iniciar el sistema
    @SuppressWarnings("unchecked")
    private ArrayList<LibroET> cargar() {
        File archivoDAT = new File(archivo);
        try {
            FileInputStream archivoEntrada = new FileInputStream(archivoDAT);
            ObjectInputStream objetoEntrada = new ObjectInputStream(archivoEntrada);
            ArrayList<LibroET> lista = (ArrayList<LibroET>) objetoEntrada.readObject();
            objetoEntrada.close();
            archivoEntrada.close();
            return lista;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR al cargar los libros: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    //Funcion que se va a encargar de agregar un libro y validar que su id no se repita
    public void agregar(LibroET l) throws Exception {
        for (LibroET libro : libros) {
            if (libro.getIdLibro() == l.getIdLibro()) {
                throw new Exception("Ya existe un libro con ID " + l.getIdLibro());
            }
        }
        libros.add(l);
        guardar();
    }

    //Funcion que se va a encargar de buscar un libro por su id, si no existe devuelve null
    public LibroET buscar(int idLibro) {
        for (LibroET l : libros) {
            if (l.getIdLibro() == idLibro) {
                return l;
            }
        }
        return null;
    }

    //Funcion que se va a encargar de devolver la lista con todos los libros
    public ArrayList<LibroET> obtenerTodos() {
        return libros;
    }

    //Funcion que se va a encargar de eliminar un libro por su id
    public boolean eliminar(int idLibro) {
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getIdLibro() == idLibro) {
                libros.remove(i);
                guardar();
                return true;
            }
        }
        return false;
    }

    //Funcion que se va a encargar de editar los datos de un libro que ya existe
    public boolean editar(int idLibro, int idCategoria, String nombreLibro, int existencias, String nombreAutor, String apellido1, String apellido2) {

        for (LibroET l : libros) {
            if (l.getIdLibro() == idLibro) {
                l.setIdCategoria(idCategoria);
                l.setNombreLibro(nombreLibro);
                l.setExistencias(existencias);
                l.setNombreAutor(nombreAutor);
                l.setApellido1(apellido1);
                l.setApellido2(apellido2);
                guardar();
                return true;
            }
        }
        return false;
    }

    //Funcion que se va a encargar de actualizar las existencias de un libro, se usa al prestar (restar 1) y al devolver (sumar 1)
    public boolean actualizarExistencias(int idLibro, int existencias) {
        for (LibroET l : libros) {
            if (l.getIdLibro() == idLibro) {
                l.setExistencias(existencias);
                guardar();
                return true;
            }
        }
        return false;
    }

    //Funcion que se va a encargar de verificar si hay libros con esa categoria, para bloquear su eliminacion
    public boolean existePorCategoria(int idCategoria) {
        for (LibroET l : libros) {
            if (l.getIdCategoria() == idCategoria) {
                return true;
            }
        }
        return false;
    }
}