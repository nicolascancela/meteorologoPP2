package gateways;
import java.util.PriorityQueue;

public class FactorySource {
	private String factory;
	private PriorityQueue<Meteorologo>listaServicios;
	
	public FactorySource(){
	}

	public void medirServicios() {
		//IDEA: OBTENER EL METEOROLOGO QUE RESPONDA PRIMERO
		listaServicios = new PriorityQueue<Meteorologo>();
		System.out.println("SE INICIA MEDICION DE TIEMPO DE SERVICIOS");
		new MedidorServicio(new MeteorologoOW(),listaServicios); //THREAD 1
		new MedidorServicio(new MeteorologoAW(),listaServicios); //THREAD 2
		new MedidorServicio(new MeteorologoDS(),listaServicios); //THREAD 3
		new MedidorServicio(new MeteorologoND(),listaServicios); //THREAD 4 > Agrego > NO DISPONIBLE
		while(listaServicios.isEmpty()) {
			System.out.print("");
		}
		//Obtengo el primer servicio que responda
		factory = listaServicios.peek().getClass().getName();
	}
	
	public String getFactory() {
		return factory;
	}

	public void setFactory(String f) {
		factory = f;
	}

	public PriorityQueue<Meteorologo> getOrden() {
		return listaServicios;
	}
}