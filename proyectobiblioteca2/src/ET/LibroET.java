
package ET;
import java.io.Serializable;

public class LibroET {
    private int idLibro;
    private int idCategoria;
    private String nombreLibro;
    private int existencias;
    private String nombreAutor;
    private String apellido1;
    private String apellido2;

    public LibroET(int idLibro, int idCategoria, String nombreLibro, int existencias, String nombreAutor, String apellido1, String apellido2) {
        this.idLibro = idLibro;
        this.idCategoria = idCategoria;
        this.nombreLibro = nombreLibro;
        this.existencias = existencias;
        this.nombreAutor = nombreAutor;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getNombreLibro() {
        return nombreLibro;
    }

    public int getExistencias() {
        return existencias;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setNombreLibro(String nombreLibro) {
        this.nombreLibro = nombreLibro;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    @Override
    public String toString() {
         return "ID Libro"+idLibro+"\nID Categoria:"+idCategoria+"\\nNombre: "+nombreLibro + "\nExistencias: " + existencias + "\nAutor: " + nombreAutor + " " + apellido1 + " " + apellido2;
    }
    
    
}
