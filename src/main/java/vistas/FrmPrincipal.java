package vistas;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import modelo.Modelo;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class FrmPrincipal extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnSalir;
	private JTextField txtCiudad;
	private String buscarCiudad;
	private JLabel lblNombreApp;
	private JLabel lblIconoClima;
	private JLabel lblCiudad;
	private JLabel lblFondo;
	private JLabel lblTemperatura;
	private JLabel lblCargando;
	private Properties idioma;
	
	public FrmPrincipal(Modelo m) {
		//Cargo el local para ver que property uso para la interfaz
		String local = System.getProperty("user.language");
		String property = local+".properties";
		
		idioma = new Properties();
		try {
			InputStream input = this.getClass().getResourceAsStream("/resources/"+property);
			idioma.load(input);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "No se encontro el idioma.");
			e.printStackTrace();
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmPrincipal.class.getResource("/resources/icono app.png")));
		setResizable(false);
		m.addObserver(this);
		inicializarVista();
		update(m,m);
	}
	
	private void inicializarVista() {
		try {
			  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch(Exception e) {
			  JOptionPane.showMessageDialog(this, "Se produjo el siguiente error: " + e);
			}
		
		setTitle("Meteor\u00F3logo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(new Color(51, 204, 255));
		contentPane.setLayout(null);
		
		lblNombreApp = new JLabel(this.idioma.getProperty("appName"));
		lblNombreApp.setForeground(Color.WHITE);
		lblNombreApp.setFont(new Font("Segoe UI Light", Font.PLAIN, 30));
		lblNombreApp.setBounds(10, 0, 298, 53);
		contentPane.add(lblNombreApp);
		
		btnSalir = new JButton(this.idioma.getProperty("btnSalir"));
		btnSalir.setBounds(495, 437, 89, 23);
		contentPane.add(btnSalir);
				
		txtCiudad = new JTextField();
		txtCiudad.setBounds(416, 11, 156, 20);
		contentPane.add(txtCiudad);
		txtCiudad.setColumns(10);
		
		JLabel lblFiltrar = new JLabel(this.idioma.getProperty("lblBuscar"));
		lblFiltrar.setBounds(343, 14, 78, 14);
		contentPane.add(lblFiltrar);
		
		lblIconoClima = new JLabel("");
		lblIconoClima.setHorizontalAlignment(SwingConstants.CENTER);
		lblIconoClima.setBounds(204, 84, 200, 200);
		contentPane.add(lblIconoClima);
		
		lblCiudad = new JLabel("Ciudad");
		lblCiudad.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));
		lblCiudad.setHorizontalAlignment(SwingConstants.CENTER);
		lblCiudad.setBounds(103, 291, 406, 53);
		contentPane.add(lblCiudad);
		
		lblTemperatura = new JLabel("");
		lblTemperatura.setHorizontalAlignment(SwingConstants.CENTER);
		lblTemperatura.setFont(new Font("Segoe UI Light", Font.PLAIN, 28));
		lblTemperatura.setBounds(103, 344, 406, 38);
		contentPane.add(lblTemperatura);
		
		lblCargando = new JLabel("");
		lblCargando.setBounds(215, 393, 140, 13);
		contentPane.add(lblCargando);
		
		lblFondo = new JLabel("");
		lblFondo.setBounds(0, 0, 594, 471);
		contentPane.add(lblFondo);
		setLocationRelativeTo(null);	
	}
		
	public JButton getBtnSalir() {
		return btnSalir;
	}

	public JTextField getTxtCiudad() {
		return txtCiudad;
	}

	public String getFiltro() {
		return buscarCiudad;
	}
	
	public void mostrarBarraCarga(boolean mostrar) {
		try {
			URL url = new URL(getClass().getResource("/resources/cargando.gif").toString());
			ImageIcon gif = new ImageIcon(url);
			lblCargando.setIcon(gif);
			lblCargando.setVisible(mostrar);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Se produjo el siguiente error: " + e);
		}
	}
	
	public void cargarInterfaz(Object m) {
		Modelo modelo = ((Modelo)m);
		String aux = modelo.getEstadoTiempo().getCiudad();
		String[] vector = aux.split(" ");
		String ciudad ="";
		for(int i=0; i<vector.length;i++) {
			ciudad = ciudad+ " "+ vector[i].substring(0, 1).toUpperCase() + vector[i].substring(1); //Muestro en mayuscula el nombre de la ciudad.
		}
		ciudad = ciudad.substring(1);
		if(ciudad.length()>=23) {
			lblCiudad.setFont(new Font("Segoe UI Light", Font.PLAIN, 25));	}
		if(ciudad.length()<23) {
			lblCiudad.setFont(new Font("Segoe UI Light", Font.PLAIN, 35));	}
		lblCiudad.setText(ciudad);
		lblTemperatura.setText(modelo.getEstadoTiempo().getTemperatura()+"º");
		try {
			Image icono = ImageIO.read(getClass().getResource("/resources/"+((Modelo)m).getRutaIcono()));
			lblIconoClima.setIcon(new ImageIcon(icono));
			Image fondo = ImageIO.read(getClass().getResource("/resources/"+((Modelo)m).getRutaFondo()));
			lblFondo.setIcon(new ImageIcon(fondo));
		}catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Se produjo el siguiente error: " + e);
		}
	}

	public void update(Observable arg0, Object arg1) {
		cargarInterfaz(arg1);
	}
}