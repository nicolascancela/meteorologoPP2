package estados;
public class DeterminarRecursos {

	public static String obtenerIconoClima(Clima c, Tiempo t) {
		String ruta = "";
		if (t instanceof Dia) {
			ruta = c.obtenerIcono((Dia) t);
		}
		if (t instanceof Noche) {
			ruta = c.obtenerIcono((Noche) t);
		}
		return ruta;
	}

	public static String obtenerFondoClima(Clima c, Tiempo t) {
		String ruta = "";
		if (t instanceof Dia) {
			ruta = c.obtenerFondo((Dia) t);
		}
		if (t instanceof Noche) {
			ruta = c.obtenerFondo((Noche) t);
		}
		return ruta;
	}
}