package modelo;

import java.util.HashSet;
import java.util.Set;
import estados.EstadoTiempo;

public class Cache {
	private Set<EstadoTiempo> cache;

	public Cache() {
		cache = new HashSet<EstadoTiempo>();
	}

	public void agregarEstadoTiempo(EstadoTiempo et) {
		// Agregamos una ciudad con lower case!
		EstadoTiempo aux = new EstadoTiempo(et.getCiudad().toLowerCase(), et.getClima(), et.getTiempo(), et.getTemperatura());
		cache.remove(et); // Sacamos el viejo estado de tiempo
		cache.add(aux); // Agregamos el nuevo estado de tiempo
	}

	public int getCantidadCache() {
		return cache.size();
	}

	public EstadoTiempo getEstadoTiempo(String ciudad) {
		EstadoTiempo ret = null;
		for (EstadoTiempo et : cache) {
			if (et.equals(new EstadoTiempo(ciudad.toLowerCase(), null, null, 0))) {
				ret = et;
			}
		}
		return ret;
	}

	public void imprimirCache() {
		System.out.println("===CIUDADES EN CACHE===");
		for (EstadoTiempo et : cache) {
			System.out.println(et);
		}
	}
}