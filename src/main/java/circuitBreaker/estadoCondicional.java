package circuitBreaker;
public class estadoCondicional extends estadoCircuitBreaker {

	public estadoCondicional(circuitBreaker cb) {
		super(cb);
		System.out.println("EL ESTADO DEL SERVICIO ES CONDICIONAL");
	}

	@Override
	public void servicioNoRespondio() {
		circuitBreaker.aumentarContadorFallas();
		// Estaba en condicional, ocurrio una excepcion
		// Si se alcanzo el limite de fallas, el servicio queda interrumpido!
		if (circuitBreaker.seAlcanzoelLimiteFallas()) {
			circuitBreaker.cambiarInterrumpidoyReintentarLlamado();
		} else {
			// Sino intento llamar de nuevo al servicio
			circuitBreaker.reintentarLlamadaServicio();
		}
	}

	@Override
	public void servicioRespondio() {
		// Se llamo al servicio y devolvio respuesta, el servicio se restablece y por consecuencia se resetea la cantidad de fallas.
		circuitBreaker.cambiarEstadoNormal();
		circuitBreaker.resetearContadorFallas();
	}

	@Override
	public String toString() {
		return "CONDICIONAL";
	}
}