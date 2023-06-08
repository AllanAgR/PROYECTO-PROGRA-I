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
public class VentaDao {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r;
    
    public int Id_venta(){
        int Id = 0;
        String sql = "SELECT MAX(Id) FROM ventas";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Id = rs.getInt(1);
                
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Id;
    }
    
    public int RegistrarVenta(Venta v){
        String sql = "INSERT INTO ventas (Cliente, Vendedor, Total, Fecha) VALUES (?, ?, ?, ?)";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, v.getCliente());
            ps.setString(2, v.getVendedor());
            ps.setDouble(3, v.getTotal());
            ps.setString(4, v.getFecha());
            ps.execute();
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            
        }
        
        finally{
            try {
                con.close();
                
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        
        return r;
    }
    
    public int RegistrarDetalle(Detalle Dv){
        String sql = "INSERT INTO detalle (CodPro, Cantidad, Precio, Id_venta) VALUES (?, ?, ?, ?)";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, Dv.getCodPro());
            ps.setInt(2, Dv.getCantidad());
            ps.setDouble(3, Dv.getPrecio());
            ps.setInt(4, Dv.getId());
            
            ps.execute();
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        
        finally{
            try {
                con.close();
                
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        
        return r;
    }
    
    public boolean ActualizarStock (int Cantidad, String Codigo){
        String sql = "UPDATE productos SET Cantidad = ? WHERE Codigo = ?";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setInt(1, Cantidad);
            ps.setString(2, Codigo);
            
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            
            return false;
        }
        
    }
    
    public List Listarventas(){
        List<Venta> ListaVenta = new ArrayList();
        String sql = "SELECT * FROM ventas";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                Venta vent = new Venta();
                vent.setId(rs.getInt("Id"));
                vent.setCliente(rs.getString("Cliente"));
                vent.setVendedor(rs.getString("Vendedor"));
                vent.setTotal(rs.getDouble("Total"));
                ListaVenta.add(vent);
            }
            
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return ListaVenta;
    }
    
}
