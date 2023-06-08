package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author AGUILAR
 */
public class ClienteDao {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean RegistrarCliente(Cliente cl){
        String sql = "INSERT INTO clientes (NIT, Nombre, Cantidad) VALUES (?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cl.getNIT());
            ps.setString(2, cl.getNombre());
            ps.setDouble(3, cl.getCantidad());
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    
    public List ListarCliente(){
        List<Cliente> ListaCl = new ArrayList();
        String sql = "SELECT * FROM clientes";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                Cliente cl = new Cliente();
                cl.setId(rs.getInt("Id"));
                cl.setNIT(rs.getInt("NIT"));
                cl.setNombre(rs.getString("Nombre"));
                cl.setCantidad(rs.getDouble("Cantidad"));
                ListaCl.add(cl);
            }
            
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return ListaCl;
    }
    
    public boolean EliminarCliente(int Id){
        String sql = "DELETE FROM clientes WHERE Id = ?";
        try {
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
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
        
    }
    
    public boolean ModificarCliente(Cliente cl){
        String sql = "UPDATE clientes SET NIT=?, Nombre=?, Cantidad=? WHERE Id = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cl.getNIT());
            ps.setString(2, cl.getNombre());
            ps.setDouble(3, cl.getCantidad());
            ps.setInt(4, cl.getId());
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

