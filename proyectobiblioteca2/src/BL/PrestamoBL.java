package BL;

import DAL.PrestamoDAL;
import ET.LibroET;
import ET.PrestamoET;
import java.util.ArrayList;

public class PrestamoBL {

    private PrestamoDAL dal;
    private UsuarioBL usuarioBL;
    private LibroBL libroBL;
    private int maximo = 20;
    private int maximoPorUsuario = 3;
    private int multaPorDia = 500;

    //Constructor que crea la DAL de prestamos y se conecta con UsuarioBL y LibroBL
    public PrestamoBL(UsuarioBL usuarioBL, LibroBL libroBL) {
        this.usuarioBL = usuarioBL;
        this.libroBL = libroBL;
        this.dal = new PrestamoDAL();
        this.usuarioBL.setPrestamoBL(this);
        this.libroBL.setPrestamoBL(this);
    }

    //Funcion que se va a encargar de validar y registrar un prestamo, y restar una existencia al libro
    public boolean prestar(int idPrestamo, int idUsuario, int idLibro, int fechaInicio, int fechaDevolucion) throws Exception {

        if (dal.obtenerTodos().size() >= maximo) {
            throw new Exception("Se alcanzo el maximo de " + maximo + " prestamos.");
        }

        if (dal.buscar(idPrestamo) != null) {
            throw new Exception("El prestamo con ID " + idPrestamo + " ya existe.");
        }

        if (usuarioBL.buscar(idUsuario) == null) {
            throw new Exception("El usuario con ID " + idUsuario + " no existe.");
        }

        LibroET libro = libroBL.buscar(idLibro);

        if (libro == null) {
            throw new Exception("El libro con ID " + idLibro + " no existe.");
        }

        if (libro.getExistencias() <= 0) {
            throw new Exception("El libro no tiene existencias disponibles.");
        }

        if (dal.contarPorUsuario(idUsuario) >= maximoPorUsuario) {
            throw new Exception("El usuario ya tiene " + maximoPorUsuario + " prestamos activos.");
        }

        if (dal.existeUsuarioLibro(idUsuario, idLibro)) {
            throw new Exception("El usuario ya tiene prestado este libro.");
        }

        if (fechaDevolucion <= fechaInicio) {
            throw new Exception("La fecha de devolucion debe ser mayor que la fecha de inicio.");
        }

        PrestamoET prestamo = new PrestamoET(idPrestamo, idUsuario, idLibro, fechaInicio, fechaDevolucion);
        dal.agregar(prestamo);
        libroBL.restarExistencia(idLibro);
        return true;
    }

    //Funcion que se va a encargar de la devolucion, elimina el prestamo, suma una existencia al libro y devuelve la multa
    public int devolver(int idPrestamo, int fechaReal) throws Exception {

        PrestamoET prestamo = dal.buscar(idPrestamo);

        if (prestamo == null) {
            throw new Exception("El prestamo con ID " + idPrestamo + " no existe.");
        }

        if (fechaReal < prestamo.getFechaInicio()) {
            throw new Exception("La fecha real de devolucion no puede ser menor que la fecha de inicio.");
        }

        int multa = calcularMulta(prestamo.getFechaDevolucion(), fechaReal);

        dal.eliminar(idPrestamo);
        libroBL.sumarExistencia(prestamo.getIdLibro());
        return multa;
    }

    //Funcion que se va a encargar de calcular la multa, son 500 por cada dia de atraso
    private int calcularMulta(int fechaLimite, int fechaReal) {

        int diasAtraso = fechaReal - fechaLimite;

        if (diasAtraso > 0) {
            return diasAtraso * multaPorDia;
        }

        return 0;
    }

    //Funcion que se va a encargar de buscar un prestamo, si no existe devuelve null
    public PrestamoET buscar(int idPrestamo) {
        return dal.buscar(idPrestamo);
    }

    //Funcion que se va a encargar de listar todos los prestamos activos
    public ArrayList<PrestamoET> listar() {
        return dal.obtenerTodos();
    }

    //Funcion que se va a encargar de contar los prestamos activos de un usuario
    public int contarPorUsuario(int idUsuario) {
        return dal.contarPorUsuario(idUsuario);
    }

    //Funcion que se va a encargar de verificar si un libro tiene prestamos activos
    public boolean existePorLibro(int idLibro) {
        return dal.existePorLibro(idLibro);
    }
}