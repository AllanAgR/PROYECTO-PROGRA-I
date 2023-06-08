package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author AGUILAR
 */
public class ProductosDao {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean RegistrarProductos(Productos pro){
        String sql = "INSERT INTO productos (Codigo, Descripcion, Cantidad, Precio, Proveedor) VALUES (?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getDescripcion());
            ps.setInt(3, pro.getCantidad());
            ps.setDouble(4, pro.getPrecio());
            ps.setString(5, pro.getProveedor());
            
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public void ConsultarProveedor(JComboBox proveedor){
        String sql = "SELECT Nombre FROM proveedores";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                proveedor.addItem(rs.getString("Nombre"));
            }
            
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    
    public List ListarProductos(){
        List<Productos> ListaPro = new ArrayList();
        String sql = "SELECT * FROM productos";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                Productos pro = new Productos();
                pro.setId(rs.getInt("Id"));
                pro.setCodigo(rs.getString("Codigo"));
                pro.setDescripcion(rs.getString("Descripcion"));
                pro.setCantidad(rs.getInt("Cantidad"));
                pro.setPrecio(rs.getDouble("Precio"));
                pro.setProveedor(rs.getString("Proveedor"));
                ListaPro.add(pro);
            }
            
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return ListaPro;
    }
    
    public boolean EliminarProductos(int Id){
        String sql = "DELETE FROM productos WHERE Id = ?";
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
    
    public boolean ModificarProductos(Productos pro){
        String sql = "UPDATE productos SET Codigo = ?, Descripcion = ?, Cantidad = ?, Precio = ?, Proveedor = ? WHERE Id = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getDescripcion());
            ps.setInt(3, pro.getCantidad());
            ps.setDouble(4, pro.getPrecio());
            ps.setString(5, pro.getProveedor());
            ps.setInt(6, pro.getId());
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
    
    public Productos BucarPro(String cod){
        Productos producto = new Productos();
        String sql = "SELECT * FROM productos WHERE Codigo = ?";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, cod);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                producto.setDescripcion(rs.getString("Descripcion"));
                producto.setCantidad(rs.getInt("Cantidad"));
                producto.setPrecio(rs.getDouble("Precio"));
                
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;
    }
    
    public Config BucarDatos(){
        Config conf = new Config();
        String sql = "SELECT * FROM configuracion";
        
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                conf.setId(rs.getInt("Id"));
                conf.setNIT(rs.getInt("NIT"));
                conf.setNombre(rs.getString("Nombre"));
                conf.setTelefono(rs.getInt("Telefono"));
                conf.setDireccion(rs.getString("Direccion"));
                conf.setRazon(rs.getString("Razon"));
                
            }
            
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return conf;
    }
    
    public boolean ModificarDatos(Config conf){
        String sql = "UPDATE configuracion SET NIT = ?, Nombre = ?, Telefono = ?, Direccion = ?, Razon = ? WHERE Id = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, conf.getNIT());
            ps.setString(2, conf.getNombre());
            ps.setInt(3, conf.getTelefono());
            ps.setString(4, conf.getDireccion());
            ps.setString(5, conf.getRazon());
            ps.setInt(6, conf.getId());
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
