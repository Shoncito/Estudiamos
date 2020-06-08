package funciones;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Generador {
	public static String generarId() {
		Calendar calendar = GregorianCalendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);
        int segundo = calendar.get(Calendar.SECOND);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = 1+calendar.get(Calendar.MONTH);
        int año = calendar.get(Calendar.YEAR);
		String id=""+año+mes+dia+hora+minuto+segundo;
		return id;
	}
}
