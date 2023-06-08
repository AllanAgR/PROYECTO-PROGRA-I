package Modelo;

/**
 *
 * @author AGUILAR
 */
public class Productos {
    private int Id;
    private String Codigo;
    private String Descripcion;
    private int Cantidad;
    private double Precio;
    private String Proveedor;

    public Productos() {
    }

    public Productos(int Id, String Codigo, String Descripcion, int Cantidad, double Precio, String Proveedor) {
        this.Id = Id;
        this.Codigo = Codigo;
        this.Descripcion = Descripcion;
        this.Cantidad = Cantidad;
        this.Precio = Precio;
        this.Proveedor = Proveedor;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double Precio) {
        this.Precio = Precio;
    }

    public String getProveedor() {
        return Proveedor;
    }

    public void setProveedor(String Proveedor) {
        this.Proveedor = Proveedor;
    }
    
    
    
}
