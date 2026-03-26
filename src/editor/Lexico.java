package editor;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexico {

	private static String Abecedario[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
			"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
	private static String Numeros[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
	private static String Especiales[] = { "*", "/", "+", "-", "=", "$", "%", "^", "(", ")", "{", "}", "[", "]", "?",
			",", ".", ":", ";", "!", "&", "<", ">" };

	private static ArrayList<String> AbecedarioList = new ArrayList<String>();
	private static ArrayList<String> NumerosList = new ArrayList<String>();
	private static ArrayList<String> EspecialesList = new ArrayList<String>();
	private static ArrayList<String> CaracteresTexto = new ArrayList<String>();

	private static ArrayList<String> letrasAlmacenadas = new ArrayList<String>();
	private static ArrayList<String> numerosAlmacenados = new ArrayList<String>();
	private static ArrayList<String> especialesAlmacenados = new ArrayList<String>();
	private static ArrayList<String> invalidosAlmacenados = new ArrayList<String>();

	/**
	 * Retorna el texto sin espacios en blanco, tabulaciones ni saltos de linea
	 * 
	 * @param pTexto
	 * @return String Limpio
	 */
	public static String LimpiarTexto(String pTexto) {
		stringToArrayList();
		String textoConMayusculas = pTexto.replaceAll("[\s]", "").replaceAll("[\t]", "").replaceAll("[\n]", "");
		String texto = textoConMayusculas.toLowerCase();
		String arr[] = texto.split("");

		CaracteresTexto.clear();
		for (String caracter : arr) {
			CaracteresTexto.add(caracter);
		}

		return textoConMayusculas;
	}

	// ----------------------- CALCULO DE ESPACIOS EN BLANCO -----------------------
	public static String CantidadEspacios(String pTexto) {
		int cantidad = 0;
		String arr[] = pTexto.split("");
		for (String caracter : arr) {
			if (caracter.equals(" ")) {
				cantidad++;
			}
		}

		return Integer.toString(cantidad);
	}

	// --------------------------- CALCULOS PARA LETRAS ---------------------------
	public static String CantidadLetras() {
		int cantidad = 0;

		for (String caracter : CaracteresTexto) {
			if (AbecedarioList.contains(caracter)) {
				cantidad++;
			}
		}
		return Integer.toString(cantidad);
	}

	public static ArrayList<String> LetrasAlmacenadas() {
		letrasAlmacenadas.clear();
		for (String caracter : CaracteresTexto) {
			if (AbecedarioList.contains(caracter)) {
				//if (!letrasAlmacenadas.contains(caracter)) { }
					letrasAlmacenadas.add(caracter);
				
			}
		}
		return letrasAlmacenadas;
	}

	// ----------------------- CALCULOS PARA NUMEROS ---------------------------
	public static String CantidadNumeros() {
		int cantidad = 0;

		for (String caracter : CaracteresTexto) {
			if (NumerosList.contains(caracter)) {
				cantidad++;
			}
		}
		return Integer.toString(cantidad);
	}

	public static ArrayList<String> NumerosAlmacenados() {
		numerosAlmacenados.clear();
		for (String caracter : CaracteresTexto) {
			if (NumerosList.contains(caracter)) {
				//if (!numerosAlmacenados.contains(caracter)) { }
					numerosAlmacenados.add(caracter);
				
			}
		}
		return numerosAlmacenados;
	}

	// -------------- CALCULOS PARA CARACTERES ESPECIALES ---------------
	public static String CantidadEspeciales() {
		int cantidad = 0;

		for (String caracter : CaracteresTexto) {
			if (EspecialesList.contains(caracter)) {
				cantidad++;
			}
		}
		return Integer.toString(cantidad);
	}

	public static ArrayList<String> EspecialesAlmacenados() {
		especialesAlmacenados.clear();
		for (String caracter : CaracteresTexto) {
			if (EspecialesList.contains(caracter)) {
				//if (!especialesAlmacenados.contains(caracter)) { }
					especialesAlmacenados.add(caracter);
				
			}
		}
		return especialesAlmacenados;
	}

	// ------------ CALCULOS PARA CARACTERES INVALIDOS ------------------

	public static String CantidadInvalidos(String pTexto) {
		int cantidad = 0;

		String textoConMayusculas = pTexto.replaceAll("[\s]", "").replaceAll("[\t]", "").replaceAll("[\n]", "");
		String texto = textoConMayusculas.toLowerCase();
		String arr[] = texto.split("");
		ArrayList<String> TextoInvalido = new ArrayList<>();
		for (String caracter : arr) {
			TextoInvalido.add(caracter);
		}

		for (String caracter : TextoInvalido) {
			if (!EspecialesList.contains(caracter) && !AbecedarioList.contains(caracter)
					&& !NumerosList.contains(caracter)) {
				cantidad++;
			}
		}
		return Integer.toString(cantidad);
	}

	public static ArrayList<String> InvalidosAlmacenados(String pTexto) {
		String textoConMayusculas = pTexto.replaceAll("[\s]", "").replaceAll("[\t]", "").replaceAll("[\n]", "");
		String texto = textoConMayusculas.toLowerCase();
		String arr[] = texto.split("");
		ArrayList<String> TextoInvalido = new ArrayList<>();
		for (String caracter : arr) {
			TextoInvalido.add(caracter);
		}

		invalidosAlmacenados.clear();
		for (String caracter : TextoInvalido) {
			if (!EspecialesList.contains(caracter) && !AbecedarioList.contains(caracter)
					&& !NumerosList.contains(caracter)) {
				//if (!invalidosAlmacenados.contains(caracter)) { }
					invalidosAlmacenados.add(caracter);
				
			}
		}
		return invalidosAlmacenados;
	}

	public static String quitarComentarios(String rep, ArrayList<String> comentarios) {
		// Expresión regular para comentarios de línea y bloque
		String regex = "(//.*?$)|(/\\*.*?\\*/)";
		Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(rep);

		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			// Agregar comentario al ArrayList
			comentarios.add(matcher.group());
			// Reemplazar comentario con una cadena vacía
			matcher.appendReplacement(sb, "");
		}
		matcher.appendTail(sb);

		return sb.toString();
	}

	// -------------------------------------------------------------------------------

	public static void stringToArrayList() {
		AbecedarioList.clear();
		NumerosList.clear();
		EspecialesList.clear();

		for (String letra : Abecedario) {
			AbecedarioList.add(letra);
		}
		for (String numero : Numeros) {
			NumerosList.add(numero);
		}
		for (String caracter : Especiales) {
			EspecialesList.add(caracter);
		}

	}
}
