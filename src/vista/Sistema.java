/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import Modelo.Cliente;
import Modelo.ClienteDao;
import Modelo.Config;
import Modelo.Detalle;
import Modelo.Eventos;
import Modelo.Productos;
import Modelo.ProductosDao;
import Modelo.Proveedor;
import Modelo.ProveedorDao;
import Modelo.Venta;
import Modelo.VentaDao;
import Modelo.login;
import Reportes.Excel;
import Reportes.Grafico;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author AGUILAR
 */
public class Sistema extends javax.swing.JFrame {
    Date fechaVenta = new Date();
    String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(fechaVenta);
    
    Cliente cl = new Cliente();
    ClienteDao cliente = new ClienteDao();

    Proveedor pr = new Proveedor();
    ProveedorDao prDao = new ProveedorDao();

    Productos pro = new Productos();
    ProductosDao proDao = new ProductosDao();

    Venta v = new Venta();
    VentaDao Vdao = new VentaDao();

    Detalle Dv = new Detalle();

    Config conf = new Config();
    
    Eventos event = new Eventos();

    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp = new DefaultTableModel();

    int item;
    double Totalpagar = 0.00;

    public Sistema() {
        initComponents();
        ListarConfig();
        this.setLocationRelativeTo(null);
        
    }
    
    public Sistema(login priv){
        initComponents();
        this.setLocationRelativeTo(null);
        txtIdCliente.setVisible(false);
        txtIdVentas.setVisible(false);
        txtIDProducto.setVisible(false);
        txtIdProveedor.setVisible(false);
        txtIdProducto.setVisible(false);
        txtIdConfig.setVisible(false);

        AutoCompleteDecorator.decorate(cbxProveedorProducto);
        proDao.ConsultarProveedor(cbxProveedorProducto);
        
        if (priv.getRol().equals("Mesero")){
            btnProveedores.setEnabled(false);
            btnConfig.setEnabled(false);
            btnRegistrar.setEnabled(false);
            
            LableVendedor.setText(priv.getNombre());
            
        }else{
            LableVendedor.setText(priv.getNombre());
        }
        
    }
    
    public void ListarCliente() {
        List<Cliente> ListarCl = cliente.ListarCliente();
        modelo = (DefaultTableModel) TableCliente.getModel();

        Object[] ob = new Object[4];
        for (int i = 0; i < ListarCl.size(); i++) {
            ob[0] = ListarCl.get(i).getId();
            ob[1] = ListarCl.get(i).getNIT();
            ob[2] = ListarCl.get(i).getNombre();
            ob[3] = ListarCl.get(i).getCantidad();

            modelo.addRow(ob);

        }
        TableCliente.setModel(modelo);
    }

    public void ListarProveedor() {
        List<Proveedor> Listarpr = prDao.ListarProveedor();
        modelo = (DefaultTableModel) TableProveedor.getModel();

        Object[] ob = new Object[5];
        for (int i = 0; i < Listarpr.size(); i++) {
            ob[0] = Listarpr.get(i).getId();
            ob[1] = Listarpr.get(i).getNIT();
            ob[2] = Listarpr.get(i).getNombre();
            ob[3] = Listarpr.get(i).getTelefono();
            ob[4] = Listarpr.get(i).getEmpresa();

            modelo.addRow(ob);

        }
        TableProveedor.setModel(modelo);
    }

    public void ListarProductos() {
        List<Productos> ListarPro = proDao.ListarProductos();
        modelo = (DefaultTableModel) TableProducto.getModel();

        Object[] ob = new Object[6];
        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0] = ListarPro.get(i).getId();
            ob[1] = ListarPro.get(i).getCodigo();
            ob[2] = ListarPro.get(i).getDescripcion();
            ob[3] = ListarPro.get(i).getCantidad();
            ob[4] = ListarPro.get(i).getPrecio();
            ob[5] = ListarPro.get(i).getProveedor();

            modelo.addRow(ob);

        }
        TableProducto.setModel(modelo);
    }

    public void ListarConfig() {
        conf = proDao.BucarDatos();

        txtIdConfig.setText("" + conf.getId());
        txtNitConfig.setText("" + conf.getNIT());
        txtNombreConfig.setText("" + conf.getNombre());
        txtTelefonoConfig.setText("" + conf.getTelefono());
        txtDireccionConfig.setText("" + conf.getDireccion());
        txtRazonConfig.setText("" + conf.getRazon());

    }
    
    public void ListarVentas() {
        List<Venta> ListarVenta = Vdao.Listarventas();
        modelo = (DefaultTableModel) TableVentas.getModel();

        Object[] ob = new Object[4];
        for (int i = 0; i < ListarVenta.size(); i++) {
            ob[0] = ListarVenta.get(i).getId();
            ob[1] = ListarVenta.get(i).getCliente();
            ob[2] = ListarVenta.get(i).getVendedor();
            ob[3] = ListarVenta.get(i).getTotal();


            modelo.addRow(ob);

        }
        TableVentas.setModel(modelo);
    }

    public void LimpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnNuevaVenta = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnProveedores = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        LableVendedor = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnEliminarVenta = new javax.swing.JButton();
        txtCodigoVenta = new javax.swing.JTextField();
        txtDescripcionVenta = new javax.swing.JTextField();
        txtCantidadVenta = new javax.swing.JTextField();
        txtPrecioVenta = new javax.swing.JTextField();
        txtStockVenta = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableVenta = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNitVenta = new javax.swing.JTextField();
        txtNombreVentaCliente = new javax.swing.JTextField();
        btnGenerarVenta = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        labelTotal = new javax.swing.JLabel();
        txtIDProducto = new javax.swing.JTextField();
        btnGraficar = new javax.swing.JButton();
        Midate = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtNitCliente = new javax.swing.JTextField();
        txtNombreCliente = new javax.swing.JTextField();
        txtCantidadCliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableCliente = new javax.swing.JTable();
        btnGuardarCliente = new javax.swing.JButton();
        btnBorrarCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnNuevoCliente = new javax.swing.JButton();
        txtIdCliente = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtNombreProveedor = new javax.swing.JTextField();
        txtTelefonoProveedor = new javax.swing.JTextField();
        txtEmpresaProveedor = new javax.swing.JTextField();
        txtNitProveedor = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableProveedor = new javax.swing.JTable();
        btnGuardarProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        btnNuevoProveedor = new javax.swing.JButton();
        btnEditarProveedor = new javax.swing.JButton();
        txtIdProveedor = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtCodigoProducto = new javax.swing.JTextField();
        txtDescripcionProducto = new javax.swing.JTextField();
        txtCantidadProducto = new javax.swing.JTextField();
        txtPrecioProducto = new javax.swing.JTextField();
        cbxProveedorProducto = new javax.swing.JComboBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableProducto = new javax.swing.JTable();
        btnGuardarProducto = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        btnNuevoProducto = new javax.swing.JButton();
        btnEditarProducto = new javax.swing.JButton();
        btnExcelProducto = new javax.swing.JButton();
        txtIdProducto = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableVentas = new javax.swing.JTable();
        btnPdfVentas = new javax.swing.JButton();
        txtIdVentas = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        btnActualizarConfig = new javax.swing.JButton();
        txtNitConfig = new javax.swing.JTextField();
        txtNombreConfig = new javax.swing.JTextField();
        txtTelefonoConfig = new javax.swing.JTextField();
        txtDireccionConfig = new javax.swing.JTextField();
        txtRazonConfig = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtIdConfig = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0)));

        btnNuevaVenta.setBackground(new java.awt.Color(153, 0, 0));
        btnNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-nueva-ventana-31.png"))); // NOI18N
        btnNuevaVenta.setText("Nueva venta");
        btnNuevaVenta.setBorder(null);
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });

        btnClientes.setBackground(new java.awt.Color(153, 0, 0));
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-contrato-de-trabajo-31.png"))); // NOI18N
        btnClientes.setText("Creditos");
        btnClientes.setBorder(null);
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        btnProveedores.setBackground(new java.awt.Color(153, 0, 0));
        btnProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-proveedor-31.png"))); // NOI18N
        btnProveedores.setText("Proveedores");
        btnProveedores.setBorder(null);
        btnProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedoresActionPerformed(evt);
            }
        });

        btnProductos.setBackground(new java.awt.Color(153, 0, 0));
        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-producto-31.png"))); // NOI18N
        btnProductos.setText("Productos");
        btnProductos.setBorder(null);
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });

        btnVentas.setBackground(new java.awt.Color(153, 0, 0));
        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-comportamiento-de-las-ventas-31.png"))); // NOI18N
        btnVentas.setText("Ventas");
        btnVentas.setBorder(null);
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });

        btnConfig.setBackground(new java.awt.Color(153, 0, 0));
        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-engranajes-31.png"))); // NOI18N
        btnConfig.setText("Config");
        btnConfig.setBorder(null);
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/log2.jpg"))); // NOI18N

        LableVendedor.setForeground(new java.awt.Color(255, 255, 255));
        LableVendedor.setText("Barrys");

        btnRegistrar.setBackground(new java.awt.Color(153, 0, 0));
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-añadir-grupo-de-usuarios-hombre-hombre-30.png"))); // NOI18N
        btnRegistrar.setText("Usuarios");
        btnRegistrar.setBorder(null);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConfig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVentas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProductos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                    .addComponent(btnClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevaVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(LableVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LableVendedor)
                .addContainerGap(114, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 640));

        jLabel1.setBackground(new java.awt.Color(51, 51, 51));
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/bannner1.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, -100, 990, 380));

        jLabel3.setText("Código");

        jLabel4.setText("Descripción");

        jLabel5.setText("Cantidad");

        jLabel6.setText("Precio");

        jLabel7.setText("Stock disponible");

        btnEliminarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-eliminar-propiedad-24.png"))); // NOI18N
        btnEliminarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarVentaActionPerformed(evt);
            }
        });

        txtCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyTyped(evt);
            }
        });

        txtDescripcionVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionVentaActionPerformed(evt);
            }
        });
        txtDescripcionVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionVentaKeyTyped(evt);
            }
        });

        txtCantidadVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyTyped(evt);
            }
        });

        txtPrecioVenta.setEditable(false);
        txtPrecioVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioVentaKeyTyped(evt);
            }
        });

        TableVenta.setForeground(new java.awt.Color(51, 51, 51));
        TableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCIÓN", "CANTIDAD", "PRECIO", "TOTAL"
            }
        ));
        TableVenta.setSelectionForeground(new java.awt.Color(51, 51, 51));
        jScrollPane1.setViewportView(TableVenta);
        if (TableVenta.getColumnModel().getColumnCount() > 0) {
            TableVenta.getColumnModel().getColumn(0).setPreferredWidth(30);
            TableVenta.getColumnModel().getColumn(1).setPreferredWidth(100);
            TableVenta.getColumnModel().getColumn(2).setPreferredWidth(30);
            TableVenta.getColumnModel().getColumn(3).setPreferredWidth(30);
            TableVenta.getColumnModel().getColumn(4).setPreferredWidth(40);
        }

        jLabel8.setText("NIT");

        jLabel9.setText("NOMBRE");

        txtNombreVentaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreVentaClienteActionPerformed(evt);
            }
        });
        txtNombreVentaCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreVentaClienteKeyTyped(evt);
            }
        });

        btnGenerarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-imprimir-40.png"))); // NOI18N
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-dinero-25.png"))); // NOI18N
        jLabel10.setText("Total a pagar");

        labelTotal.setBackground(new java.awt.Color(51, 51, 51));
        labelTotal.setText("-----");

        btnGraficar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-reequilibrar-la-cartera-25.png"))); // NOI18N
        btnGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficarActionPerformed(evt);
            }
        });

        jLabel11.setText("Seleccionar:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txtNitVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(txtNombreVentaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGenerarVenta)
                        .addGap(153, 153, 153)
                        .addComponent(jLabel10)
                        .addGap(36, 36, 36)
                        .addComponent(labelTotal)
                        .addGap(112, 112, 112))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 965, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 178, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtDescripcionVenta)
                        .addGap(37, 37, 37)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtStockVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(txtIDProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(48, 48, 48)
                        .addComponent(btnEliminarVenta))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnGraficar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Midate, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))))
                .addGap(27, 27, 27))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnGraficar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Midate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtStockVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIDProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnEliminarVenta))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNitVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombreVentaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnGenerarVenta)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(labelTotal))))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel2);

        jLabel12.setText("Nombre");

        jLabel13.setText("NIT");

        jLabel16.setText("Cantidad");

        txtNitCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNitClienteKeyTyped(evt);
            }
        });

        txtNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreClienteKeyTyped(evt);
            }
        });

        txtCantidadCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadClienteKeyTyped(evt);
            }
        });

        TableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NIT", "Nombre", "Cantidad"
            }
        ));
        TableCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableCliente);
        if (TableCliente.getColumnModel().getColumnCount() > 0) {
            TableCliente.getColumnModel().getColumn(0).setPreferredWidth(50);
            TableCliente.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableCliente.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        btnGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-guardar-25.png"))); // NOI18N
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });

        btnBorrarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-eliminar-26.png"))); // NOI18N
        btnBorrarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarClienteActionPerformed(evt);
            }
        });

        btnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-aprobar-y-actualizar-25.png"))); // NOI18N
        btnEditarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });

        btnNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-archivo-nuevo-25.png"))); // NOI18N
        btnNuevoCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnGuardarCliente)
                            .addComponent(btnNuevoCliente))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBorrarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(5, 5, 5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNitCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidadCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtNitCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtCantidadCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(145, 145, 145)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGuardarCliente)
                            .addComponent(btnBorrarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnNuevoCliente)
                            .addComponent(btnEditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", jPanel3);

        jLabel18.setText("Nombre");

        jLabel19.setText("Teléfono");

        jLabel20.setText("Empresa");

        jLabel21.setText("NIT");

        txtNombreProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProveedorKeyTyped(evt);
            }
        });

        txtTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoProveedorKeyTyped(evt);
            }
        });

        txtEmpresaProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpresaProveedorActionPerformed(evt);
            }
        });
        txtEmpresaProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmpresaProveedorKeyTyped(evt);
            }
        });

        txtNitProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNitProveedorKeyTyped(evt);
            }
        });

        TableProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "NIT", "Nombre", "Teléfono", "Empresa"
            }
        ));
        TableProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableProveedor);
        if (TableProveedor.getColumnModel().getColumnCount() > 0) {
            TableProveedor.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableProveedor.getColumnModel().getColumn(1).setPreferredWidth(40);
            TableProveedor.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableProveedor.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableProveedor.getColumnModel().getColumn(4).setPreferredWidth(70);
        }

        btnGuardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-guardar-25.png"))); // NOI18N
        btnGuardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProveedorActionPerformed(evt);
            }
        });

        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-eliminar-26.png"))); // NOI18N
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });

        btnNuevoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-archivo-nuevo-25.png"))); // NOI18N
        btnNuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProveedorActionPerformed(evt);
            }
        });

        btnEditarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-aprobar-y-actualizar-25.png"))); // NOI18N
        btnEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(51, 51, 51)
                        .addComponent(txtNitProveedor))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel18))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreProveedor)
                            .addComponent(txtTelefonoProveedor)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(txtEmpresaProveedor))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGuardarProveedor)
                            .addComponent(btnNuevoProveedor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEliminarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(43, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtNitProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txtEmpresaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGuardarProveedor)
                            .addComponent(btnEliminarProveedor))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEditarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNuevoProveedor))
                        .addGap(55, 55, 55))))
        );

        jTabbedPane1.addTab("tab3", jPanel4);

        jLabel22.setText("Codigo:");

        jLabel23.setText("Descripción:");

        jLabel24.setText("Cantidad:");

        jLabel25.setText("Precio:");

        jLabel26.setText("Proveedor");

        txtDescripcionProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionProductoKeyTyped(evt);
            }
        });

        txtCantidadProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadProductoKeyTyped(evt);
            }
        });

        txtPrecioProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProductoKeyTyped(evt);
            }
        });

        cbxProveedorProducto.setEditable(true);

        TableProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Codigo", "Descripción", "Cantidad", "Precio", "Proveedor"
            }
        ));
        TableProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableProducto);
        if (TableProducto.getColumnModel().getColumnCount() > 0) {
            TableProducto.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableProducto.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableProducto.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableProducto.getColumnModel().getColumn(3).setPreferredWidth(40);
            TableProducto.getColumnModel().getColumn(4).setPreferredWidth(50);
            TableProducto.getColumnModel().getColumn(5).setPreferredWidth(100);
        }

        btnGuardarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-guardar-25.png"))); // NOI18N
        btnGuardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProductoActionPerformed(evt);
            }
        });

        btnEliminarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-eliminar-26.png"))); // NOI18N
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });

        btnNuevoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-archivo-nuevo-25.png"))); // NOI18N
        btnNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProductoActionPerformed(evt);
            }
        });

        btnEditarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-aprobar-y-actualizar-25.png"))); // NOI18N
        btnEditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProductoActionPerformed(evt);
            }
        });

        btnExcelProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-ms-excel-25.png"))); // NOI18N
        btnExcelProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addGap(30, 30, 30)
                                .addComponent(cbxProveedorProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(49, 49, 49)
                                .addComponent(txtPrecioProducto))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(36, 36, 36)
                                .addComponent(txtCantidadProducto))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(18, 18, 18)
                                .addComponent(txtDescripcionProducto))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(47, 47, 47)
                                .addComponent(txtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(btnNuevoProducto)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnEditarProducto))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(btnGuardarProducto)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnEliminarProducto)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(btnExcelProducto)))))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel22)
                            .addComponent(txtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23)
                            .addComponent(txtDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24)
                            .addComponent(txtCantidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25)
                            .addComponent(txtPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel26)
                            .addComponent(cbxProveedorProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardarProducto)
                            .addComponent(btnEliminarProducto))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevoProducto)
                            .addComponent(btnEditarProducto))
                        .addGap(18, 18, 18)
                        .addComponent(btnExcelProducto))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", jPanel5);

        TableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente", "Vendedor", "Total"
            }
        ));
        TableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableVentas);
        if (TableVentas.getColumnModel().getColumnCount() > 0) {
            TableVentas.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(1).setPreferredWidth(60);
            TableVentas.getColumnModel().getColumn(2).setPreferredWidth(60);
            TableVentas.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        btnPdfVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/icons8-pdf-28.png"))); // NOI18N
        btnPdfVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnPdfVentas)
                        .addGap(18, 18, 18)
                        .addComponent(txtIdVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 901, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnPdfVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        jTabbedPane1.addTab("tab5", jPanel6);

        jLabel27.setText("NIT");

        jLabel28.setText("NOMBRE");

        jLabel29.setText("TELÉFONO");

        jLabel30.setText("DIRECCIÓN");

        jLabel31.setText("RAZÓN SOCIAL");

        btnActualizarConfig.setText("ACTUALIZAR");
        btnActualizarConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarConfigActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel32.setText("DATOS DE LA EMPRESA");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(450, 450, 450)
                        .addComponent(btnActualizarConfig))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(349, 349, 349)
                        .addComponent(jLabel32)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(txtRazonConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(txtNitConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(txtNombreConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(74, 74, 74)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(txtTelefonoConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30)
                            .addComponent(txtDireccionConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(74, 74, 74))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel32)
                .addGap(27, 27, 27)
                .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNitConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefonoConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccionConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel31)
                .addGap(18, 18, 18)
                .addComponent(txtRazonConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(btnActualizarConfig)
                .addContainerGap(143, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab6", jPanel7);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, 990, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        jTabbedPane1.setSelectedIndex(4);
        LimpiarTable();
        ListarVentas();
    }//GEN-LAST:event_btnVentasActionPerformed

    private void txtDescripcionVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionVentaActionPerformed

    private void txtEmpresaProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpresaProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmpresaProveedorActionPerformed

    private void btnActualizarConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarConfigActionPerformed
        if (!"".equals(txtNitConfig.getText()) || !"".equals(txtNombreConfig.getText()) || !"".equals(txtTelefonoConfig.getText()) || !"".equals(txtDireccionConfig.getText()) || !"".equals(txtRazonConfig.getText())) {
                conf.setNIT(Integer.parseInt(txtNitConfig.getText()));
                conf.setNombre(txtNombreConfig.getText());
                conf.setTelefono(Integer.parseInt(txtTelefonoConfig.getText()));
                conf.setDireccion(txtDireccionConfig.getText());
                conf.setRazon(txtRazonConfig.getText());
                conf.setId(Integer.parseInt(txtIdConfig.getText()));

                proDao.ModificarDatos(conf);
                JOptionPane.showMessageDialog(null, "Los datos de la empresa se han actualizado");
                ListarConfig();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos están vacíos");
            }
    }//GEN-LAST:event_btnActualizarConfigActionPerformed

    private void txtNombreVentaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreVentaClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreVentaClienteActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        if (!"".equals(txtNitCliente.getText()) || !"".equals(txtNombreCliente.getText()) || !"".equals(txtCantidadCliente.getText())) {
            cl.setNIT(Integer.parseInt(txtNitCliente.getText()));
            cl.setNombre(txtNombreCliente.getText());
            cl.setCantidad(Double.parseDouble(txtCantidadCliente.getText()));
            cliente.RegistrarCliente(cl);
            LimpiarTable();
            LimpiarCliente();
            ListarCliente();
            JOptionPane.showMessageDialog(null, "Cliente Registrado");
        } else {
            JOptionPane.showMessageDialog(null, "Los campos están vacios");
        }

    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        // TODO add your handling code here:
        LimpiarTable();
        ListarCliente();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnGuardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProveedorActionPerformed
        if (!"".equals(txtNitProveedor.getText()) || !"".equals(txtNombreProveedor.getText()) || !"".equals(txtTelefonoProveedor.getText()) || !"".equals(txtEmpresaProveedor.getText())) {
            pr.setNIT(Integer.parseInt(txtNitProveedor.getText()));
            pr.setNombre(txtNombreProveedor.getText());
            pr.setTelefono(Integer.parseInt(txtTelefonoProveedor.getText()));
            pr.setEmpresa(txtEmpresaProveedor.getText());
            prDao.RegistrarProveedor(pr);

            LimpiarTable();
            ListarProveedor();
            LimpiarProveedor();

            JOptionPane.showMessageDialog(null, "El Proveedor se ha registrado");

        } else {
            JOptionPane.showMessageDialog(null, "Los campos están vacios");
        }
    }//GEN-LAST:event_btnGuardarProveedorActionPerformed

    private void btnProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedoresActionPerformed
        LimpiarTable();
        ListarProveedor();
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_btnProveedoresActionPerformed

    private void btnGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProductoActionPerformed
        if (!"".equals(txtCodigoProducto.getText()) || !"".equals(txtDescripcionProducto.getText()) || !"".equals(txtCantidadProducto.getText()) || !"".equals(txtPrecioProducto.getText()) || !"".equals(cbxProveedorProducto.getSelectedItem())) {
            pro.setCodigo(txtCodigoProducto.getText());
            pro.setDescripcion(txtDescripcionProducto.getText());
            pro.setCantidad(Integer.parseInt(txtCantidadProducto.getText()));
            pro.setPrecio(Double.parseDouble(txtPrecioProducto.getText()));
            pro.setProveedor(cbxProveedorProducto.getSelectedItem().toString());

            proDao.RegistrarProductos(pro);
            LimpiarTable();
            ListarProductos();
            LimpiarProductos();

            JOptionPane.showMessageDialog(null, "El producto se ha registrado");

        } else {
            JOptionPane.showMessageDialog(null, "Los campos está vacios");
        }
    }//GEN-LAST:event_btnGuardarProductoActionPerformed

    private void TableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableClienteMouseClicked
        int fila = TableCliente.rowAtPoint(evt.getPoint());
        txtIdCliente.setText(TableCliente.getValueAt(fila, 0).toString());
        txtNitCliente.setText(TableCliente.getValueAt(fila, 1).toString());
        txtNombreCliente.setText(TableCliente.getValueAt(fila, 2).toString());
        txtCantidadCliente.setText(TableCliente.getValueAt(fila, 3).toString());
    }//GEN-LAST:event_TableClienteMouseClicked

    private void btnBorrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarClienteActionPerformed
        if (!"".equals(txtIdCliente.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar?");
            if (pregunta == 0) {
                int Id = Integer.parseInt(txtIdCliente.getText());
                cliente.EliminarCliente(Id);
                LimpiarTable();
                LimpiarCliente();
                ListarCliente();
            }
        }
    }//GEN-LAST:event_btnBorrarClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        if ("".equals(txtIdCliente.getText())) {

            JOptionPane.showMessageDialog(null, "Selececcione una fila");

        } else {

            if (!"".equals(txtNitCliente.getText()) && !"".equals(txtNombreCliente.getText()) && !"".equals(txtCantidadCliente.getText()) && !"".equals(txtIdCliente.getText())) {
                cl.setNIT(Integer.parseInt(txtNitCliente.getText()));
                cl.setNombre(txtNombreCliente.getText());
                cl.setCantidad(Double.parseDouble(txtCantidadCliente.getText()));
                cl.setId(Integer.parseInt(txtIdCliente.getText()));

                cliente.ModificarCliente(cl);
                JOptionPane.showMessageDialog(null, "El cliante se ha actualizado");
                LimpiarTable();
                LimpiarCliente();
                ListarCliente();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos están vacíos");
            }
        }
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        LimpiarCliente();
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void TableProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProveedorMouseClicked
        int fila = TableProveedor.rowAtPoint(evt.getPoint());
        txtIdProveedor.setText(TableProveedor.getValueAt(fila, 0).toString());
        txtNitProveedor.setText(TableProveedor.getValueAt(fila, 1).toString());
        txtNombreProveedor.setText(TableProveedor.getValueAt(fila, 2).toString());
        txtTelefonoProveedor.setText(TableProveedor.getValueAt(fila, 3).toString());
        txtEmpresaProveedor.setText(TableProveedor.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_TableProveedorMouseClicked

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        if (!"".equals(txtIdProveedor.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar?");

            if (pregunta == 0) {
                int Id = Integer.parseInt(txtIdProveedor.getText());
                prDao.EliminarProveedor(Id);

                LimpiarTable();
                ListarProveedor();
                LimpiarProveedor();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProveedorActionPerformed
        if ("".equals(txtIdProveedor.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");

        } else {

            if (!"".equals(txtNitProveedor.getText()) || !"".equals(txtNombreProveedor.getText()) || !"".equals(txtTelefonoProveedor.getText()) || !"".equals(txtEmpresaProveedor.getText())) {
                pr.setNIT(Integer.parseInt(txtNitProveedor.getText()));
                pr.setNombre(txtNombreProveedor.getText());
                pr.setTelefono(Integer.parseInt(txtTelefonoProveedor.getText()));
                pr.setEmpresa(txtEmpresaProveedor.getText());
                pr.setId(Integer.parseInt(txtIdProveedor.getText()));

                prDao.ModificarProveedor(pr);
                JOptionPane.showMessageDialog(null, "El proveeeor se ha actualizado");
                LimpiarTable();
                ListarProveedor();
                LimpiarProveedor();
            }
        }
    }//GEN-LAST:event_btnEditarProveedorActionPerformed

    private void btnNuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProveedorActionPerformed
        LimpiarProveedor();
    }//GEN-LAST:event_btnNuevoProveedorActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        LimpiarTable();
        ListarProductos();
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_btnProductosActionPerformed

    private void TableProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProductoMouseClicked
        int fila = TableProducto.rowAtPoint(evt.getPoint());
        txtIdProducto.setText(TableProducto.getValueAt(fila, 0).toString());
        txtCodigoProducto.setText(TableProducto.getValueAt(fila, 1).toString());
        txtDescripcionProducto.setText(TableProducto.getValueAt(fila, 2).toString());
        txtCantidadProducto.setText(TableProducto.getValueAt(fila, 3).toString());
        txtPrecioProducto.setText(TableProducto.getValueAt(fila, 4).toString());
        cbxProveedorProducto.setSelectedItem(TableProducto.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_TableProductoMouseClicked

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        if (!"".equals(txtIdProducto.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");

            int pregunta = JOptionPane.showConfirmDialog(null, "¿Seguro que desea eliminar?");
            if (pregunta == 0) {
                int Id = Integer.parseInt(txtIdProducto.getText());
                proDao.EliminarProductos(Id);
                LimpiarTable();
                LimpiarProductos();
                ListarProductos();
            }
        }
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnEditarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProductoActionPerformed
        if ("".equals(txtIdProducto.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");

        } else {

            if (!"".equals(txtCodigoProducto.getText()) || !"".equals(txtDescripcionProducto.getText()) || !"".equals(txtCantidadProducto.getText()) || !"".equals(txtPrecioProducto.getText())) {
                pro.setCodigo(txtCodigoProducto.getText());
                pro.setDescripcion(txtDescripcionProducto.getText());
                pro.setCantidad(Integer.parseInt(txtCantidadProducto.getText()));
                pro.setPrecio(Double.parseDouble(txtPrecioProducto.getText()));
                pro.setProveedor(cbxProveedorProducto.getSelectedItem().toString());
                pro.setId(Integer.parseInt(txtIdProducto.getText()));

                proDao.ModificarProductos(pro);
                JOptionPane.showMessageDialog(null, "El producto se ha actualizado");
                LimpiarTable();
                ListarProductos();
                LimpiarProductos();
            }
        }
    }//GEN-LAST:event_btnEditarProductoActionPerformed

    private void btnExcelProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelProductoActionPerformed
        Excel.reporte();
    }//GEN-LAST:event_btnExcelProductoActionPerformed

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            if (!"".equals(txtCodigoVenta.getText())) {
                String cod = txtCodigoVenta.getText();
                pro = proDao.BucarPro(cod);

                if (pro.getDescripcion() != null) {
                    txtDescripcionVenta.setText("" + pro.getDescripcion());
                    txtPrecioVenta.setText("" + pro.getPrecio());
                    txtStockVenta.setText("" + pro.getCantidad());
                    txtCantidadVenta.requestFocus();

                } else {
                    LimpiarVenta();

                    txtCodigoVenta.requestFocus();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto");
                txtCodigoVenta.requestFocus();
            }

        }
    }//GEN-LAST:event_txtCodigoVentaKeyPressed

    private void txtCantidadVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            if (!"".equals(txtCantidadVenta.getText())) {
                String cod = txtCodigoVenta.getText();
                String descripcion = txtDescripcionVenta.getText();
                int cantidad = Integer.parseInt(txtCantidadVenta.getText());
                double precio = Double.parseDouble(txtPrecioVenta.getText());
                double total = cantidad * precio;
                int stock = Integer.parseInt(txtStockVenta.getText());

                if (stock >= cantidad) {
                    item = item + 1;
                    tmp = (DefaultTableModel) TableVenta.getModel();

                    for (int i = 0; i < TableVenta.getRowCount(); i++) {
                        if (TableVenta.getValueAt(i, 1).equals(txtDescripcionVenta.getText())) {
                            JOptionPane.showMessageDialog(null, "El producto ya se ha registrado");

                            return;
                        }

                    }

                    ArrayList Lista = new ArrayList();
                    Lista.add(item);
                    Lista.add(cod);
                    Lista.add(descripcion);
                    Lista.add(cantidad);
                    Lista.add(precio);
                    Lista.add(total);

                    Object[] O = new Object[5];
                    O[0] = Lista.get(1);
                    O[1] = Lista.get(2);
                    O[2] = Lista.get(3);
                    O[3] = Lista.get(4);
                    O[4] = Lista.get(5);

                    tmp.addRow(O);
                    TableVenta.setModel(tmp);
                    TotalPagar();
                    LimpiarVenta();

                    txtCodigoVenta.requestFocus();

                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible");

                }

            } else {
                JOptionPane.showMessageDialog(null, "Ingrese cantidad");

            }
        }
    }//GEN-LAST:event_txtCantidadVentaKeyPressed

    private void btnEliminarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarVentaActionPerformed
        modelo = (DefaultTableModel) TableVenta.getModel();
        modelo.removeRow(TableVenta.getSelectedRow());

        TotalPagar();
        txtCodigoVenta.requestFocus();
    }//GEN-LAST:event_btnEliminarVentaActionPerformed

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        if (TableVenta.getRowCount() > 0) {
            if (!"".equals(txtNombreVentaCliente.getText())) {
                RegistrarVenta();
                RegistrarDetalle();
                ActualizarStock();
                pdf();
                LimpiarTableVenta();
                LimpiarClienteVenta();
            }else{
                JOptionPane.showMessageDialog(null, "Ingrese un Nombre");
                
            }

        }else{
            JOptionPane.showMessageDialog(null, "No hay productos en la venta");
        }
    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void btnNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProductoActionPerformed
        LimpiarProductos();
    }//GEN-LAST:event_btnNuevoProductoActionPerformed

    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    private void txtCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoVentaKeyTyped

    private void txtDescripcionVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionVentaKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtDescripcionVentaKeyTyped

    private void txtCantidadVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadVentaKeyTyped

    private void txtPrecioVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioVentaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioVentaKeyTyped

    private void txtNombreVentaClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreVentaClienteKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreVentaClienteKeyTyped

    private void txtNitClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNitClienteKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtNitClienteKeyTyped

    private void txtNombreClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreClienteKeyTyped

    private void txtCantidadClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadClienteKeyTyped
        event.numberDecimalKeyPress(evt, txtCantidadCliente);
    }//GEN-LAST:event_txtCantidadClienteKeyTyped

    private void txtNitProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNitProveedorKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtNitProveedorKeyTyped

    private void txtNombreProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProveedorKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreProveedorKeyTyped

    private void txtTelefonoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoProveedorKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtTelefonoProveedorKeyTyped

    private void txtEmpresaProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmpresaProveedorKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtEmpresaProveedorKeyTyped

    private void txtDescripcionProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionProductoKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtDescripcionProductoKeyTyped

    private void txtCantidadProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadProductoKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadProductoKeyTyped

    private void txtPrecioProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProductoKeyTyped
        event.numberDecimalKeyPress(evt, txtPrecioProducto);
    }//GEN-LAST:event_txtPrecioProductoKeyTyped

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_btnConfigActionPerformed

    private void TableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseClicked
        int fila = TableVentas.rowAtPoint(evt.getPoint());
        txtIdVentas.setText(TableVentas.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_TableVentasMouseClicked

    private void btnPdfVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentasActionPerformed
        
        try {
            int id = Integer.parseInt(txtIdVentas.getText());
            
            File file = new File("src/pdf/venta" + id + ".pdf");
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPdfVentasActionPerformed

    private void btnGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficarActionPerformed
        String fechaReporte = new SimpleDateFormat("dd/MM/yyyy").format(Midate.getDate());
        Grafico.Graficar(fechaReporte); 
    }//GEN-LAST:event_btnGraficarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        Registro reg = new Registro();
        reg.setVisible(true);
    }//GEN-LAST:event_btnRegistrarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LableVendedor;
    private com.toedter.calendar.JDateChooser Midate;
    private javax.swing.JTable TableCliente;
    private javax.swing.JTable TableProducto;
    private javax.swing.JTable TableProveedor;
    private javax.swing.JTable TableVenta;
    private javax.swing.JTable TableVentas;
    private javax.swing.JButton btnActualizarConfig;
    private javax.swing.JButton btnBorrarCliente;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarProducto;
    private javax.swing.JButton btnEditarProveedor;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnEliminarVenta;
    private javax.swing.JButton btnExcelProducto;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnGraficar;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarProducto;
    private javax.swing.JButton btnGuardarProveedor;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoProducto;
    private javax.swing.JButton btnNuevoProveedor;
    private javax.swing.JButton btnPdfVentas;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedores;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnVentas;
    private javax.swing.JComboBox cbxProveedorProducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JTextField txtCantidadCliente;
    private javax.swing.JTextField txtCantidadProducto;
    private javax.swing.JTextField txtCantidadVenta;
    private javax.swing.JTextField txtCodigoProducto;
    private javax.swing.JTextField txtCodigoVenta;
    private javax.swing.JTextField txtDescripcionProducto;
    private javax.swing.JTextField txtDescripcionVenta;
    private javax.swing.JTextField txtDireccionConfig;
    private javax.swing.JTextField txtEmpresaProveedor;
    private javax.swing.JTextField txtIDProducto;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdConfig;
    private javax.swing.JTextField txtIdProducto;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtIdVentas;
    private javax.swing.JTextField txtNitCliente;
    private javax.swing.JTextField txtNitConfig;
    private javax.swing.JTextField txtNitProveedor;
    private javax.swing.JTextField txtNitVenta;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreConfig;
    private javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtNombreVentaCliente;
    private javax.swing.JTextField txtPrecioProducto;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtRazonConfig;
    private javax.swing.JTextField txtStockVenta;
    private javax.swing.JTextField txtTelefonoConfig;
    private javax.swing.JTextField txtTelefonoProveedor;
    // End of variables declaration//GEN-END:variables

    private void LimpiarCliente() {
        txtIdCliente.setText("");
        txtNitCliente.setText("");
        txtNombreCliente.setText("");
        txtCantidadCliente.setText("");
    }

    private void LimpiarProveedor() {
        txtIdProveedor.setText("");
        txtNitProveedor.setText("");
        txtNombreProveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtEmpresaProveedor.setText("");
    }

    private void LimpiarProductos() {
        txtIdProducto.setText("");
        txtCodigoProducto.setText("");
        txtDescripcionProducto.setText("");
        txtCantidadProducto.setText("");
        txtPrecioProducto.setText("");
        cbxProveedorProducto.setSelectedItem(null);
    }

    private void TotalPagar() {
        Totalpagar = 0.00;
        int numFila = TableVenta.getRowCount();

        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(TableVenta.getModel().getValueAt(i, 4)));

            Totalpagar = Totalpagar + cal;
        }
        labelTotal.setText(String.format("%.2f", Totalpagar));

    }

    private void LimpiarVenta() {
        txtCodigoVenta.setText("");
        txtDescripcionVenta.setText("");
        txtCantidadVenta.setText("");
        txtStockVenta.setText("");
        txtPrecioVenta.setText("");
        txtIdVentas.setText("");
    }

    private void RegistrarVenta() {
        String Cliente = txtNombreVentaCliente.getText();
        String Vendedor = LableVendedor.getText();
        double monto = Totalpagar;

        v.setCliente(Cliente);
        v.setVendedor(Vendedor);
        v.setTotal(monto);
        v.setFecha(fechaActual);
        
        Vdao.RegistrarVenta(v);

    }

    private void RegistrarDetalle() {
        int Id = Vdao.Id_venta();

        for (int i = 0; i < TableVenta.getRowCount(); i++) {

            String Codigo = TableVenta.getValueAt(i, 0).toString();
            int Cantidad = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
            double Precio = Double.parseDouble(TableVenta.getValueAt(i, 3).toString());

            Dv.setCodPro(Codigo);
            Dv.setCantidad(Cantidad);
            Dv.setPrecio(Precio);
            Dv.setId(Id);

            Vdao.RegistrarDetalle(Dv);

        }

    }

    private void ActualizarStock() {
        for (int i = 0; i < TableVenta.getRowCount(); i++) {

            String Codigo = TableVenta.getValueAt(i, 0).toString();
            int Cantidad = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());

            pro = proDao.BucarPro(Codigo);

            int StockActual = pro.getCantidad() - Cantidad;

            Vdao.ActualizarStock(StockActual, Codigo);

        }
    }

    private void LimpiarTableVenta() {
        tmp = (DefaultTableModel) TableVenta.getModel();

        int fila = TableVenta.getRowCount();

        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);

        }
    }

    private void LimpiarClienteVenta() {
        txtNitVenta.setText("");
        txtNombreVentaCliente.setText("");
    }

    private void pdf() {
        try {
            int id = Vdao.Id_venta();

            FileOutputStream archivo;
            File file = new File("src/pdf/Venta" + id + ".pdf");
            archivo = new FileOutputStream(file);

            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            Image img = Image.getInstance("src/Img/11.jpg");

            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.RED);
            fecha.add(Chunk.NEWLINE);
            Date date = new Date();
            fecha.add("Factura: " + id + "\n" + "Fecha: " + new SimpleDateFormat("dd-mm-yyyy").format(date) + "\n\n");

            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] ColumnaEncabezado = new float[]{20f, 40f, 70f, 40f};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            Encabezado.addCell(img);

            String NIT = txtNitConfig.getText();
            String nom = txtNombreConfig.getText();
            String tel = txtTelefonoConfig.getText();
            String dir = txtDireccionConfig.getText();
            String ra = txtRazonConfig.getText();

            Encabezado.addCell("");
            Encabezado.addCell("NIT: " + NIT + "\nNombre: " + nom + "Telefono: " + "\nTeléfono: " + tel + "\nDirección: " + dir + "\nRazón: " + ra);
            Encabezado.addCell(fecha);
            doc.add(Encabezado);

            //Clientes
            Paragraph cli = new Paragraph();
            cli.add(Chunk.NEWLINE);
            cli.add("Datos Del Cliente" + "\n\n");
            doc.add(cli);

            PdfPTable tablacli = new PdfPTable(2);
            tablacli.setWidthPercentage(100);
            tablacli.getDefaultCell().setBorder(0);
            float[] Columnacli = new float[]{20f, 50f};
            tablacli.setWidths(Columnacli);
            tablacli.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell cl1 = new PdfPCell(new Phrase("NIT", negrita));
            PdfPCell cl2 = new PdfPCell(new Phrase("Nombre", negrita));
            cl1.setBorder(0);
            cl2.setBorder(0);
            

            tablacli.addCell(cl1);
            tablacli.addCell(cl2);
            tablacli.addCell(txtNitVenta.getText());
            tablacli.addCell(txtNombreVentaCliente.getText());
            doc.add(tablacli);

            //Productos
            PdfPTable tablapro = new PdfPTable(4);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            float[] Columnapro = new float[]{10f, 50f, 15f, 20f};
            tablapro.setWidths(Columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell pro1 = new PdfPCell(new Phrase("Cant.", negrita));
            PdfPCell pro2 = new PdfPCell(new Phrase("Descripción", negrita));
            PdfPCell pro3 = new PdfPCell(new Phrase("Precio U.", negrita));
            PdfPCell pro4 = new PdfPCell(new Phrase("Precio T.", negrita));
            pro1.setBorder(0);
            pro2.setBorder(0);
            pro3.setBorder(0);
            pro4.setBorder(0);

            pro1.setBackgroundColor(BaseColor.DARK_GRAY);
            pro2.setBackgroundColor(BaseColor.DARK_GRAY);
            pro3.setBackgroundColor(BaseColor.DARK_GRAY);
            pro4.setBackgroundColor(BaseColor.DARK_GRAY);

            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);

            for (int i = 0; i < TableVenta.getRowCount(); i++) {
                String producto = TableVenta.getValueAt(i, 1).toString();
                String cantidad = TableVenta.getValueAt(i, 2).toString();
                String precio = TableVenta.getValueAt(i, 3).toString();
                String total = TableVenta.getValueAt(i, 4).toString();

                tablapro.addCell(cantidad);
                tablapro.addCell(producto);
                tablapro.addCell(precio);
                tablapro.addCell(total);

            }

            doc.add(tablapro);

            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total a pagar: Q" + Totalpagar);
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);

            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Cancelación y firma\n\n");
            firma.add("------------------------");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);

            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("Gracias por su compra");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);

            doc.close();
            archivo.close();
            Desktop.getDesktop().open(file);

        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }

}
