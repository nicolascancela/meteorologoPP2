package estados;

public class EstadoTiempo {
	private String ciudad;
	private Clima clima;
	private Tiempo tiempo;
	private double temperatura;
	
	public EstadoTiempo(String ciudad, Clima c, Tiempo t, double temperatura) {
		this.ciudad=ciudad;
		this.clima=c;
		this.tiempo=t;
		this.temperatura=temperatura;
	}
	
	public String getCiudad() {
		return ciudad;
	}

	public Clima getClima() {
		return clima;
	}

	public Tiempo getTiempo() {
		return tiempo;
	}

	public double getTemperatura() {
		return temperatura;
	}
	
	public boolean checkNull() {
		return clima==null || tiempo==null;
	}

	@Override
	public String toString() {
		return ciudad+" "+clima+" "+tiempo+" "+temperatura;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ciudad.toLowerCase() == null) ? 0 : ciudad.toLowerCase().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstadoTiempo other = (EstadoTiempo) obj;
		if (ciudad.toLowerCase() == null) {
			if (other.ciudad.toLowerCase() != null)
				return false;
		} else if (!ciudad.toLowerCase().equals(other.ciudad.toLowerCase()))
			return false;
		return true;
	}
}