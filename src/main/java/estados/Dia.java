package estados;

public class Dia extends Tiempo {

	@Override
	public String obtenerIconoTiempo(Clima c) {
		return "dia.png";
	}

	@Override
	public String obtenerIconoTiempo(Nublado c) {
		return "dianublado.png";
	}

	@Override
	public String obtenerIconoTiempo(Despejado c) {
		return "diadespejado.png";
	}

	@Override
	public String obtenerIconoTiempo(Lluvia c) {
		return "dialluvia.png";
	}

	@Override
	public String toString() {
		return "Dia";
	}

	@Override
	String obtenerFondoTiempo(Clima c) {
		return "fondodia.png";
	}

	@Override
	String obtenerFondoTiempo(Nublado c) {
		return "fondodianublado.png";
	}

	@Override
	String obtenerFondoTiempo(Despejado c) {
		return "fondodiadespejado.png";
	}

	@Override
	String obtenerFondoTiempo(Lluvia c) {
		return "fondodialluvia.png";
	}
}