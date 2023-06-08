/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author AGUILAR
 */
public class Venta {
    private int Id;
    private String Cliente;
    private String Vendedor;
    private double Total;
    private String Fecha;

    public Venta() {
    }

    public Venta(int Id, String Cliente, String Vendedor, double Total, String Fecha) {
        this.Id = Id;
        this.Cliente = Cliente;
        this.Vendedor = Vendedor;
        this.Total = Total;
        this.Fecha = Fecha;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String Cliente) {
        this.Cliente = Cliente;
    }

    public String getVendedor() {
        return Vendedor;
    }

    public void setVendedor(String Vendedor) {
        this.Vendedor = Vendedor;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double Total) {
        this.Total = Total;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }
    
    
    
}
