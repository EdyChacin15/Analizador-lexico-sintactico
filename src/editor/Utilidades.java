package editor;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;


/* NUMERACION
 * La clase TextLineNumber es usada para colocar la numeracion, de  Rob Camick on May 23, 2009. Extraída de:
 * https://tips4java.wordpress.com/2009/05/23/text-component-line-number/
 * https://github.com/tips4java/tips4java/blob/main/source/TextLineNumber.java
 */

public class Utilidades {

	/**
	 * Agrega texto al final 
	 * @param line
	 * @param areaTexto
	 */
	public static void append(String line, JTextPane areaTexto) {
		try {
			Document doc = areaTexto.getDocument();
			doc.insertString(doc.getLength(), line, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para mostrar numeracion
	 * @param numeracion
	 * @param textArea
	 * @param scroll
	 */
	public static void viewNumeracionInicio(boolean numeracion, JTextPane textArea, JScrollPane scroll) {
		if (numeracion) {
			scroll.setRowHeaderView(new TextLineNumber(textArea)); 
		} else {
			scroll.setRowHeaderView(null);
		}
	}
	/**
	 * Metodo para mostrar numeracion
	 * @param contador
	 * @param numeracion
	 * @param textArea
	 * @param scroll
	 */
	public static void viewNumeracion(int contador, boolean numeracion, ArrayList<JTextPane> textArea,
			ArrayList<JScrollPane> scroll) {
		if (numeracion) {
			for (int i = 0; i < contador; i++) {
				scroll.get(i).setRowHeaderView(new TextLineNumber(textArea.get(i)));
			}
		} else {
			for (int i = 0; i < contador; i++) {
				scroll.get(i).setRowHeaderView(null);
			}
		}
	}

	/**
	 * Cambiar Apariencia del area de texto
	 * @param contador
	 * @param tipo
	 * @param tamano
	 * @param list
	 */
	public static void aFondo(int contador, String tipo, int tamano, ArrayList<JTextPane> list) {
		if (tipo.equals("w")) { // w -> white (blanco)
			for (int i = 0; i < contador; i++) {

				list.get(i).selectAll();

				StyleContext sc = StyleContext.getDefaultStyleContext();

				// Para color de texto
				AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);

				// Para el tipo de texto
				aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Arial");

				// Para el tamanio del texo
				aset = sc.addAttribute(aset, StyleConstants.FontSize, tamano);

				list.get(i).setCharacterAttributes(aset, false);
				list.get(i).setBackground(Color.WHITE);

				list.get(i).setCaretPosition(0); // Restablece la posición del caret al inicio
			}

		} else if (tipo.equals("d")) {
			for (int i = 0; i < contador; i++) {
				list.get(i).selectAll();

				StyleContext sc = StyleContext.getDefaultStyleContext();

				// Para color de texto
				AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground,
						new Color(161, 145, 123));

				// Para el tipo de texto
				aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Arial");

				// Para el tamanio del texo
				aset = sc.addAttribute(aset, StyleConstants.FontSize, tamano);

				list.get(i).setCharacterAttributes(aset, false);
				list.get(i).setBackground(new Color(32, 33, 36));

				list.get(i).setCaretPosition(0); // Restablece la posición del caret al inicio
			}
		}
	}

	/**
	 * Crear los botones para barra de herramientas
	 * @param url
	 * @param objContenedor
	 * @param rotulo
	 * @return
	 */
	public static JButton addButton(URL url, Object objContenedor, String rotulo) {
		JButton button = new JButton(
				new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		button.setToolTipText(rotulo);
		((Container) objContenedor).add(button);
		return button;
	}

	/**
	 * Cambiar el tamaño del texto
	 * @param tamano
	 * @param contador
	 * @param list
	 */
	public static void tamTexto(int tamano, int contador, ArrayList<JTextPane> list) {
		for (int i = 0; i < contador; i++) {
			// Seleccionamos todo el texto del area de texto
			list.get(i).selectAll();

			// Para cambiar tamanio del texto
			StyleContext sc = StyleContext.getDefaultStyleContext();
			AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.FontSize, tamano);

			// Aplicar el cambio
			list.get(i).setCharacterAttributes(aset, false);
		}
	}

	/**
	 * Metodo para bloquear y desbloquear Items cuando se requiera
	 * @param j (menuItems a desactivar y reactivar)
	 */
	public static void activaItems(JMenuItem j[]) {
		for (JMenuItem item : j) {
			item.setEnabled(true);
		}
	}
	
	

	public static void desactivaItems(JMenuItem j[]) {
		for (JMenuItem item : j) {
			item.setEnabled(false);
		}
	}
}
