package UI;

import BL.UsuarioBL;
import ET.UsuarioET;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuUsuario {

    private Scanner sc = new Scanner(System.in);
    private UsuarioBL bl;

    //Constructor que recibe la BL de usuarios
    public MenuUsuario(UsuarioBL bl) {
        this.bl = bl;
    }

    //Funcion que se va a encargar de mostrar el menu de usuarios
    public void mostrar() {
        int op = 0;
        do {
            try {
                System.out.println("\n===== MENU USUARIOS =====");
                System.out.println("1. Registrar usuario");
                System.out.println("2. Buscar usuario");
                System.out.println("3. Eliminar usuario");
                System.out.println("4. Listar usuarios");
                System.out.println("5. Actualizar usuario");
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
                    case 6 -> System.out.println("Saliendo del menu de usuarios...");
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

    //Funcion que se va a encargar de pedir los datos y registrar un usuario
    private void registrar() {
        try {
            System.out.println("\n--- REGISTRAR USUARIO ---");
            System.out.print("Ingrese ID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese nombre: ");
            String nombre = sc.nextLine().trim();
            System.out.print("Ingrese primer apellido: ");
            String apellido1 = sc.nextLine().trim();
            System.out.print("Ingrese segundo apellido: ");
            String apellido2 = sc.nextLine().trim();

            boolean agregado = bl.agregar(id, nombre, apellido1, apellido2);

            if (agregado) {
                System.out.println("Usuario agregado correctamente.");
            } else {
                System.out.println("Usuario no se pudo agregar.");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Error: el ID de usuario debe ser un numero.");
            sc.nextLine();
        } catch (Exception ex) {
            System.out.println("Error al agregar el usuario: " + ex.getMessage());
        }
    }

    //Funcion que se va a encargar de buscar un usuario por su id
    private void buscar() {
        try {
            System.out.println("\n--- BUSCAR USUARIO ---");
            System.out.print("Ingrese ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            UsuarioET usuario = bl.buscar(id);

            if (usuario != null) {
                System.out.println("Usuario encontrado:");
                System.out.println(usuario);
            } else {
                System.out.println("No se encontro un usuario con ese ID.");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Error: el ID de usuario debe ser un numero.");
            sc.nextLine();
        } catch (Exception ex) {
            System.out.println("Error al buscar el usuario: " + ex.getMessage());
        }
    }

    //Funcion que se va a encargar de eliminar un usuario por su id
    private void eliminar() {
        try {
            System.out.println("\n--- ELIMINAR USUARIO ---");
            System.out.print("Ingrese ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            boolean eliminado = bl.eliminar(id);

            if (eliminado) {
                System.out.println("Usuario eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar el usuario.");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Error: el ID de usuario debe ser un numero.");
            sc.nextLine();
        } catch (Exception ex) {
            System.out.println("Error al eliminar el usuario: " + ex.getMessage());
        }
    }

    //Funcion que se va a encargar de listar todos los usuarios
    private void listar() {
        try {
            System.out.println("\n--- LISTA DE USUARIOS ---");
            ArrayList<UsuarioET> lista = bl.listar();

            if (lista == null || lista.isEmpty()) {
                System.out.println("No existen usuarios registrados.");
            } else {
                for (UsuarioET usuario : lista) {
                    System.out.println(usuario);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error al listar los usuarios: " + ex.getMessage());
        }
    }

    //Funcion que se va a encargar de actualizar los datos de un usuario
    private void actualizar() {
        try {
            System.out.println("\n--- ACTUALIZAR USUARIO ---");
            System.out.print("Ingrese ID del usuario: ");
            int id = sc.nextInt();
            sc.nextLine();

            UsuarioET usuario = bl.buscar(id);

            if (usuario == null) {
                System.out.println("No se encontro un usuario con ese ID.");
                return;
            }

            System.out.println("Usuario actual: " + usuario);
            System.out.print("Ingrese el nuevo nombre: ");
            String nombre = sc.nextLine().trim();
            System.out.print("Ingrese el nuevo primer apellido: ");
            String apellido1 = sc.nextLine().trim();
            System.out.print("Ingrese el nuevo segundo apellido: ");
            String apellido2 = sc.nextLine().trim();

            boolean actualizado = bl.actualizar(id, nombre, apellido1, apellido2);

            if (actualizado) {
                System.out.println("Usuario actualizado correctamente.");
            } else {
                System.out.println("No se pudo actualizar el usuario.");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Error: el ID de usuario debe ser un numero.");
            sc.nextLine();
        } catch (Exception ex) {
            System.out.println("Error al actualizar el usuario: " + ex.getMessage());
        }
    }
}