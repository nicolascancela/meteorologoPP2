package validador;

public class ValidadorStringSimbolosNumeros implements Validador {

	public boolean validar(String a) {
		return a.matches("[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-]*[0-9]*") || a.matches("[0-9]*[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-]*");
	}

}
