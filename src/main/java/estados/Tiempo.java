package estados;

public abstract class Tiempo {
	abstract String obtenerIconoTiempo(Clima c);
	abstract String obtenerIconoTiempo(Nublado c);
	abstract String obtenerIconoTiempo(Despejado c);
	abstract String obtenerIconoTiempo(Lluvia c);
	
	abstract String obtenerFondoTiempo(Clima c);
	abstract String obtenerFondoTiempo(Nublado c);
	abstract String obtenerFondoTiempo(Despejado c);
	abstract String obtenerFondoTiempo(Lluvia c);
}