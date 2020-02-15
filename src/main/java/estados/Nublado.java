package estados;

public class Nublado extends Clima {

	@Override
	public String obtenerIcono(Tiempo t) {
		return t.obtenerIconoTiempo(this);
	}

	@Override
	public String toString() {
		return "Nublado";
	}

	@Override
	String obtenerFondo(Tiempo t) {
		return t.obtenerFondoTiempo(this);
	}
}