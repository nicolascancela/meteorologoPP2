package controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import modelo.Modelo;
import validador.ValidadorCompuesto;
import validador.ValidadorStringEspacios;
import validador.ValidadorStringEspaciosyNumeros;
import validador.ValidadorStringEspaciosySimbolos;
import validador.ValidadorStringNumeros;
import validador.ValidadorStringSimbolos;
import validador.ValidadorStringSimbolosNumeros;
import validador.ValidadorStringVacio;
import vistas.FrmPrincipal;

public class CtrPrincipal implements Observer, ActionListener {
	private Modelo m;
	private FrmPrincipal v;
	private ValidadorCompuesto vc;

	public CtrPrincipal(Modelo m, FrmPrincipal v) {
		this.v = v;
		this.m = m;
		m.addObserver(this);
		agregarActionsListener();
		generarValidadores();
	}

	private void generarValidadores() {
		vc = new ValidadorCompuesto();
		vc.agregarValidador(new ValidadorStringEspacios());
		vc.agregarValidador(new ValidadorStringEspaciosyNumeros());
		vc.agregarValidador(new ValidadorStringEspaciosySimbolos());
		vc.agregarValidador(new ValidadorStringNumeros());
		vc.agregarValidador(new ValidadorStringSimbolos());
		vc.agregarValidador(new ValidadorStringSimbolosNumeros());
		vc.agregarValidador(new ValidadorStringVacio());		
	}

	private void agregarActionsListener() {
		v.getBtnSalir().addActionListener(this);
		v.getTxtCiudad().addActionListener(this);
	}

	public void actionPerformed(ActionEvent arg0) {
		ActionEvent e = arg0;
		if (e.getSource() == v.getBtnSalir()) {
			mostrarConfirmacionparaSalir();
		}

		if (e.getSource() == v.getTxtCiudad()) {
			v.mostrarBarraCarga(true);
			String ciudad = v.getTxtCiudad().getText();
			if (vc.validar(ciudad)) {
				JOptionPane.showMessageDialog(v, "Escriba una ciudad válida", "Error", 0);
				v.mostrarBarraCarga(false);
				return;
			}
			m.cambiarCiudad(ciudad);
			v.mostrarBarraCarga(false);
		}
	}

	private void mostrarConfirmacionparaSalir() {
		int seleccion = JOptionPane.showConfirmDialog(v, "¿Salir de la aplicación?", "Atención", 0);
		if (seleccion == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	public void mostrarExcepcion(Exception e) {
		JOptionPane.showMessageDialog(v, "Se produjo el siguiente error: " + e.getMessage());
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}
}