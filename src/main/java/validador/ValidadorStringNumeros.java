package validador;

public class ValidadorStringNumeros implements Validador {

	public boolean validar(String a) {
		return a.matches("[0-9]+");
	}

}
