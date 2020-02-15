package circuitBreaker;

public class estadoInterrumpido extends estadoCircuitBreaker {
	
	public estadoInterrumpido(circuitBreaker cb) {
		super(cb);
		System.out.println("EL ESTADO DEL SERVICIO ES INTERRUMPIDO");
	}
		   
	@Override
    public void servicioRespondio() {
    	//Se llamo al servicio y devolvió respuesta, el servicio se pone como condicional
		circuitBreaker.resetearContadorFallas();
		circuitBreaker.cambiarEstadoCondicional();
    }
	
	@Override
    public void servicioNoRespondio(){ 
		//Si el servicio esta interrumpido, directamente solicitamos que se cambie de servicio!
		circuitBreaker.cambiarServicioyReintentarLlamado();
    }

	@Override
	public String toString() {
		return "INTERRUMPIDO";
	}
}