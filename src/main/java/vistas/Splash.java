package vistas;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

import modelo.Modelo;

public class Splash extends Thread {
	private Modelo m;

	public Splash(Modelo modelo) {
		m = modelo;
	}

	@Override
	public void run() {
		try {
			System.out.println("INICIO PANTALLA SPLASH");
			boolean mostrar = true;
			JWindow ventanaInicio = new JWindow();

			URL url = new URL(getClass().getResource("/resources/cargando.gif").toString());
			ImageIcon gif = new ImageIcon(url);
			JLabel lblCargando = new JLabel(gif);
			lblCargando.setBounds(140, 200, 140, 13);
			ventanaInicio.add(lblCargando);

			ventanaInicio.getContentPane().add(new JLabel("", new ImageIcon(new URL(getClass().getResource("/resources/splash.png").toString())), SwingConstants.CENTER));
			ventanaInicio.setBounds(500, 150, 439, 248);
			ventanaInicio.setLocationRelativeTo(null);

			while (mostrar) {
				mostrar = m.getMeteorologo() == null;
				ventanaInicio.setVisible(true);
				sleep(1000);
			}
			System.out.println("CERRANDO THREAD PANTALLA SPLASH");
			ventanaInicio.setVisible(false);
			//stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}