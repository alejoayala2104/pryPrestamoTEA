package pryPrestamo;

import java.text.DecimalFormat;

public class FormatearResultados {

	   static public String formatear(String patron, double valor) {
	      DecimalFormat decimalFormat = new DecimalFormat(patron);
	      String resultado = decimalFormat.format(valor);	  
	      return resultado;
	   }	   
}
