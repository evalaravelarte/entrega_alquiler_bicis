// Eva y Fernando
package alquiler_bicis;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

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
		scrollPane.setBounds(98, 61, 186, 117);
		
		frame.getContentPane().add(scrollPane);
		
	}

}
