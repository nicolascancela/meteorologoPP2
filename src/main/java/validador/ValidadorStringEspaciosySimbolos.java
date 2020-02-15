package validador;

public class ValidadorStringEspaciosySimbolos implements Validador {

	public boolean validar(String a) {
		return a.matches("\\s*[!@#$%&*()_+=|<>?{}\\[\\]~-]*") || a.matches("[!@#$%&*()_+=|<>?{}\\[\\]~-]*\\s*");
	}

}
