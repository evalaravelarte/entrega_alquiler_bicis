package alquiler_bicis;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import tema8Ejercicio5_BD_Tienda.ConnectionSingleton;

class ConnectionSingleton {

    private static Connection con;

    public static Connection getConnection() throws SQLException {
	String url = "jdbc:mysql://127.0.0.1:3307/alquiler_bicis";
	String user = "alumno";
	String password = "alumno";
	if (con == null || con.isClosed()) {
	    con = DriverManager.getConnection(url, user, password);
	}
	return con;
    }
}

public class Alquiler_bicis {

    public static final String FILA_OCULTA = "Bici ref: 100";

    private JFrame frameTienda;
    private DefaultTableModel modelBi;
    private DefaultTableModel modelUs;
    private JTable table2;
    private static Connection con;
    private JPanel panel = new JPanel();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

	try {

	    JFrame.setDefaultLookAndFeelDecorated(true);
	    JDialog.setDefaultLookAndFeelDecorated(true);
	    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

	} catch (ClassNotFoundException e1) {
	    e1.printStackTrace();
	} catch (InstantiationException e1) {
	    e1.printStackTrace();
	} catch (IllegalAccessException e1) {
	    e1.printStackTrace();
	} catch (UnsupportedLookAndFeelException e1) {
	    e1.printStackTrace();
	}

	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    Alquiler_bicis window = new Alquiler_bicis();
		    window.frameTienda.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the application.
     */
    public Alquiler_bicis() {
	initialize();
    }

    /**
     * Actualiza y bloquea la primera línea de la bd bicis para ocultar la bici 100.
     */
    void actualizaBDprimLinea() {

	modelBi = (DefaultTableModel) table2.getModel();
	for (int i = 0; i < modelBi.getColumnCount(); i++) {
	    modelBi.setValueAt(FILA_OCULTA, 0, i);
	}
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

	JLabel lblUsuariosBD;
	JLabel lblBicisBD;
	JButton btnMostrarUs;
	JScrollPane scrollPaneUs;
	JScrollPane scrollPaneBi;
	JLabel lblCUcod;
	JTable table;
	JTextField txtFCUcod;
	JTextField txtFCUnom;
	JLabel lblCUnom;
	JLabel lblCBcod;
	JLabel lblEstadoBi;
	JTextField txtFCBcod;
	JLabel lblDevCodUsu;
	JLabel lblDevCodBici;
	JLabel lblAlqCodUsu;
	JLabel lblAlqCodBici;
	JButton btnAlq;
	JButton btnActualizarBD;
	JComboBox cBoxDevCodUs;
	JComboBox cBoxAlqCodUs;
	JComboBox cBoxDevCodBic;
	JComboBox cBoxAlqCodBic;
	JComboBox cBoxEstadoBi;

	/**
	 * VENTANA
	 */
	frameTienda = new JFrame();
	frameTienda.getContentPane().setFont(new Font("Silom", Font.BOLD, 15));
	frameTienda.setBounds(100, 100, 1200, 800);
	frameTienda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frameTienda.getContentPane().setLayout(null);
	frameTienda.setResizable(false);

	/**
	 * ETIQUETAS BD
	 */
	lblUsuariosBD = new JLabel("USUARIOS");
	lblUsuariosBD.setFont(new Font("Silom", Font.BOLD, 20));
	lblUsuariosBD.setBounds(265, 44, 101, 20);
	frameTienda.getContentPane().add(lblUsuariosBD);

	lblBicisBD = new JLabel("BICICLETAS");
	lblBicisBD.setFont(new Font("Silom", Font.BOLD, 20));
	lblBicisBD.setBounds(802, 44, 138, 20);
	frameTienda.getContentPane().add(lblBicisBD);

	/**
	 * BDs
	 */
	modelUs = new DefaultTableModel();
	modelUs.addColumn("ID");
	modelUs.addColumn("Nombre");
	modelUs.addColumn("ID Bici");
	// modelUs.addColumn("Dni");
	// modelUs.addColumn("Fecha Alq");

	table = new JTable(modelUs);
	table.setBounds(67, 81, 425, 144);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	frameTienda.getContentPane().add(table);

	scrollPaneUs = new JScrollPane(table);
	scrollPaneUs.setBounds(65, 76, 500, 218);
	frameTienda.getContentPane().add(scrollPaneUs);

	modelBi = new DefaultTableModel();
	modelBi.addColumn("ID");
	// modelBi.addColumn("Modelo");
	modelBi.addColumn("Estado");
	// modelBi.addColumn("Fecha Dev");

	table2 = new JTable(modelBi);
	table2.setBounds(571, 47, 425, 144);
	table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	frameTienda.getContentPane().add(table2);

	scrollPaneBi = new JScrollPane(table2);
	scrollPaneBi.setBounds(621, 76, 500, 218);
	frameTienda.getContentPane().add(scrollPaneBi);

	cBoxDevCodUs = new JComboBox();
	cBoxDevCodUs.setBounds(215, 565, 85, 24);
	frameTienda.getContentPane().add(cBoxDevCodUs);

	cBoxDevCodBic = new JComboBox();
	cBoxDevCodBic.setBounds(454, 565, 85, 24);
	frameTienda.getContentPane().add(cBoxDevCodBic);

	cBoxAlqCodUs = new JComboBox();
	cBoxAlqCodUs.setBounds(791, 565, 85, 24);
	frameTienda.getContentPane().add(cBoxAlqCodUs);

	cBoxAlqCodBic = new JComboBox();
	cBoxAlqCodBic.setBounds(1018, 565, 85, 24);
	frameTienda.getContentPane().add(cBoxAlqCodBic);

	cBoxEstadoBi = new JComboBox();
	cBoxEstadoBi.setBounds(987, 375, 116, 24);
	frameTienda.getContentPane().add(cBoxEstadoBi);

	/**
	 * ACTUALIZA BD USER/BICI Y CBOX USER/BICI
	 */
	btnActualizarBD = new JButton("Actualizar");
	btnActualizarBD.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		try {
		    Connection con = ConnectionSingleton.getConnection();
		    Statement updBDus_stmt = con.createStatement();
		    ResultSet updBDus_rs = updBDus_stmt.executeQuery("SELECT * FROM usuario");
		    modelUs.setRowCount(0);
		    while (updBDus_rs.next()) {
			Object[] row = new Object[3];
			row[0] = updBDus_rs.getInt("cod_usuario");
			row[1] = updBDus_rs.getString("nombre");
			row[2] = updBDus_rs.getInt("bici_cod_bici");
			modelUs.addRow(row);
		    }
		    updBDus_stmt.close();
		    updBDus_rs.close();

		    Statement updBDbi_stmt = con.createStatement();
		    ResultSet updBDbi_rs = updBDbi_stmt.executeQuery("SELECT * FROM bici");
		    modelBi.setRowCount(0);
		    while (updBDbi_rs.next()) {

			Object[] row = new Object[4];
			row[0] = updBDbi_rs.getInt("cod_bici");
			// row[1] = rs.getString("modelo");
			row[1] = updBDbi_rs.getString("libre");
			// row[2] = rs.getString("fechaDevo");
			modelBi.addRow(row);
		    }
		    updBDbi_rs.close();
		    updBDbi_stmt.close();
		    con.close();

		} catch (SQLException e1) {
		    e1.printStackTrace();
		    System.out.println("Error: bd no cargada");
		    JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		try {

		    Connection con = ConnectionSingleton.getConnection();
		    Statement updCBxUs_stmt = con.createStatement();
		    ResultSet updCBxUs_rs = updCBxUs_stmt.executeQuery("SELECT cod_usuario FROM usuario");
		    cBoxDevCodUs.removeAllItems();
		    cBoxAlqCodUs.removeAllItems();
		    while (updCBxUs_rs.next()) {
			int codUsu = updCBxUs_rs.getInt("cod_usuario");
			cBoxDevCodUs.addItem(codUsu);
			cBoxAlqCodUs.addItem(codUsu);

		    }
		    updCBxUs_stmt.close();
		    updCBxUs_rs.close();

		    Statement updCBxBi_stmt = con.createStatement();
		    ResultSet updCBxBi_rs = updCBxBi_stmt.executeQuery("SELECT cod_bici FROM bici");
		    cBoxDevCodBic.removeAllItems();
		    cBoxAlqCodBic.removeAllItems();
		    while (updCBxBi_rs.next()) {

			int codBici = updCBxBi_rs.getInt("cod_bici");
			cBoxDevCodBic.addItem(codBici);
			cBoxAlqCodBic.addItem(codBici);

		    }
		    con.close();
		    updCBxBi_stmt.close();
		    updCBxBi_rs.close();

		} catch (SQLException e2) {
		    e2.printStackTrace();
		    System.out.println("Error al cargar cBox 1");
		    JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (NullPointerException e2) {
		    System.out.println("Error al cargar cBox 2");
		}

	    }
	});
	btnActualizarBD.setFont(new Font("Silom", Font.BOLD, 10));
	btnActualizarBD.setBounds(546, 44, 103, 20);
	btnActualizarBD.setVisible(false);
	frameTienda.getContentPane().add(btnActualizarBD);
	btnActualizarBD.doClick();

	/**
	 * CREAR USER L/T
	 */
	lblCUcod = new JLabel("Código:");
	lblCUcod.setFont(new Font("Silom", Font.BOLD, 15));
	lblCUcod.setBounds(78, 379, 81, 17);
	frameTienda.getContentPane().add(lblCUcod);

	txtFCUcod = new JTextField();
	txtFCUcod.setBounds(157, 371, 85, 33);
	frameTienda.getContentPane().add(txtFCUcod);
	txtFCUcod.setColumns(10);

	lblCUnom = new JLabel("Nombre:");
	lblCUnom.setFont(new Font("Silom", Font.BOLD, 15));
	lblCUnom.setBounds(297, 380, 85, 15);
	frameTienda.getContentPane().add(lblCUnom);

	txtFCUnom = new JTextField();
	txtFCUnom.setColumns(10);
	txtFCUnom.setBounds(382, 371, 168, 33);
	frameTienda.getContentPane().add(txtFCUnom);

	/**
	 * CREAR BI L/T
	 */
	lblCBcod = new JLabel("Código:");
	lblCBcod.setFont(new Font("Silom", Font.BOLD, 15));
	lblCBcod.setBounds(679, 379, 81, 17);
	frameTienda.getContentPane().add(lblCBcod);

	txtFCBcod = new JTextField();
	txtFCBcod.setColumns(10);
	txtFCBcod.setBounds(757, 371, 85, 33);
	frameTienda.getContentPane().add(txtFCBcod);

	lblDevCodUsu = new JLabel("Código usuario:");
	lblDevCodUsu.setFont(new Font("Silom", Font.PLAIN, 15));
	lblDevCodUsu.setBounds(78, 569, 138, 17);
	frameTienda.getContentPane().add(lblDevCodUsu);

	lblEstadoBi = new JLabel("Estado:");
	lblEstadoBi.setFont(new Font("Silom", Font.BOLD, 15));
	lblEstadoBi.setBounds(907, 379, 68, 17);
	frameTienda.getContentPane().add(lblEstadoBi);

	/**
	 * DEVO BI US
	 */
	lblDevCodBici = new JLabel("Código bici:");
	lblDevCodBici.setFont(new Font("Silom", Font.BOLD, 15));
	lblDevCodBici.setBounds(349, 569, 98, 17);
	frameTienda.getContentPane().add(lblDevCodBici);

	/**
	 * ALQ BI
	 */
	lblAlqCodUsu = new JLabel("Código usuario:");
	lblAlqCodUsu.setFont(new Font("Silom", Font.BOLD, 15));
	lblAlqCodUsu.setBounds(657, 569, 127, 17);
	frameTienda.getContentPane().add(lblAlqCodUsu);

	lblAlqCodBici = new JLabel("Código bici:");
	lblAlqCodBici.setFont(new Font("Silom", Font.BOLD, 15));
	lblAlqCodBici.setBounds(914, 569, 99, 17);
	frameTienda.getContentPane().add(lblAlqCodBici);

	btnAlq = new JButton("Alquilar");
	btnAlq.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		try {

		    int codUsuAlq = 0;
		    int checkCodBici = 0;
		    int codBiciLibre = 0;

		    Connection con = ConnectionSingleton.getConnection();
		    PreparedStatement alqSelUs_stmt = con.prepareStatement("SELECT * FROM usuario WHERE cod_usuario=?");
		    alqSelUs_stmt.setInt(1, (int) cBoxAlqCodUs.getSelectedItem());
		    ResultSet alqSelUs_rs = alqSelUs_stmt.executeQuery();
		    while (alqSelUs_rs.next()) {

			codUsuAlq = alqSelUs_rs.getInt("cod_usuario");
			checkCodBici = alqSelUs_rs.getInt("bici_cod_bici");

		    }
		    alqSelUs_stmt.close();
		    alqSelUs_rs.close();

		    if (checkCodBici == 100) {

			PreparedStatement alqSelBi_stmt = con.prepareStatement("SELECT * FROM bici WHERE cod_bici=?");
			alqSelBi_stmt.setInt(1, (int) cBoxAlqCodBic.getSelectedItem());
			ResultSet alqSelBi_rs = alqSelBi_stmt.executeQuery();
			while (alqSelBi_rs.next()) {

			    codBiciLibre = alqSelBi_rs.getInt("cod_bici");

			}
			alqSelBi_stmt.close();
			alqSelBi_rs.close();

			if (codBiciLibre != 100) {

			    PreparedStatement updUsuBi_pstmt = con
				    .prepareStatement("UPDATE usuario SET bici_cod_bici = ? WHERE cod_usuario=?");
			    updUsuBi_pstmt.setInt(1, (int) cBoxAlqCodBic.getSelectedItem());
			    updUsuBi_pstmt.setInt(2, (int) cBoxAlqCodUs.getSelectedItem());
			    updUsuBi_pstmt.executeUpdate();

			    updUsuBi_pstmt.close();

			    PreparedStatement updBDestado_pstmt = con
				    .prepareStatement("UPDATE bici SET libre = ? WHERE cod_bici=?");
			    updBDestado_pstmt.setString(1, "Alquilada");
			    updBDestado_pstmt.setInt(2, (int) cBoxAlqCodBic.getSelectedItem());
			    updBDestado_pstmt.executeUpdate();

			    updBDestado_pstmt.close();
			    con.close();
			    btnActualizarBD.doClick();
			    JOptionPane.showMessageDialog(null, "Alquiler completado");

			} else if (codBiciLibre == 100) {
			    JOptionPane.showMessageDialog(null, "Bicicleta no disponible");
			} else {
			    JOptionPane.showMessageDialog(null, "Bicicleta alquilada, por favor seleccione otra");
			}
		    } else {
			JOptionPane.showMessageDialog(null, "El usuario ya tiene una bicicleta en alquiler");
		    }

		} catch (SQLException e1) {
		    System.out.println("Error: bici no alquilada");
		    JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		btnActualizarBD.doClick();
		actualizaBDprimLinea();
	    }
	});
	btnAlq.setFont(new Font("Silom", Font.BOLD, 15));
	btnAlq.setBounds(835, 627, 103, 33);
	frameTienda.getContentPane().add(btnAlq);

	JButton btnCrearUsu = new JButton("Crear");
	btnCrearUsu.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		try {

		    Connection con = ConnectionSingleton.getConnection();
		    PreparedStatement crear_pstmt = con.prepareStatement("INSERT INTO usuario VALUES(?,?,100)");

		    crear_pstmt.setInt(1, Integer.parseInt(txtFCUcod.getText()));
		    crear_pstmt.setString(2, txtFCUnom.getText());
		    crear_pstmt.executeUpdate();
		    crear_pstmt.close();
		    con.close();
		    JOptionPane.showMessageDialog(null, "Usuario creado");

		} catch (SQLException e1) {
		    System.out.println("Error: user no creado");
		    JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		btnActualizarBD.doClick();
		actualizaBDprimLinea();
		txtFCUcod.setText(null);
		txtFCUnom.setText(null);
	    }
	});
	btnCrearUsu.setFont(new Font("Silom", Font.BOLD, 15));
	btnCrearUsu.setBounds(264, 426, 103, 33);
	frameTienda.getContentPane().add(btnCrearUsu);

	JButton btnCrearBi = new JButton("Crear");
	btnCrearBi.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		try {
		    Connection con = ConnectionSingleton.getConnection();
		    PreparedStatement crear_pstmt = con.prepareStatement("INSERT INTO bici VALUES(?,?)");

		    crear_pstmt.setInt(1, Integer.parseInt(txtFCBcod.getText()));
		    crear_pstmt.setString(2, "Libre");
		    crear_pstmt.executeUpdate();
		    crear_pstmt.close();
		    con.close();
		    JOptionPane.showMessageDialog(null, "Bicicleta creada");

		} catch (SQLException e1) {
		    System.out.println("Error: bici no creada");
		    JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		btnActualizarBD.doClick();
		actualizaBDprimLinea();
		txtFCBcod.setText(null);
	    }
	});
	btnCrearBi.setFont(new Font("Silom", Font.BOLD, 15));
	btnCrearBi.setBounds(835, 426, 103, 33);
	frameTienda.getContentPane().add(btnCrearBi);

	JButton btnDev = new JButton("Devolver");
	btnDev.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

	    }
	});
	btnDev.setFont(new Font("Silom", Font.BOLD, 15));
	btnDev.setBounds(265, 627, 103, 33);
	frameTienda.getContentPane().add(btnDev);

    }

}
