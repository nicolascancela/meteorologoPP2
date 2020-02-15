package modelo;
import java.util.Observable;
import java.util.PriorityQueue;

import circuitBreaker.circuitBreaker;
import estados.EstadoTiempo;
import estados.DeterminarRecursos;
import gateways.FactorySource;
import gateways.Meteorologo;
import vistas.Splash;

public class Modelo extends Observable {
	private EstadoTiempo estadoTiempo;
	private Meteorologo meteorologo; // Servicio actual
	private String ciudad;
	private circuitBreaker circuitBreaker;
	private FactorySource factorySource;
	private PriorityQueue<Meteorologo> ordenServicios;
	private Cache cache;

	public Modelo() {
		mostrarSplash();
		factorySource = new FactorySource();
		factorySource.medirServicios();
		cache = new Cache();
		try {
			Class<?> cls = Class.forName(factorySource.getFactory());
			meteorologo = (Meteorologo) cls.newInstance();
			ciudad = "Buenos Aires"; // Iniciamos la app con Buenos Aires
			circuitBreaker = new circuitBreaker(3, this); // Creo el circuit Breaker!
			ordenServicios = factorySource.getOrden(); // Obtengo la lista inicial de los servicios
			actualizarDatosTiempo();
			new actualizadorTiempo(this, 1, 2).start(); // Hilo de ejecucion: actualiza el tiempo, y mide cada tanto los servicios.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void mostrarSplash() {
		new Splash(this).start();
	}

	public void cambiarCiudad(String c) {
		ciudad = c;
		actualizarDatosTiempo();
	}

	public void actualizarDatosTiempo() {
		obtenerMeterologo();
		circuitBreaker.intentoLlamadaServicio(); // El Circuit Breaker toma el servicio actual del modelo y hace la llamada al servicio.
		setChanged();
		notifyObservers(this); //Notifico a la vista que cambió el tiempo.
	}
	
	public void cambiarMeteorologo() {
		factorySource.medirServicios();
		ordenServicios = factorySource.getOrden(); // Obtengo la lista de los servicios actualizada
		actualizarDatosTiempo(); // Llamo a los meteorologos nuevamente
	}

	public void obtenerMeterologo() {
		meteorologo = ordenServicios.peek(); // Agarro al meteorologo con menor latencia.
	}
		
	public EstadoTiempo getEstadoTiempo() {
		return estadoTiempo;
	}

	public void setEstadoTiempo(EstadoTiempo et) {
		estadoTiempo = et;
	}

	public String getRutaIcono() {
		return DeterminarRecursos.obtenerIconoClima(estadoTiempo.getClima(), estadoTiempo.getTiempo());
	}

	public String getRutaFondo() {
		return DeterminarRecursos.obtenerFondoClima(estadoTiempo.getClima(), estadoTiempo.getTiempo());
	}
	
	public Meteorologo getMeteorologo() {
		return meteorologo;
	}

	public String getCiudad() {
		return ciudad;
	}
	
	public Cache getCache() {
		return cache;
	}
}