package estados;

public class Lluvia extends Clima {

	@Override
	public String obtenerIcono(Tiempo t) {
		return t.obtenerIconoTiempo(this);
	}

	@Override
	public String toString() {
		return "Lluvia";
	}

	@Override
	String obtenerFondo(Tiempo t) {
		return t.obtenerFondoTiempo(this);
	}
}