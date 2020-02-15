package circuitBreaker;
import gateways.Meteorologo;
import modelo.Modelo;

public class circuitBreaker {
	private estadoCircuitBreaker estado;
	public int fallas = 0; //En cero inicialmente
	public int limiteFallas = 3; //Por defecto en 3
	public int intentos = 0;
	private Modelo modelo;

	public circuitBreaker(int lim, Modelo m) {
		System.out.println("CREANDO CIRCUIT BREAKER CON LIMITE DE FALLAS: " + lim);
		limiteFallas = lim;
		modelo = m;
		cambiarEstadoNormal(); //El CB se inicia en estado normal, esto va a ir cambiando dependiendo del funcionamiento de los servicios
	}

	public int getFallas() {
		return fallas;
	}

	public estadoCircuitBreaker cambiarEstadoNormal() {
		estado = new estadoNormal(this);
		return estado;
	}

	public estadoCircuitBreaker cambiarEstadoCondicional() {
		estado = new estadoCondicional(this);
		return estado;
	}

	public estadoCircuitBreaker cambiarEstadoInterrumpido() {
		estado = new estadoInterrumpido(this);
		return estado;
	}

	public void resetearIntentos() {
		intentos = 0;
	}

	public void aumentarContadorFallas() {
		fallas++;
	}

	public void resetearContadorFallas() {
		fallas = 0;
	}

	public boolean seAlcanzoelLimiteFallas() {
		return fallas >= limiteFallas;
	}

	public void solicitarCambioServicio() {
		resetearIntentos();
		modelo.cambiarMeteorologo();
		cambiarEstadoNormal();
	}
	
	public void cambiarServicioyReintentarLlamado() {
		solicitarCambioServicio();
		reintentarLlamadaServicio();
	}
	
	public void cambiarInterrumpidoyReintentarLlamado() {
		cambiarEstadoInterrumpido();
		reintentarLlamadaServicio();
	}
	
	public void cambiarCondicionalyReintentarLlamado() {
		cambiarEstadoCondicional();
		reintentarLlamadaServicio();
	}

	public void reintentarLlamadaServicio() {
		System.out.println("REINTENTANDO LLAMADA");
		intentoLlamadaServicio();
	}

	public void intentoLlamadaServicio() {
		String ciudad = modelo.getCiudad();
		Meteorologo m = modelo.getMeteorologo(); //Obtengo el servicio actual
		System.out.println("LLAMANDO AL SERVICIO " + m.toString().toUpperCase());
		intentos++;
		try {
			modelo.setEstadoTiempo(m.obtenerTiempo(ciudad, modelo.getCache())); //Hago el llamado al servicio
			estado.servicioRespondio(); // Se llamo al servicio y devolvio respuesta!
			System.out.println("LOS DATOS SE OBTUVIERON CORRECTAMENTE");
		} catch (Exception e) {
			estado.servicioNoRespondio(); // Se llamo al servicio y ocurrio un problema!
			System.out.println("HUBO UN PROBLEMA OBTENIENDO LOS DATOS");
		}
		System.out.println("INFORMACION DEL CIRCUIT BREAKER:\nESTADO: " + estado + "\nINTENTOS: " + intentos + "\nFALLAS: " + getFallas());
	}
}