package DAL;

import ET.CategoriaET;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CategoriaDAL {

    //Carpeta donde se guardan los archivos .dat, si no se guarda en C se debe cambiar la ruta
    private String carpeta = "C:\\Biblioteca";

    //Archivo donde se guardan las categorias
    private String archivo = carpeta + "\\categorias.dat";

    private ArrayList<CategoriaET> categorias;

    //Constructor que crea la carpeta, valida el archivo y carga la lista en memoria
    public CategoriaDAL() {
        crearCarpeta();
        validarArchivo();
        categorias = cargar();
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
            System.out.println("El archivo categorias.dat no existe.");
            System.out.println("Creando archivo...");
            crearArchivo();
            return;
        }

        //Se verifica que sea realmente un archivo
        if (!archivoDAT.isFile()) {
            System.out.println("ERROR: categorias.dat no es un archivo valido.");
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
                System.out.println("ERROR: categorias.dat no contiene una lista valida.");
                crearArchivo();
                return;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: categorias.dat esta danado o no tiene un formato valido.");
            System.out.println("Se creara nuevamente el archivo.");
            crearArchivo();
        }
    }

    //Funcion que se va a encargar de crear el archivo con una lista vacia
    private void crearArchivo() {
        ArrayList<CategoriaET> listaVacia = new ArrayList<>();
        try {
            FileOutputStream archivoSalida = new FileOutputStream(archivo);
            ObjectOutputStream objetoSalida = new ObjectOutputStream(archivoSalida);
            objetoSalida.writeObject(listaVacia);
            objetoSalida.close();
            archivoSalida.close();
            System.out.println("Archivo categorias.dat creado correctamente.");
        } catch (IOException e) {
            System.out.println("ERROR al crear categorias.dat: " + e.getMessage());
        }
    }

    //Funcion que se va a encargar de guardar la lista completa en el archivo
    private void guardar() {
        try {
            FileOutputStream archivoSalida = new FileOutputStream(archivo);
            ObjectOutputStream objetoSalida = new ObjectOutputStream(archivoSalida);
            objetoSalida.writeObject(categorias);
            objetoSalida.close();
            archivoSalida.close();
        } catch (IOException e) {
            System.out.println("ERROR al guardar las categorias: " + e.getMessage());
        }
    }

    //Funcion que se va a encargar de cargar la lista desde el archivo al iniciar el sistema
    @SuppressWarnings("unchecked")
    private ArrayList<CategoriaET> cargar() {
        File archivoDAT = new File(archivo);
        try {
            FileInputStream archivoEntrada = new FileInputStream(archivoDAT);
            ObjectInputStream objetoEntrada = new ObjectInputStream(archivoEntrada);
            ArrayList<CategoriaET> lista = (ArrayList<CategoriaET>) objetoEntrada.readObject();
            objetoEntrada.close();
            archivoEntrada.close();
            return lista;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR al cargar las categorias: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    //Funcion que se va a encargar de agregar una categoria y validar que su id no se repita
    public void agregar(CategoriaET c) throws Exception {
        for (CategoriaET cat : categorias) {
            if (cat.getIdCategoria() == c.getIdCategoria()) {
                throw new Exception("Ya existe una categoria con ID " + c.getIdCategoria());
            }
        }
        categorias.add(c);
        guardar();
    }

    //Funcion que se va a encargar de buscar una categoria por su id, si no existe devuelve null
    public CategoriaET buscar(int idCategoria) {
        for (CategoriaET c : categorias) {
            if (c.getIdCategoria() == idCategoria) {
                return c;
            }
        }
        return null;
    }

    //Funcion que se va a encargar de devolver la lista con todos las categorias
    public ArrayList<CategoriaET> obtenerTodos() {
        return categorias;
    }

    //Funcion que se va a encargar de eliminar una categoria por su id
    public boolean eliminar(int idCategoria) {
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getIdCategoria() == idCategoria) {
                categorias.remove(i);
                guardar();
                return true;
            }
        }
        return false;
    }

    //Funcion que se va a encargar de editar los datos de una categoria que ya existe
    public boolean editar(int idCategoria, String nombreCategoria) {
        for (CategoriaET c : categorias) {
            if (c.getIdCategoria() == idCategoria) {
                c.setNombreCategoria(nombreCategoria);
                guardar();
                return true;
            }
        }
        return false;
    }
}