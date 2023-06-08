package Modelo;

/**
 *
 * @author AGUILAR
 */
public class login {
    private int Id;
    private String Nombre;
    private String Usuario;
    private String Pass;
    private String Rol;

    public login() {
    }

    public login(int Id, String Nombre, String Usuario, String Pass, String Rol) {
        this.Id = Id;
        this.Nombre = Nombre;
        this.Usuario = Usuario;
        this.Pass = Pass;
        this.Rol = Rol;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String Rol) {
        this.Rol = Rol;
    }
    
    
}
