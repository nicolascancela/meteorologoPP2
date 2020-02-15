package validador;

import java.util.LinkedList;
import java.util.List;

public class ValidadorCompuesto implements Validador {
	public List<Validador>validadores;
	
	public ValidadorCompuesto() {
		validadores = new LinkedList<Validador>();
	}
	
	public void agregarValidador(Validador v) {
		validadores.add(v);
	}
	
	public void eliminarValidador(Validador v) {
		validadores.remove(v);
	}
	
	
	public boolean validar(String a) {
		boolean valido = false;
		for(Validador v: validadores) {
			valido = valido || v.validar(a);
		}
		return valido;
	}

}
