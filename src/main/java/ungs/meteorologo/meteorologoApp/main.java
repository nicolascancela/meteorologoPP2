package ungs.meteorologo.meteorologoApp;

import controlador.CtrPrincipal;
import modelo.Modelo;
import vistas.FrmPrincipal;

public class main {
	public static void main(String[] args) {
		// Creo el modelo
		Modelo m = new Modelo();
		// Creo la vista
		FrmPrincipal frmP = new FrmPrincipal(m);
		CtrPrincipal ctrP = new CtrPrincipal(m, frmP);
		// Agrego los observadores al modelo
		m.addObserver(frmP);
		// Inicio ventanas
		frmP.setVisible(true);
	}
}