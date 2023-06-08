package Reportes;

import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author AGUILAR
 */
public class Grafico {
    
    public static void Graficar(String Fecha){
        Connection con;
        Conexion cn = new Conexion();
        PreparedStatement ps;
        ResultSet rs;
        
        try {
            String sql = "SELECT Total FROM ventas WHERE Fecha = ?";
            
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, Fecha);
            rs = ps.executeQuery();
            DefaultPieDataset dateset = new DefaultPieDataset();
            
            while (rs.next()) {                
                dateset.setValue(rs.getString("Total"), rs.getDouble("Total"));
            }
            
            JFreeChart jf = ChartFactory.createPieChart("Reporte de Venta", dateset);
            
            ChartFrame f = new ChartFrame("Total de ventas por día", jf);
            f.setSize(1000, 500);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
                    
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
