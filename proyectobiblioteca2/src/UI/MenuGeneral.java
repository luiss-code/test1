package UI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuGeneral {

    private Scanner sc = new Scanner(System.in);
    private MenuCategoria menuCategoria;
    private MenuLibro menuLibro;
    private MenuUsuario menuUsuario;
    private MenuPrestamo menuPrestamo;

    //Constructor que recibe todos los menus del sistema
    public MenuGeneral(MenuCategoria menuCategoria, MenuLibro menuLibro, MenuUsuario menuUsuario, MenuPrestamo menuPrestamo) {
        this.menuCategoria = menuCategoria;
        this.menuLibro = menuLibro;
        this.menuUsuario = menuUsuario;
        this.menuPrestamo = menuPrestamo;
    }

    //Funcion que se va a encargar de mostrar el menu principal de la biblioteca
    public void mostrar() {
        int op = 0;
        do {
            try {
                System.out.println("\n==============================");
                System.out.println("    SISTEMA DE BIBLIOTECA");
                System.out.println("==============================");
                System.out.println("1. Gestion de Categorias");
                System.out.println("2. Gestion de Libros");
                System.out.println("3. Gestion de Usuarios");
                System.out.println("4. Prestamos");
                System.out.println("5. Salir");
                System.out.println("==============================");
                System.out.print("Seleccione una opcion: ");
                op = sc.nextInt();
                sc.nextLine();

                switch (op) {
                    case 1 -> menuCategoria.mostrar();
                    case 2 -> menuLibro.mostrar();
                    case 3 -> menuUsuario.mostrar();
                    case 4 -> menuPrestamo.mostrar();
                    case 5 -> System.out.println("Saliendo del Sistema de Biblioteca...");
                    default -> System.out.println("Error: opcion invalida.");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Error: Debe ingresar un numero.");
                sc.nextLine();
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } while (op != 5);
    }
}