package modelo;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class actualizadorTiempo extends Thread {
	private int minutosActualizacion = 3;
	private int minutosMedicion = 3;
	private Modelo m;
	private Calendar proximaActualizacion;
	private Calendar proximaMedicion;

	public actualizadorTiempo(Modelo modelo, int minAct, int minMed) {
		m = modelo;
		minutosActualizacion = minAct;
		minutosMedicion = minMed;
		proximaActualizacion = new GregorianCalendar();
		proximaActualizacion.add((GregorianCalendar.MINUTE), minutosActualizacion);
		proximaMedicion = new GregorianCalendar();
		proximaMedicion.add((GregorianCalendar.MINUTE), minutosMedicion);
	}

	public actualizadorTiempo(Modelo modelo) {
		//Por defecto, sino se mandan parametros el timer se actualiza cada 3 minutos!
		m = modelo;
		proximaActualizacion = new GregorianCalendar();
		proximaActualizacion.add((GregorianCalendar.MINUTE), minutosActualizacion);
		proximaMedicion = new GregorianCalendar();
		proximaMedicion.add((GregorianCalendar.MINUTE), minutosMedicion);
	}
	
	@Override
	public void run() {
		try {
			System.out.println("INICIANDO HILO DE ACTUALIZACION EN " + minutosActualizacion + " MINUTOS\nINICIANDO HILO DE MEDICIÓN EN " + minutosMedicion + " MINUTOS");
			while (true) {
				actualizarDatosTiempo();
				actualizarMedicionMeteorologo();
				sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void actualizarDatosTiempo() {
		Calendar actual = new GregorianCalendar();
		actual.setTimeZone(actual.getTimeZone()); // Tiempo actual
		if (proximaActualizacion.compareTo(actual) == -1) {
			System.out.println("ACTUALIZANDO DATOS TIEMPO");
			m.actualizarDatosTiempo();
			proximaActualizacion.add((GregorianCalendar.MINUTE), minutosActualizacion);
		}
	}

	private void actualizarMedicionMeteorologo() {
		Calendar actual = new GregorianCalendar();
		actual.setTimeZone(actual.getTimeZone()); // Tiempo actual
		if (proximaMedicion.compareTo(actual) == -1) {
			System.out.println("MIDIENDO METEOROLOGOS");
			m.cambiarMeteorologo();
			proximaMedicion.add((GregorianCalendar.MINUTE), minutosMedicion);
		}
	}
}