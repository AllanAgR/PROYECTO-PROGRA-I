package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author AGUILAR
 */
public class LoginDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();
    
    public login log(String Usuario, String Pass){
        login l = new login();
        String sql = "SELECT * FROM Usuarios WHERE Usuario = ? AND Pass = ?";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, Usuario);
            ps.setString(2, Pass);
            rs = ps.executeQuery();
            if (rs.next()) {
                l.setId(rs.getInt("Id"));
                l.setNombre(rs.getString("Nombre"));
                l.setUsuario(rs.getString("Usuario"));
                l.setPass(rs.getString("Pass"));
                l.setRol(rs.getString("Rol"));
                
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return l;
    }
    
    public boolean Registrar(login reg){
        String sql = "INSERT INTO usuarios(Nombre, Usuario, Pass, Rol) VALUES (?, ?, ?, ?)";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, reg.getNombre());
            ps.setString(2, reg.getUsuario());
            ps.setString(3, reg.getPass());
            ps.setString(4, reg.getRol());
            ps.execute();
            
            return true;
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            
            return false;
            
        }
    }
}
