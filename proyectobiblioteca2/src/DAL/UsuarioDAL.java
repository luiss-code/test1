package DAL;

import ET.UsuarioET;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class UsuarioDAL {

    //Carpeta donde se guardan los archivos .dat, si no se guarda en C se debe cambiar la ruta
    private String carpeta = "C:\\Biblioteca";

    //Archivo donde se guardan los usuarios
    private String archivo = carpeta + "\\usuarios.dat";

    private ArrayList<UsuarioET> usuarios;

    //Constructor que crea la carpeta, valida el archivo y carga la lista en memoria
    public UsuarioDAL() {
        crearCarpeta();
        validarArchivo();
        usuarios = cargar();
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
            System.out.println("El archivo usuarios.dat no existe.");
            System.out.println("Creando archivo...");
            crearArchivo();
            return;
        }

        //Se verifica que sea realmente un archivo
        if (!archivoDAT.isFile()) {
            System.out.println("ERROR: usuarios.dat no es un archivo valido.");
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
                System.out.println("ERROR: usuarios.dat no contiene una lista valida.");
                crearArchivo();
                return;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: usuarios.dat esta danado o no tiene un formato valido.");
            System.out.println("Se creara nuevamente el archivo.");
            crearArchivo();
        }
    }

    //Funcion que se va a encargar de crear el archivo con una lista vacia
    private void crearArchivo() {
        ArrayList<UsuarioET> listaVacia = new ArrayList<>();
        try {
            FileOutputStream archivoSalida = new FileOutputStream(archivo);
            ObjectOutputStream objetoSalida = new ObjectOutputStream(archivoSalida);
            objetoSalida.writeObject(listaVacia);
            objetoSalida.close();
            archivoSalida.close();
            System.out.println("Archivo usuarios.dat creado correctamente.");
        } catch (IOException e) {
            System.out.println("ERROR al crear usuarios.dat: " + e.getMessage());
        }
    }

    //Funcion que se va a encargar de guardar la lista completa en el archivo
    private void guardar() {
        try {
            FileOutputStream archivoSalida = new FileOutputStream(archivo);
            ObjectOutputStream objetoSalida = new ObjectOutputStream(archivoSalida);
            objetoSalida.writeObject(usuarios);
            objetoSalida.close();
            archivoSalida.close();
        } catch (IOException e) {
            System.out.println("ERROR al guardar los usuarios: " + e.getMessage());
        }
    }

    //Funcion que se va a encargar de cargar la lista desde el archivo al iniciar el sistema
    @SuppressWarnings("unchecked")
    private ArrayList<UsuarioET> cargar() {
        File archivoDAT = new File(archivo);
        try {
            FileInputStream archivoEntrada = new FileInputStream(archivoDAT);
            ObjectInputStream objetoEntrada = new ObjectInputStream(archivoEntrada);
            ArrayList<UsuarioET> lista = (ArrayList<UsuarioET>) objetoEntrada.readObject();
            objetoEntrada.close();
            archivoEntrada.close();
            return lista;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR al cargar los usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    //Funcion que se va a encargar de agregar un usuario y validar que su id no se repita
    public void agregar(UsuarioET u) throws Exception {
        for (UsuarioET usuario : usuarios) {
            if (usuario.getIdUsuario() == u.getIdUsuario()) {
                throw new Exception("Ya existe un usuario con ID " + u.getIdUsuario());
            }
        }
        usuarios.add(u);
        guardar();
    }

    //Funcion que se va a encargar de buscar un usuario por su id, si no existe devuelve null
    public UsuarioET buscar(int idUsuario) {
        for (UsuarioET u : usuarios) {
            if (u.getIdUsuario() == idUsuario) {
                return u;
            }
        }
        return null;
    }

    //Funcion que se va a encargar de devolver la lista con todos los usuarios
    public ArrayList<UsuarioET> obtenerTodos() {
        return usuarios;
    }

    //Funcion que se va a encargar de eliminar un usuario por su id
    public boolean eliminar(int idUsuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getIdUsuario() == idUsuario) {
                usuarios.remove(i);
                guardar();
                return true;
            }
        }
        return false;
    }

    //Funcion que se va a encargar de editar los datos de un usuario que ya existe
    public boolean editar(int idUsuario, String nombreUsuario, String apellidoUsuario1, String apellidoUsuario2) {

        for (UsuarioET u : usuarios) {
            if (u.getIdUsuario() == idUsuario) {
                u.setNombreUsuario(nombreUsuario);
                u.setApellidoUsuario1(apellidoUsuario1);
                u.setApellidoUsuario2(apellidoUsuario2);
                guardar();
                return true;
            }
        }
        return false;
    }
}