package UI;

import BL.CategoriaBL;
import ET.CategoriaET;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuCategoria {

    private Scanner sc = new Scanner(System.in);
    private CategoriaBL bl;

    //Constructor que recibe la BL de categorias
    public MenuCategoria(CategoriaBL bl) {
        this.bl = bl;
    }

    //Funcion que se va a encargar de mostrar el menu de categorias
    public void mostrar() {
        int op = 0;
        do {
            try {
                System.out.println("\n===== MENU CATEGORIAS =====");
                System.out.println("1. Registrar categoria");
                System.out.println("2. Buscar categoria");
                System.out.println("3. Eliminar categoria");
                System.out.println("4. Listar categorias");
                System.out.println("5. Actualizar categoria");
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
                    case 6 -> System.out.println("Saliendo del menu de categorias...");
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

    //Funcion que se va a encargar de pedir los datos y registrar una categoria
    private void registrar() {
        try {
            System.out.println("\n--- REGISTRAR CATEGORIA ---");
            System.out.print("Ingrese ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese nombre: ");
            String nombre = sc.nextLine().trim();

            boolean agregado = bl.agregar(id, nombre);

            if (agregado) {
                System.out.println("Categoria agregada correctamente.");
            } else {
                System.out.println("Categoria no se pudo agregar.");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Error: el ID de categoria debe ser un numero.");
            sc.nextLine();
        } catch (Exception ex) {
            System.out.println("Error al agregar la categoria: " + ex.getMessage());
        }
    }

    //Funcion que se va a encargar de buscar una categoria por su id
    private void buscar() {
        try {
            System.out.println("\n--- BUSCAR CATEGORIA ---");
            System.out.print("Ingrese ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            CategoriaET categoria = bl.buscar(id);

            if (categoria != null) {
                System.out.println("Categoria encontrada:");
                System.out.println(categoria);
            } else {
                System.out.println("No se encontro una categoria con ese ID.");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Error: el ID de categoria debe ser un numero.");
            sc.nextLine();
        } catch (Exception ex) {
            System.out.println("Error al buscar la categoria: " + ex.getMessage());
        }
    }

    //Funcion que se va a encargar de eliminar una categoria por su id
    private void eliminar() {
        try {
            System.out.println("\n--- ELIMINAR CATEGORIA ---");
            System.out.print("Ingrese ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            boolean eliminado = bl.eliminar(id);

            if (eliminado) {
                System.out.println("Categoria eliminada correctamente.");
            } else {
                System.out.println("No se pudo eliminar la categoria.");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Error: el ID de categoria debe ser un numero.");
            sc.nextLine();
        } catch (Exception ex) {
            System.out.println("Error al eliminar la categoria: " + ex.getMessage());
        }
    }

    //Funcion que se va a encargar de listar todas las categorias
    private void listar() {
        try {
            System.out.println("\n--- LISTA DE CATEGORIAS ---");
            ArrayList<CategoriaET> lista = bl.listar();

            if (lista == null || lista.isEmpty()) {
                System.out.println("No existen categorias registradas.");
            } else {
                for (CategoriaET categoria : lista) {
                    System.out.println(categoria);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error al listar las categorias: " + ex.getMessage());
        }
    }

    //Funcion que se va a encargar de actualizar el nombre de una categoria
    private void actualizar() {
        try {
            System.out.println("\n--- ACTUALIZAR CATEGORIA ---");
            System.out.print("Ingrese ID de la categoria: ");
            int id = sc.nextInt();
            sc.nextLine();

            CategoriaET categoria = bl.buscar(id);

            if (categoria == null) {
                System.out.println("No se encontro una categoria con ese ID.");
                return;
            }

            System.out.println("Categoria actual: " + categoria);
            System.out.print("Ingrese el nuevo nombre: ");
            String nombre = sc.nextLine().trim();

            boolean actualizado = bl.actualizar(id, nombre);

            if (actualizado) {
                System.out.println("Categoria actualizada correctamente.");
            } else {
                System.out.println("No se pudo actualizar la categoria.");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Error: el ID de categoria debe ser un numero.");
            sc.nextLine();
        } catch (Exception ex) {
            System.out.println("Error al actualizar la categoria: " + ex.getMessage());
        }
    }
}