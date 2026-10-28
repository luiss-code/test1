package UI;

import BL.CategoriaBL;
import BL.LibroBL;
import BL.PrestamoBL;
import BL.UsuarioBL;


public class SistemaBiblioteca {

    //Funcion principal que se va a encargar de crear las capas e iniciar el sistema
    public static void main(String[] args) {

        //CAPA BL
        CategoriaBL categoriaBL = new CategoriaBL();
        LibroBL libroBL = new LibroBL(categoriaBL);
        UsuarioBL usuarioBL = new UsuarioBL();
        PrestamoBL prestamoBL = new PrestamoBL(usuarioBL, libroBL);

        //MENUS
        MenuCategoria menuCategoria = new MenuCategoria(categoriaBL);
        MenuLibro menuLibro = new MenuLibro(libroBL, categoriaBL);
        MenuUsuario menuUsuario = new MenuUsuario(usuarioBL);
        MenuPrestamo menuPrestamo = new MenuPrestamo(prestamoBL, usuarioBL, libroBL);

        //MENU GENERAL
        MenuGeneral menuGeneral = new MenuGeneral(menuCategoria, menuLibro, menuUsuario, menuPrestamo);

        //INICIAR SISTEMA
        menuGeneral.mostrar();
    }
}