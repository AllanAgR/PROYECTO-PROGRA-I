package Modelo;

/**
 *
 * @author AGUILAR
 */
public class Cliente {
    private int Id;
    private int NIT;
    private String Nombre;
    private double Cantidad;

    public Cliente() {
    }

    public Cliente(int Id, int NIT, String Nombre, double Cantidad) {
        this.Id = Id;
        this.NIT = NIT;
        this.Nombre = Nombre;
        this.Cantidad = Cantidad;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getNIT() {
        return NIT;
    }

    public void setNIT(int NIT) {
        this.NIT = NIT;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(double Cantidad) {
        this.Cantidad = Cantidad;
    }
    
    
}
