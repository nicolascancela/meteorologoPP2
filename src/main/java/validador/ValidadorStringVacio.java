package validador;

public class ValidadorStringVacio implements Validador {

	public boolean validar(String a) {
		return a.isEmpty();
	}

}
