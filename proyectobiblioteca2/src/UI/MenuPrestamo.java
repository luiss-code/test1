package UI;

import BL.LibroBL;
import BL.PrestamoBL;
import BL.UsuarioBL;
import ET.LibroET;
import ET.UsuarioET;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPrestamo {

    private Scanner sc = new Scanner(System.in);
    private PrestamoBL bl;
    private UsuarioBL usuarioBL;
    private LibroBL libroBL;

    //Constructor que recibe la BL de prestamos, la de usuarios y la de libros
    public MenuPrestamo(PrestamoBL bl, UsuarioBL usuarioBL, LibroBL libroBL) {
        this.bl = bl;
        this.usuarioBL = usuarioBL;
        this.libroBL = libroBL;
    }

    //Funcion que se va a encargar de mostrar el menu de prestamos
    public void mostrar() {
        int op = 0;
        do {
            try {
                System.out.println("\n===== MENU PRESTAMOS =====");
                System.out.println("1. Prestamo");
                System.out.println("2. Devolucion");
                System.out.println("3. Salir");
                System.out.print("Seleccione una opcion: ");
                op = sc.nextInt();
                sc.nextLine();

                switch (op) {
                    case 1 -> prestar();
                    case 2 -> devolver();
                    case 3 -> System.out.println("Saliendo del menu de prestamos...");
                    default -> System.out.println("Error: opcion invalida.");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Error: Debe ingresar un numero.");
                sc.nextLine();
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } while (op != 3);
    }

    //Funcion que se va a encargar de mostrar los usuarios y libros, y registrar un prestamo
    private void prestar() {
        try {
            System.out.println("\n--- PRESTAMO ---");

            ArrayList<UsuarioET> usuarios = usuarioBL.listar();

            if (usuarios == null || usuarios.isEmpty()) {
                System.out.println("No existen usuarios registrados.");
                return;
            }

            ArrayList<LibroET> libros = libroBL.listar();

            if (libros == null || libros.isEmpty()) {
                System.out.println("No existen libros registrados.");
                return;
            }

            System.out.println("\n--- USUARIOS ---");
            for (UsuarioET usuario : usuarios) {
                System.out.println(usuario);
            }

            System.out.println("\n--- LIBROS ---");
            for (LibroET libro : libros) {
                System.out.println(
                        "ID: " + libro.getIdLibro()
                        + " | Nombre: " + libro.getNombreLibro()
                        + " | Existencias: " + libro.getExistencias()
                );
            }

            System.out.print("\nIngrese ID del prestamo: ");
            int idPrestamo = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese ID del usuario: ");
            int idUsuario = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese ID del libro: ");
            int idLibro = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese fecha de inicio (numero de dia): ");
            int fechaInicio = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese fecha limite de devolucion (numero de dia): ");
            int fechaDevolucion = sc.nextInt();
            sc.nextLine();

            boolean agregado = bl.prestar(idPrestamo, idUsuario, idLibro, fechaInicio, fechaDevolucion);

            if (agregado) {
                System.out.println("Prestamo registrado correctamente.");
            } else {
                System.out.println("No se pudo registrar el prestamo.");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Error: debe ingresar numeros validos.");
            sc.nextLine();
        } catch (Exception ex) {
            System.out.println("Error al registrar el prestamo: " + ex.getMessage());
        }
    }

    //Funcion que se va a encargar de registrar la devolucion y mostrar la multa si hay atraso
    private void devolver() {
        try {
            System.out.println("\n--- DEVOLUCION ---");
            System.out.print("Ingrese ID del prestamo: ");
            int idPrestamo = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese fecha real de devolucion (numero de dia): ");
            int fechaReal = sc.nextInt();
            sc.nextLine();

            int multa = bl.devolver(idPrestamo, fechaReal);

            System.out.println("Libro devuelto correctamente.");

            if (multa > 0) {
                System.out.println("El libro se devolvio con atraso.");
                System.out.println("Multa a pagar: " + multa);
            } else {
                System.out.println("Devolucion a tiempo, no tiene multa.");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Error: debe ingresar numeros validos.");
            sc.nextLine();
        } catch (Exception ex) {
            System.out.println("Error al registrar la devolucion: " + ex.getMessage());
        }
    }
}