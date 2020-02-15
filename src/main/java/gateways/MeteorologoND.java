package gateways;
import estados.Clima;
import estados.Despejado;
import estados.Dia;
import estados.EstadoTiempo;
import estados.Tiempo;
import modelo.Cache;

public class MeteorologoND extends Meteorologo {
	//METEOROLOGO NULL > INFORMACION NO DISPONIBLE
	public MeteorologoND() {
		nombreServicio="No Disponible";
	}
	
	@Override
	public EstadoTiempo obtenerTiempo(String ciudad, Cache cache) throws Exception {
		EstadoTiempo aux = cache.getEstadoTiempo(ciudad);
		//Sino tengo la ciudad en el buffer, muestro "No disponible", sino devuelvo la info del buffer!
		if(aux==null) {
			String city = "Informacion no disponible";
			Clima c =  new Despejado();
			Tiempo t = new Dia();
		    double temp = 0;
			EstadoTiempo et = new EstadoTiempo(city,c,t,temp);
			setLatencia(Long.MAX_VALUE);
			
			long tiempoActual = System.currentTimeMillis(); //Tiempo actual
			long tiempoTardar = tiempoActual + 3500; //Le sumo 3,5 segundos --> Tiempo en milisegundos
			
			while(tiempoTardar>tiempoActual)
			{
				tiempoActual = System.currentTimeMillis(); //Tiempo actual
			}
			return et;
	}
		else {
			return aux;
		}}
	
	@Override
	public void setLatencia(Long lat) {
		latencia = Long.MAX_VALUE; //Cuando seteo latencia le doy el valor maximo.
	}
}