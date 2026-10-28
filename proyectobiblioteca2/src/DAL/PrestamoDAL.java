package DAL;

import ET.PrestamoET;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PrestamoDAL {

    //Carpeta donde se guardan los archivos .dat, si no se guarda en C se debe cambiar la ruta
    private String carpeta = "C:\\Biblioteca";

    //Archivo donde se guardan los prestamos
    private String archivo = carpeta + "\\prestamos.dat";

    private ArrayList<PrestamoET> prestamos;

    //Constructor que crea la carpeta, valida el archivo y carga la lista en memoria
    public PrestamoDAL() {
        crearCarpeta();
        validarArchivo();
        prestamos = cargar();
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
            System.out.println("El archivo prestamos.dat no existe.");
            System.out.println("Creando archivo...");
            crearArchivo();
            return;
        }

        //Se verifica que sea realmente un archivo
        if (!archivoDAT.isFile()) {
            System.out.println("ERROR: prestamos.dat no es un archivo valido.");
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
                System.out.println("ERROR: prestamos.dat no contiene una lista valida.");
                crearArchivo();
                return;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: prestamos.dat esta danado o no tiene un formato valido.");
            System.out.println("Se creara nuevamente el archivo.");
            crearArchivo();
        }
    }

    //Funcion que se va a encargar de crear el archivo con una lista vacia
    private void crearArchivo() {
        ArrayList<PrestamoET> listaVacia = new ArrayList<>();
        try {
            FileOutputStream archivoSalida = new FileOutputStream(archivo);
            ObjectOutputStream objetoSalida = new ObjectOutputStream(archivoSalida);
            objetoSalida.writeObject(listaVacia);
            objetoSalida.close();
            archivoSalida.close();
            System.out.println("Archivo prestamos.dat creado correctamente.");
        } catch (IOException e) {
            System.out.println("ERROR al crear prestamos.dat: " + e.getMessage());
        }
    }

    //Funcion que se va a encargar de guardar la lista completa en el archivo
    private void guardar() {
        try {
            FileOutputStream archivoSalida = new FileOutputStream(archivo);
            ObjectOutputStream objetoSalida = new ObjectOutputStream(archivoSalida);
            objetoSalida.writeObject(prestamos);
            objetoSalida.close();
            archivoSalida.close();
        } catch (IOException e) {
            System.out.println("ERROR al guardar los prestamos: " + e.getMessage());
        }
    }

    //Funcion que se va a encargar de cargar la lista desde el archivo al iniciar el sistema
    @SuppressWarnings("unchecked")
    private ArrayList<PrestamoET> cargar() {
        File archivoDAT = new File(archivo);
        try {
            FileInputStream archivoEntrada = new FileInputStream(archivoDAT);
            ObjectInputStream objetoEntrada = new ObjectInputStream(archivoEntrada);
            ArrayList<PrestamoET> lista = (ArrayList<PrestamoET>) objetoEntrada.readObject();
            objetoEntrada.close();
            archivoEntrada.close();
            return lista;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR al cargar los prestamos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    //Funcion que se va a encargar de agregar un prestamo y validar que su id no se repita
    public void agregar(PrestamoET p) throws Exception {
        for (PrestamoET prestamo : prestamos) {
            if (prestamo.getIdPrestamo() == p.getIdPrestamo()) {
                throw new Exception("Ya existe un prestamo con ID " + p.getIdPrestamo());
            }
        }
        prestamos.add(p);
        guardar();
    }

    //Funcion que se va a encargar de buscar un prestamo por su id, si no existe devuelve null
    public PrestamoET buscar(int idPrestamo) {
        for (PrestamoET p : prestamos) {
            if (p.getIdPrestamo() == idPrestamo) {
                return p;
            }
        }
        return null;
    }

    //Funcion que se va a encargar de devolver la lista con todos los prestamos
    public ArrayList<PrestamoET> obtenerTodos() {
        return prestamos;
    }

    //Funcion que se va a encargar de eliminar un prestamo por su id, se usa en la devolucion
    public boolean eliminar(int idPrestamo) {
        for (int i = 0; i < prestamos.size(); i++) {
            if (prestamos.get(i).getIdPrestamo() == idPrestamo) {
                prestamos.remove(i);
                guardar();
                return true;
            }
        }
        return false;
    }

    //Funcion que se va a encargar de contar los prestamos activos de un usuario, el maximo es 3
    public int contarPorUsuario(int idUsuario) {
        int cantidad = 0;
        for (PrestamoET p : prestamos) {
            if (p.getIdUsuario() == idUsuario) {
                cantidad++;
            }
        }
        return cantidad;
    }

    //Funcion que se va a encargar de verificar si un libro tiene prestamos activos, para bloquear su eliminacion
    public boolean existePorLibro(int idLibro) {
        for (PrestamoET p : prestamos) {
            if (p.getIdLibro() == idLibro) {
                return true;
            }
        }
        return false;
    }

    //Funcion que se va a encargar de verificar si el usuario ya tiene prestado ese mismo libro
    public boolean existeUsuarioLibro(int idUsuario, int idLibro) {
        for (PrestamoET p : prestamos) {
            if (p.getIdUsuario() == idUsuario && p.getIdLibro() == idLibro) {
                return true;
            }
        }
        return false;
    }
}