package circuitBreaker;

public class estadoNormal extends estadoCircuitBreaker {

	public estadoNormal(circuitBreaker cb) {
		super(cb);
		System.out.println("EL ESTADO DEL SERVICIO ESTA NORMAL");
		//El servicio esta bien, reseteo contador de fallas
		circuitBreaker.resetearContadorFallas();
	}
	
	@Override
    public void servicioNoRespondio(){
		circuitBreaker.aumentarContadorFallas();
		//Si se produce alguna falla, el servicio queda condicional :(
    	circuitBreaker.cambiarCondicionalyReintentarLlamado();
    }
	
	@Override
	public String toString() {
		return "NORMAL";
	}
}