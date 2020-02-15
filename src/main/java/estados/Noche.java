package estados;

public class Noche extends Tiempo {

	@Override
	public String obtenerIconoTiempo(Clima c) {
		return "noche.png";
	}

	@Override
	public String obtenerIconoTiempo(Nublado c) {
		return "nochenublado.png";
	}

	@Override
	public String obtenerIconoTiempo(Despejado c) {
		return "nochedespejado.png";
	}

	@Override
	public String obtenerIconoTiempo(Lluvia c) {
		return "nochelluvia.png";
	}

	@Override
	public String toString() {
		return "Noche";
	}

	@Override
	String obtenerFondoTiempo(Clima c) {
		return "fondonoche";
	}

	@Override
	String obtenerFondoTiempo(Nublado c) {
		return "fondonochenublado.png";
	}

	@Override
	String obtenerFondoTiempo(Despejado c) {
		return "fondonochedespejado.png";
	}

	@Override
	String obtenerFondoTiempo(Lluvia c) {
		return "fondonochelluvia.png";
	}
}