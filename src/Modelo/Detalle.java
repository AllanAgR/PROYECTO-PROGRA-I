package Modelo;

/**
 *
 * @author AGUILAR
 */
public class Detalle {
    private int Id;
    private String CodPro;
    private int Cantidad;
    private double Precio;
    private int Id_venta;

    public Detalle() {
    }

    public Detalle(int Id, String CodPro, int Cantidad, double Precio, int Id_venta) {
        this.Id = Id;
        this.CodPro = CodPro;
        this.Cantidad = Cantidad;
        this.Precio = Precio;
        this.Id_venta = Id_venta;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getCodPro() {
        return CodPro;
    }

    public void setCodPro(String CodPro) {
        this.CodPro = CodPro;
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

    public int getId_venta() {
        return Id_venta;
    }

    public void setId_venta(int Id_venta) {
        this.Id_venta = Id_venta;
    }
    
    
    
}
