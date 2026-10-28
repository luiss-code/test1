package UI;

import BL.CategoriaBL;
import BL.LibroBL;
import ET.CategoriaET;
import ET.LibroET;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuLibro {

    private Scanner sc = new Scanner(System.in);
    private LibroBL bl;
    private CategoriaBL categoriaBL;

    //Constructor que recibe la BL de libros y la de categorias
    public MenuLibro(LibroBL bl, CategoriaBL categoriaBL) {
        this.bl = bl;
        this.categoriaBL = categoriaBL;
    }

    //Funcion que se va a encargar de mostrar el menu de libros
    public void mostrar() {
        int op = 0;
        do {
            try {
                System.out.println("\n===== MENU LIBROS =====");
                System.out.println("1. Registrar libro");
                System.out.println("2. Buscar libro");
                System.out.println("3. Eliminar libro");
                System.out.println("4. Listar libros");
                System.out.println("5. Actualizar libro");
                System.out.println("6. Salir");
                System.out.print("Seleccione una opcion: ");
                op = sc.nextInt();
                sc.nextLine();

                switch (op) {
                    case 1 -> registrar();
                    case 2 -> buscar();
                    case 3 -> eliminar();
                    case 4 -> listar();
                    case 5 -> actualizar();
                    case 6 -> System.out.println("Saliendo del menu de libros...");
                    default -> System.out.println("Error: opcion invalida.");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Error: Debe ingresar un numero.");
                sc.nextLine();
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } while (op != 6);
    }

    //Funcion que se va a encargar de mostrar las categorias disponibles, devuelve false si no hay ninguna
    private boolean mostrarCategorias() {
        ArrayList<CategoriaET> categorias = categoriaBL.listar();

        if (categorias == null || categorias.isEmpty()) {
            System.out.println("No existen categorias registradas, primero debe crear una.");
            return false;
        }

        System.out.println("\n--- CATEGORIAS DISPONIBLES ---");
        for (CategoriaET categoria : categorias) {
            System.out.println(categoria);
        }
        return true;
    }

    //Funcion que se va a encargar de pedir los datos y registrar un libro
    private void registrar() {
        try {
            System.out.println("\n--- REGISTRAR LIBRO ---");

            if (!mostrarCategorias()) {
                return;
            }

            System.out.print("\nIngrese ID del libro: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese ID de la categoria: ");
            int idCategoria = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese nombre del libro: ");
            String nombre = sc.nextLine().trim();
            System.out.print("Ingrese existencias: ");
            int existencias = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese nombre del autor: ");
            String autor = sc.nextLine().trim();
            System.out.print("Ingrese primer apellido del autor: ");
            String apellido1 = sc.nextLine().trim();
            System.out.print("Ingrese segundo apellido del autor: ");
            String apellido2 = sc.nextLine().trim();

            boolean agregado = bl.agregar(id, idCategoria, nombre, existencias, autor, apellido1, apellido2);

            if (agregado) {
                System.out.println("Libro agregado correctamente.");
            } else {
                System.out.println("Libro no se pudo agregar.");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Error: debe ingresar numeros validos.");
            sc.nextLine();
        } catch (Exception ex) {
            System.out.println("Error al agregar el libro: " + ex.getMessage());
        }
    }

    //Funcion que se va a encargar de buscar un libro por su id
    private void buscar() {
        try {
            System.out.println("\n--- BUSCAR LIBRO ---");
            System.out.print("Ingrese ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            LibroET libro = bl.buscar(id);

            if (libro != null) {
                System.out.println("Libro encontrado:");
                System.out.println(libro);
            } else {
                System.out.println("No se encontro un libro con ese ID.");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Error: el ID de libro debe ser un numero.");
            sc.nextLine();
        } catch (Exception ex) {
            System.out.println("Error al buscar el libro: " + ex.getMessage());
        }
    }

    //Funcion que se va a encargar de eliminar un libro por su id
    private void eliminar() {
        try {
            System.out.println("\n--- ELIMINAR LIBRO ---");
            System.out.print("Ingrese ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            boolean eliminado = bl.eliminar(id);

            if (eliminado) {
                System.out.println("Libro eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar el libro.");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Error: el ID de libro debe ser un numero.");
            sc.nextLine();
        } catch (Exception ex) {
            System.out.println("Error al eliminar el libro: " + ex.getMessage());
        }
    }

    //Funcion que se va a encargar de listar todos los libros
    private void listar() {
        try {
            System.out.println("\n--- LISTA DE LIBROS ---");
            ArrayList<LibroET> lista = bl.listar();

            if (lista == null || lista.isEmpty()) {
                System.out.println("No existen libros registrados.");
            } else {
                for (LibroET libro : lista) {
                    System.out.println(libro);
                    System.out.println("-----------------------------");
                }
            }
        } catch (Exception ex) {
            System.out.println("Error al listar los libros: " + ex.getMessage());
        }
    }

    //Funcion que se va a encargar de actualizar los datos de un libro
    private void actualizar() {
        try {
            System.out.println("\n--- ACTUALIZAR LIBRO ---");
            System.out.print("Ingrese ID del libro: ");
            int id = sc.nextInt();
            sc.nextLine();

            LibroET libro = bl.buscar(id);

            if (libro == null) {
                System.out.println("No se encontro un libro con ese ID.");
                return;
            }

            System.out.println("Libro actual:\n" + libro);

            if (!mostrarCategorias()) {
                return;
            }

            System.out.print("\nIngrese el nuevo ID de la categoria: ");
            int idCategoria = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese el nuevo nombre del libro: ");
            String nombre = sc.nextLine().trim();
            System.out.print("Ingrese las nuevas existencias: ");
            int existencias = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese el nuevo nombre del autor: ");
            String autor = sc.nextLine().trim();
            System.out.print("Ingrese el nuevo primer apellido del autor: ");
            String apellido1 = sc.nextLine().trim();
            System.out.print("Ingrese el nuevo segundo apellido del autor: ");
            String apellido2 = sc.nextLine().trim();

            boolean actualizado = bl.actualizar(id, idCategoria, nombre, existencias, autor, apellido1, apellido2);

            if (actualizado) {
                System.out.println("Libro actualizado correctamente.");
            } else {
                System.out.println("No se pudo actualizar el libro.");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Error: debe ingresar numeros validos.");
            sc.nextLine();
        } catch (Exception ex) {
            System.out.println("Error al actualizar el libro: " + ex.getMessage());
        }
    }
}
