package editor;

import java.io.File;

public class Principal {
	public static void main(String[] args) {
		String ruta1 = "C:/Users/Edymar/Documents/JAVA/Compiladores/Editor/src/editor/Lexer.flex";
		String ruta2 = "C:/Users/Edymar/Documents/JAVA/Compiladores/Editor/src/editor/LexerCup.flex";
		String[] rutaS = {"-parser", "Sintax", "C:/Users/Edymar/Documents/JAVA/Compiladores/Editor/src/editor/Sintax.cup"};
		//generarLexer(ruta);
	}
	public static void generarLexer(String ruta1, String ruta2, String[] rutaS) {
		File archivo;
		archivo = new File(ruta1);
		JFlex.Main.generate(archivo);
		archivo = new File(ruta2);
		JFlex.Main.generate(archivo);
		
	}
}
