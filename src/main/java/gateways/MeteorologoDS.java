package gateways;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.json.JSONObject;

import estados.Clima;
import estados.Despejado;
import estados.Dia;
import estados.EstadoTiempo;
import estados.Lluvia;
import estados.Noche;
import estados.Nublado;
import estados.Tiempo;
import modelo.Cache;
import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;
import tk.plogitech.darksky.forecast.APIKey;
import tk.plogitech.darksky.forecast.ForecastRequest;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder;
import tk.plogitech.darksky.forecast.GeoCoordinates;
import tk.plogitech.darksky.forecast.model.Forecast;
import tk.plogitech.darksky.forecast.model.Latitude;
import tk.plogitech.darksky.forecast.model.Longitude;

public class MeteorologoDS extends Meteorologo {
	private final String USER_AGENT = "Mozilla/5.0";
	private final String URL_Geolocalizacion = "http://dataservice.accuweather.com/locations/v1/cities/geoposition/search?apikey=";
	private final String API_KEY_GEO = "API KEY GEO";
	private final String API_KEY = "API KEY DARKSKY";

	public MeteorologoDS() {
		nombreServicio = "DarkSky";
	}

	@Override
	public EstadoTiempo obtenerTiempo(String ciudad, Cache cache) throws Exception {
		EstadoTiempo et = null;
		JSONObject lonLat = obtenerGeolocalizacion(ciudad);
		// Parseamos los JSON!
		Longitude longitud = new Longitude((Double) lonLat.get("lng"));
		Latitude latitud = new Latitude((Double) lonLat.get("lat"));

		ForecastRequest request = new ForecastRequestBuilder().key(new APIKey(API_KEY_GEO)).location(new GeoCoordinates(longitud, latitud)).build();

		DarkSkyJacksonClient client = new DarkSkyJacksonClient();
		Forecast forecast = client.forecast(request);

		double celsius = forecast.getCurrently().getTemperature();
		double temperatura = new BigDecimal(celsius).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

		// Ahora me fijo si esta despejado, hay nubes o llueve
		Clima c = null;
		Double nubes = forecast.getCurrently().getCloudCover();
		Double lluvia = forecast.getCurrently().getPrecipProbability();
		if (lluvia != null) {
			if (lluvia > 0.5) {
				c = new Lluvia();
			} else {
				if (nubes > 0.5) {
					c = new Nublado();
				} else {
					c = new Despejado();
				}
			}
		} else {
			if (nubes > 0.5) {
				c = new Nublado();
			} else {
				c = new Despejado();
			}
		}

		// Veo el tiempo
		Tiempo t = null;
		Instant tiempoActual = forecast.getCurrently().getTime();
		int hora = LocalDateTime.ofInstant(tiempoActual.now(), ZoneOffset.systemDefault()).getHour();

		if (hora < 19) {
			t = new Dia();
		} else {
			t = new Noche();
		}

		et = new EstadoTiempo(ciudad, c, t, temperatura);

		if (et.checkNull()) {
			System.out.println("NO ANDA DARKSKY");
			throw new Exception();
		}
		System.out.println("DARKSKY: " + et);
		//Agrego el estado del tiempo al buffer
		cache.agregarEstadoTiempo(et); 
		return et;
	}

	private JSONObject obtenerGeolocalizacion(String ciudad) throws Exception {
		ciudad = ciudad.replace(" ", "+");

		String url = "http://open.mapquestapi.com/geocoding/v1/address?key=";
		// http://open.mapquestapi.com/geocoding/v1/address?key=KEY&location=Buenos+Aires
		String key = "API KEY GEOLOCALIZACION";

		url = url.concat(key).concat("&location=" + ciudad);

		URL obj = new URL(url.toString());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		// System.out.println(url);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// obtengo todo los datos de la respuesta
		JSONObject result = new JSONObject(response.toString());
		String x = (result.get("results").toString());

		x = x.substring(1, x.length() - 1);

		// Obtengo la ubicacion
		JSONObject location = new JSONObject(x);

		String y = location.get("locations").toString();

		y = y.substring(1, y.length() - 1);

		// Obtengo la latitud y longitud
		JSONObject displayLatLng = new JSONObject(y);
		String z = displayLatLng.get("displayLatLng").toString();
		JSONObject longLat = new JSONObject(z);

		return longLat;
	}
}