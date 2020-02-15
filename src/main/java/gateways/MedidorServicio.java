package gateways;
import java.util.PriorityQueue;

import modelo.Cache;

public class MedidorServicio extends Thread {
	private Meteorologo meteorologo;
	private PriorityQueue<Meteorologo>lista;

	public MedidorServicio(Meteorologo m, PriorityQueue<Meteorologo> orden) {
		meteorologo = m;
		lista = orden;
		start();
	}

	@Override
	public void run() {
		medirServicio(meteorologo);
		stop(); // Termino de medir el servicio y detengo el thread!
	}

	public synchronized void medirServicio(Meteorologo m) {
		System.out.println("INICIO MEDICION: " + m.toString().toUpperCase());
		Long inicio = System.nanoTime();
		try {
			m.obtenerTiempo("Miami", new Cache()); // Llamo al meteorologo
		} catch (Exception e) {
			System.out.println("EL SERVICIO " + meteorologo.toString().toUpperCase() + " SE CAYÓ");
			return; // Si falló, no lo agrego a la lista de meteorologos!
		}
		Long total = System.nanoTime() - inicio;
		meteorologo.setLatencia(total); // Calculo la latencia y se la asigno al servicio
		System.out.println("FINALIZO MEDICION DE: "+ m.toString().toUpperCase());
		lista.add(m); //Agrego el servicio a la lista de servicios
		return;
	}
}