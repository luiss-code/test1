
package ET;
import java.io.Serializable;

public class CategoriaET implements Serializable {
private int idCategoria;
private String nombreCategoria;

public CategoriaET(int idCategoria, String nombreCategoria){
    this.idCategoria = idCategoria;
    this.nombreCategoria = nombreCategoria;
}

public int getIdCategoria(){
    return idCategoria;
}

public void setIdCategoria(int idCategoria){
    this.idCategoria=idCategoria;
}

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
@Override
public String toString(){
    return "ID Categoria "+idCategoria+" Nombre: "+nombreCategoria;
}
}
