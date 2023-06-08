package Modelo;

public class Proveedor {
    private int Id;
    private int NIT;
    private String Nombre;
    private int Telefono;
    private String Empresa;

    public Proveedor() {
    }

    public Proveedor(int Id, int NIT, String Nombre, int Telefono, String Empresa) {
        this.Id = Id;
        this.NIT = NIT;
        this.Nombre = Nombre;
        this.Telefono = Telefono;
        this.Empresa = Empresa;
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

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String Empresa) {
        this.Empresa = Empresa;
    }
    
    
}
