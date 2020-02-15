package validador;

public class ValidadorStringEspaciosyNumeros implements Validador {

	public boolean validar(String a) {
		return a.matches("\\s*[0-9]*") || a.matches("[0-9]*\\s*");
	}

}
