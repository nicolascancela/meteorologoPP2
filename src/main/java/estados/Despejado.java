package estados;

public class Despejado extends Clima {

	@Override
	public String obtenerIcono(Tiempo t) {
		return t.obtenerIconoTiempo(this);
	}

	@Override
	public String toString() {
		return "Despejado";
	}

	@Override
	String obtenerFondo(Tiempo t) {
		return t.obtenerFondoTiempo(this);
	}
}