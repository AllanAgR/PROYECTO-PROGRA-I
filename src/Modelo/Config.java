package Modelo;

/**
 *
 * @author AGUILAR
 */
public class Config {
    private int Id;
    private int NIT;
    private String Nombre;
    private int Telefono;
    private String Direccion;
    private String Razon;

    public Config() {
    }

    public Config(int Id, int NIT, String Nombre, int Telefono, String Direccion, String Razon) {
        this.Id = Id;
        this.NIT = NIT;
        this.Nombre = Nombre;
        this.Telefono = Telefono;
        this.Direccion = Direccion;
        this.Razon = Razon;
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

    public int getTelefono() {
        return Telefono;
    }

    public void setTelefono(int Telefono) {
        this.Telefono = Telefono;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getRazon() {
        return Razon;
    }

    public void setRazon(String Razon) {
        this.Razon = Razon;
    }
    
    
    
}
