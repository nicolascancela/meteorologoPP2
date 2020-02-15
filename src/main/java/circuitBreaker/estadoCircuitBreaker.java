package circuitBreaker;

public abstract class estadoCircuitBreaker {
	protected circuitBreaker circuitBreaker;
	
	public estadoCircuitBreaker(circuitBreaker cb) {
		circuitBreaker=cb;
	}
    
    public void servicioRespondio() {
    	circuitBreaker.resetearContadorFallas();
    }
    
    public void servicioNoRespondio(){ 
    	circuitBreaker.aumentarContadorFallas();
    }
}