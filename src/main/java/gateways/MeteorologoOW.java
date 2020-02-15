package gateways;

import java.math.BigDecimal;
import java.util.Date;
import estados.Clima;
import estados.Despejado;
import estados.Dia;
import estados.EstadoTiempo;
import estados.Lluvia;
import estados.Noche;
import estados.Nublado;
import estados.Tiempo;
import modelo.Cache;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;

public class MeteorologoOW extends Meteorologo {
	private final String API_KEY = "API KEY OPENWEATHER";

	public MeteorologoOW() {
		nombreServicio = "OpenWeather";
	}

	@Override
	public EstadoTiempo obtenerTiempo(String ciudad, Cache cache) throws Exception {
		EstadoTiempo et;
		OWM owm = new OWM(API_KEY); // KEY PARA LA API!
		CurrentWeather cwd;
		// Seteo la API con el nombre de la ciudad
		cwd = owm.currentWeatherByCityName(ciudad);

		// Calculo la temperatura
		double celsius = cwd.getMainData().getTemp() - 273.0;

		// Obtengo el tiempo de la ciudad y lo seteo.
		Tiempo t;
		Date hoy = cwd.getDateTime();
		Date anochecer = cwd.getSystemData().getSunsetDateTime();

		int hora = hoy.getHours();
		int horaAnochecer = anochecer.getHours();

		if (hora < horaAnochecer) {
			t = new Dia();
		} else {
			t = new Noche();
		}

		// Ahora me fijo si esta despejado, hay nubes o llueve
		Clima c = null;
		if (cwd.getRainData() != null) {
			if (cwd.getRainData().getPrecipVol3h() > 0.0) {
				c = new Lluvia();
			}

		} else {
			if (cwd.getCloudData().getCloud() > 50.0) {
				c = new Nublado();
			} else {
				c = new Despejado();
			}
		}
		double temperatura = new BigDecimal(celsius).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		ciudad = cwd.getCityName();
		et = new EstadoTiempo(ciudad, c, t, temperatura);

		if (et.checkNull()) {
			System.out.println("NO ANDA OPENWEATHER");
			throw new Exception();
		}
		System.out.println("OPEN WEATHER: " + et);
		
		//Agrego el estado del tiempo al buffer
		cache.agregarEstadoTiempo(et); 
		return et;
	}
}