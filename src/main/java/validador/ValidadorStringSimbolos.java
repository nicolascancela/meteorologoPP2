package validador;

public class ValidadorStringSimbolos implements Validador {

	public boolean validar(String a) {
		return a.matches("[!@#$%&*()_+=|<>?{}\\[\\]~-]*");
	}

}
