package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AGUILAR
 */
public class ProveedorDao {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean RegistrarProveedor(Proveedor pr){
        String slq = "INSERT INTO proveedores(NIT, Nombre, Telefono, Empresa) VALUES (?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(slq);
            
            ps.setInt(1, pr.getNIT());
            ps.setString(2, pr.getNombre());
            ps.setInt(3, pr.getTelefono());
            ps.setString(4, pr.getEmpresa());
            
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
                
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public List ListarProveedor (){
        List<Proveedor> Listapr = new ArrayList();
        String sql = "SELECT * FROM proveedores ";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Proveedor pr = new Proveedor();
                pr.setId(rs.getInt("Id"));
                pr.setNIT(rs.getInt("NIT"));
                pr.setNombre(rs.getString("Nombre"));
                pr.setTelefono(rs.getInt("Telefono"));
                pr.setEmpresa(rs.getString("Empresa"));
                
                Listapr.add(pr);
            }
        } catch (SQLException e) {
            
            System.out.println(e.toString());
        }
        return Listapr;
    }
    
    public boolean EliminarProveedor(int Id){
        String sql = "DELETE FROM proveedores WHERE Id = ? ";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql); 
            ps.setInt(1, Id);
            ps.execute();
            
            return true;
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            
            return false;
        }finally{
            try {
                
                con.close();
                
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public boolean ModificarProveedor(Proveedor pr){
        String sql = "UPDATE proveedores SET NIT = ?, Nombre = ?, Telefono = ?, Empresa = ? WHERE Id = ?";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, pr.getNIT());
            ps.setString(2, pr.getNombre());
            ps.setInt(3, pr.getTelefono());
            ps.setString(4, pr.getEmpresa());
            ps.setInt(5, pr.getId());
            ps.execute();
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            
            return false;
        }finally{
            
            try {
                con.close();
                
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        
    }
}
