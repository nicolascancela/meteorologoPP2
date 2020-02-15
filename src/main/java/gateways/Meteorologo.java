package gateways;
import estados.EstadoTiempo;
import modelo.Cache;

public abstract class Meteorologo implements Comparable<Meteorologo> {
	protected String nombreServicio;
	protected Long latencia;

	public abstract EstadoTiempo obtenerTiempo(String ciudad, Cache cache) throws Exception;

	public Long getLatencia() {
		return latencia;
	}

	public void setLatencia(Long lat) {
		latencia = lat;
	}

	public int compareTo(Meteorologo o) {
		return getLatencia().compareTo(o.getLatencia());
	}

	@Override
	public String toString() {
		return nombreServicio.toUpperCase();
	}
}