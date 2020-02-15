package validador;

public class ValidadorStringEspacios implements Validador {

	public boolean validar(String a) {
		return a.matches("\\s*");
	}

}
