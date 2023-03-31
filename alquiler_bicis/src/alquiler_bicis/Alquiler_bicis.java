package alquiler_bicis;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JButton;

class ConnectionSingleton {
	 private static Connection con;
	 public static Connection getConnection() throws SQLException {
	String url = "jdbc:mysql://127.0.0.1:3307/alquiler_bicis";
	String user = "alumno";
	String password = "alumno";
	if (con==null || con.isClosed()) {
	con=DriverManager.getConnection(url, user, password);
	}
	return con;
	}
	}


public class Alquiler_bicis {

	private JFrame frame;
	private JTable table;
	private JTextField textFieldCodUsuario;
	private JTextField textFieldNomUsuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Alquiler_bicis window = new Alquiler_bicis();
					window.frame.setVisible(true);
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
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1298, 722);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		DefaultTableModel model = new DefaultTableModel();
			model.addColumn("ID");
			model.addColumn("NOMBRE");
			model.addColumn("BICI");
		
		try {
			Connection con=ConnectionSingleton.getConnection();
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM usuario");
			while (rs.next()) {
			 Object[] row = new Object[3];
			 row[0] = rs.getInt("cod_usuario");
			 row[1] = rs.getString("nombre");
			 row[2] = rs.getInt("cod_bici");
			 model.addRow(row);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		table = new JTable(model);
		frame.getContentPane().add(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(122, 61, 322, 161);
		
		frame.getContentPane().add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("Crear usuario:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(122, 297, 108, 22);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("USUARIOS");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(229, 22, 108, 29);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Código:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(122, 350, 49, 13);
		frame.getContentPane().add(lblNewLabel_2);
		
		textFieldCodUsuario = new JTextField();
		textFieldCodUsuario.setBounds(181, 349, 47, 19);
		frame.getContentPane().add(textFieldCodUsuario);
		textFieldCodUsuario.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Nombre:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(297, 352, 63, 13);
		frame.getContentPane().add(lblNewLabel_3);
		
		textFieldNomUsuario = new JTextField();
		textFieldNomUsuario.setBounds(362, 349, 96, 19);
		frame.getContentPane().add(textFieldNomUsuario);
		textFieldNomUsuario.setColumns(10);
		
		JButton btnNewButton = new JButton("CREAR");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement crear_pstmt = con.prepareStatement("INSERT INTO usuario VALUES(?,?,0)");
					
					crear_pstmt.setInt(1, Integer.parseInt(textFieldCodUsuario.getText()));
					crear_pstmt.setString(2, textFieldNomUsuario.getText());
					crear_pstmt.executeUpdate();
					crear_pstmt.close();
					con.close();
					JOptionPane.showMessageDialog(null, "Usuario creado");
					
				}catch(SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(240, 410, 96, 29);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_4 = new JLabel("Devolver bici:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(122, 505, 96, 13);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Código de usuario:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_5.setBounds(122, 557, 125, 13);
		frame.getContentPane().add(lblNewLabel_5);
		
		JComboBox comboBoxCodUsuario = new JComboBox();
		comboBoxCodUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				
				comboBoxCodUsuario.removeAllItems();
				
				try {
					Connection con = ConnectionSingleton.getConnection();
					Statement devolver_stmt = con.createStatement();
					ResultSet devolver_rs = devolver_stmt.executeQuery("SELECT cod_usuario FROM usuario WHERE cod_bici IN(SELECT cod_bici FROM bici WHERE libre=1)");
					
					while(devolver_rs.next()) {
						int cod_usuario = devolver_rs.getInt("cod_usuario");
						comboBoxCodUsuario.addItem(cod_usuario);
					}
					devolver_rs.close();
					devolver_stmt.close();
					con.close();
					
					
				}catch(SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		comboBoxCodUsuario.setBounds(257, 555, 49, 21);
		frame.getContentPane().add(comboBoxCodUsuario);
		
		JLabel lblNewLabel_6 = new JLabel("Código de bici:");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_6.setBounds(362, 559, 96, 13);
		frame.getContentPane().add(lblNewLabel_6);
		
		JComboBox comboBoxCodBici = new JComboBox();
		comboBoxCodBici.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				
				comboBoxCodBici.removeAllItems();
				
				try {
					Connection con = ConnectionSingleton.getConnection();
					Statement devolver_stmt = con.createStatement();
					ResultSet devolver_rs = devolver_stmt.executeQuery("SELECT cod_bici FROM bici WHERE libre=1");
					
					while(devolver_rs.next()) {
						int cod_bici = devolver_rs.getInt("cod_bici");
						comboBoxCodBici.addItem(cod_bici);
					}
					devolver_rs.close();
					devolver_stmt.close();
					con.close();
					
					
				}catch(SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		comboBoxCodBici.setBounds(468, 555, 49, 21);
		frame.getContentPane().add(comboBoxCodBici);
		
		JButton btnNewButton_1 = new JButton("DEVOLVER");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement devolver_pstmt_bici = con.prepareStatement("UPDATE bici SET libre=0 WHERE cod_bici=?");
					PreparedStatement devolver_pstmt_usuario = con.prepareStatement("UPDATE usuario SET cod_bici=0 WHERE cod_bici=?");
					
					devolver_pstmt_bici.setInt(1,Integer.parseInt(comboBoxCodBici.getSelectedItem().toString()));
					devolver_pstmt_usuario.setInt(1,Integer.parseInt(comboBoxCodBici.getSelectedItem().toString()));
					devolver_pstmt_bici.executeUpdate();
					devolver_pstmt_usuario.executeUpdate();
					devolver_pstmt_bici.close();
					devolver_pstmt_usuario.close();
					con.close();
					
					JOptionPane.showMessageDialog(null, "Bici correctamente devuelta");
					
				}catch(SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.setBounds(240, 623, 120, 29);
		frame.getContentPane().add(btnNewButton_1);
		
	}

}
