package gateways;

import java.util.Date;
import java.util.Random;

import estados.Clima;
import estados.Despejado;
import estados.Dia;
import estados.EstadoTiempo;
import estados.Lluvia;
import estados.Noche;
import estados.Nublado;
import estados.Tiempo;
import modelo.Cache;

public class MeteorologoStub extends Meteorologo {

	@SuppressWarnings("deprecation")
	@Override
	public EstadoTiempo obtenerTiempo(String ciudad, Cache cache) throws Exception {
		Random r = new Random();
		if(r.nextBoolean()) {
			//Simulamos error!
			throw new Exception();
		}
		Clima c = null;
		int clima = r.nextInt(3);
		switch(clima) {
		case(0):
			c = new Despejado();
			break;
		case(1):
			c = new Nublado();
			break;
		case(2):
			c = new Lluvia();
			break;
		}
		Tiempo t;
		Date hoy = new Date();
		int hora = hoy.getHours();
		int min = hoy.getMinutes();
	     if(hora<19 && min<30){
	    	 t = new Dia();
	     }
	     else {
	    	 t = new Noche();
	     }
	    double temp = (double) r.nextInt(40);
		EstadoTiempo et = new EstadoTiempo(ciudad,c,t,temp);
		return et;
	}
}