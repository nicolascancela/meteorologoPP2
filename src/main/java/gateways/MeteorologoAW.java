package gateways;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class MeteorologoAW extends Meteorologo {
	private final String USER_AGENT = "Mozilla/5.0";
	private final String URL_Localizacion = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey=";
	private final String URL_ClimaGeneral = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/";
	private final String URL_ClimaActual = "http://dataservice.accuweather.com/currentconditions/v1/";
	private final String API_KEY = "API KEY ACCUWEATHER";

	public MeteorologoAW() {
		nombreServicio = "AccuWeather";
	}

	@Override
	public EstadoTiempo obtenerTiempo(String ciudad, Cache cache) throws Exception {
		// Obtengo la ubicacion
		JSONObject city = getJSONCiudad(ciudad);

		String Citykey = (String) city.get("Key");

		JSONObject climaActual = obtenerClimaActual(Citykey);
		// System.out.println(climaActual);

		String temp = (climaActual.get("Temperature").toString());

		JSONObject temperatura = new JSONObject(temp);

		String metric = temperatura.get("Metric").toString();
		JSONObject metricas = new JSONObject(metric);

		Double celsius = 0.0;

		Object Gradoscelsius = metricas.get("Value");
		if (Gradoscelsius instanceof Double) {
			celsius = ((Double) Gradoscelsius).doubleValue();
		} else {
			celsius = 0.0 + (Integer) Gradoscelsius;
		}

		boolean esDia = (climaActual.get("IsDayTime").toString()).equals("true");
		// System.out.println(esDia);
		Tiempo t;
		if (esDia) {
			t = new Dia();
		} else {
			t = new Noche();
		}

		String estadoNubes = (climaActual.get("WeatherText").toString()).toLowerCase();
		// System.out.println(estadoNubes);
		Clima c = null;
		if (estadoNubes.contains("lluvia") || estadoNubes.contains("llovizna") || estadoNubes.contains("tormenta")) {
			c = new Lluvia();
		}

		if (estadoNubes.contains("nublado") || estadoNubes.contains("nuboso") || estadoNubes.contains("parcialmente soleado")) {
			c = new Nublado();
		}

		if (estadoNubes.contains("soleado") || estadoNubes.contains("sol") || estadoNubes.contains("despejado")) {
			c = new Despejado();
		}

		EstadoTiempo et = new EstadoTiempo(ciudad, c, t, celsius);

		if (et.checkNull()) {
			System.out.println("NO ANDA ACCUWEATHER");
			throw new Exception();
		}
		System.out.println("ACCUWEATHER: " + et);
		//Agrego el estado del tiempo al buffer
		cache.agregarEstadoTiempo(et); 
		return et;
	}
	
	// HTTP GET request
	private JSONObject getJSONCiudad(String ciudad) throws Exception {
		ciudad = ciudad.replace(" ", "%20");
		String url = URL_Localizacion.concat(API_KEY).concat("&q="+ciudad);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		String r = response.toString();
		if(r.charAt(0)=='[' && r.charAt(r.length()-1)==']') {
			r = r.substring(1,r.length()-1);
		}
		//System.out.println("OBTENER LOCALIZACION");
		JSONObject json = new JSONObject(r);
		String key = (String) json.get("Key");
		return json;
	}
	
	// HTTP GET request
	private JSONObject obtenerClima(String key) throws Exception {
		String url = URL_ClimaGeneral.concat(key).concat("?apikey="+API_KEY);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		JSONObject jsonClima = new JSONObject(response.toString());
		return jsonClima;
	}	
	
	private JSONObject obtenerClimaActual(String citykey) throws Exception {
		String url = URL_ClimaActual.concat(citykey).concat("?apikey="+API_KEY).concat("&language=es-ES");
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		String r = response.substring(1,response.length()-1);
		JSONObject jsonClimaActual = new JSONObject(r.toString());
		return jsonClimaActual;
}
	}