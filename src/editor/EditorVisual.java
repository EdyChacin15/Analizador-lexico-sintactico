/* URBE. 7mo Trimestre
 * PROYECTO COMPILADORES (C1113)
 * 
 *  Primera entrega: Editor de texto. 24/09/2024.
 *  
 *  Segunda entrega: Analizador lexico de texto. 04/11/2024.
 */

package editor;

import java.awt.EventQueue;



import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.UndoManager;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class EditorVisual {

	private JFrame marco;
	private ArrayList<JTextPane> listAreaTexto = new ArrayList<JTextPane>();
	private ArrayList<JScrollPane> listScroll = new ArrayList<JScrollPane>();
	private ArrayList<File> listFile = new ArrayList<File>();
	private ArrayList<UndoManager> listManager = new ArrayList<UndoManager>();

	private JToolBar herramientas = new JToolBar(SwingConstants.VERTICAL);
	private JTabbedPane tPane = new JTabbedPane(JTabbedPane.TOP);
	private JPopupMenu menuEmergente = new JPopupMenu();

	private int contadorPanel = 0;
	private boolean numeracion2 = true;
	private boolean existePanel = false;
	private String tipoFondo = "w";
	private String NombreArchivoActual = "";
	public String texto = "";

	private String lastSavedText = ""; // Almacena el último texto guardado

	private JSlider slider;
	private JTextField txtEspaciosEnBlanco;
	private JTextField txtLetras;
	private JTextField txtCaracteresEspeciales;
	private JTextField txtNmeros;
	private JTextField txtComentarios;
	private JTextField txtCaracteresInvlidos;
	private JTextField cantidadEspaciosEnBlanco;
	private JTextField cantidadLetras;
	private JTextField cantidadNumeros;
	private JTextField cantidadEspeciales;
	private JTextField cantidadInvalido;
	private JTextField cantidadComentarios;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditorVisual window = new EditorVisual();
					window.marco.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EditorVisual() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		marco = new JFrame();
		marco.getContentPane().setFont(new Font("Century Gothic", Font.PLAIN, 14));
		marco.setTitle("Edit Text");
		marco.setBounds(600, 600, 650, 600);
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marco.setLocationRelativeTo(null);

		// --- PANEL PRINCIPAL (contiene panel area de trabajo, analisis lexico,
		// analisis sintactico) ---

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		marco.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		// --------------- PANEL AREA DE TRABAJO ----------
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 128));
		panel.setLayout(new BorderLayout());

		JPanel panelMenu = new JPanel();
		panelMenu.setLayout(new BorderLayout());
		panel.add(panelMenu, BorderLayout.NORTH);

		// Contiene menuItems que se desactiva cuando no se utilizan
		JMenuItem items[] = new JMenuItem[8];

		JMenuBar menu = new JMenuBar();
		menu.setFont(new Font("Century Gothic", Font.BOLD, 14));
		panelMenu.add(menu, BorderLayout.NORTH);

		// ---------------- MENU ARCHIVO -----------------
		JMenu archivo = new JMenu("Archivo");
		archivo.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		menu.add(archivo);

		JMenuItem nuevoArchivo = new JMenuItem("Nuevo Archivo");
		nuevoArchivo.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		archivo.add(nuevoArchivo);

		JMenuItem abrirArchivo = new JMenuItem("Abrir Archivo");
		abrirArchivo.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		archivo.add(abrirArchivo);

		JMenuItem guardar = new JMenuItem("Guardar");
		guardar.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		items[0] = guardar;
		archivo.add(guardar);

		JMenuItem guardarComo = new JMenuItem("Guardar Como");
		guardarComo.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		items[1] = guardarComo;
		archivo.add(guardarComo);

		// ------------------ MENU EDITAR -------------------
		JMenu editar = new JMenu("Editar");
		editar.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		menu.add(editar);

		JMenuItem deshacer = new JMenuItem("Deshacer");
		deshacer.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		items[2] = deshacer;
		editar.add(deshacer);

		JMenuItem rehacer = new JMenuItem("Rehacer");
		rehacer.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		items[3] = rehacer;
		editar.add(rehacer);
		editar.addSeparator();

		JMenuItem cortar = new JMenuItem("Cortar");
		cortar.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		items[4] = cortar;
		editar.add(cortar);

		JMenuItem copiar = new JMenuItem("Copiar");
		copiar.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		items[5] = copiar;
		editar.add(copiar);

		JMenuItem pegar = new JMenuItem("Pegar");
		pegar.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		items[6] = pegar;
		editar.add(pegar);

		// ------------------ MENU SELECCION ----------------
		JMenu seleccion = new JMenu("Seleccion");
		seleccion.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		menu.add(seleccion);

		JMenuItem seleccionarTodo = new JMenuItem("Seleccionar Todo");
		seleccionarTodo.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		items[7] = seleccionarTodo;
		seleccion.add(seleccionarTodo);

		// ------------------- MENU VER ---------------------
		JMenu ver = new JMenu("Ver");
		ver.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		menu.add(ver);

		JMenuItem numeracion = new JMenuItem("Numeracion");
		numeracion.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		ver.add(numeracion);

		JMenu apariencia = new JMenu("Apariencia");
		apariencia.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		// ver.add(apariencia);

		JMenuItem normal = new JMenuItem("Normal");
		normal.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		apariencia.add(normal);

		JMenuItem dark = new JMenuItem("Oscuro");
		dark.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		apariencia.add(dark);

		// -------- PANEL PARA EL SLIDER --------------
		JPanel panelExtra = new JPanel();
		panelExtra.setBackground(new Color(79, 79, 79));
		panelExtra.setLayout(new BorderLayout());
		JPanel panelCentro = new JPanel();
		panelCentro.setBackground(new Color(79, 79, 79));

		// Slider para cambiar tamaño de la fuente
		slider = new JSlider(8, 38, 14);
		slider.setBorder(null);
		slider.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		slider.setBackground(new Color(192, 192, 192));
		slider.setMajorTickSpacing(6); // La separacion entre las barras grandes es de 6 en 6
		slider.setMinorTickSpacing(2); // Indica que la separacion entre las barras pequeñas es de 2 en 2
		slider.setPaintTicks(true);
		slider.setPaintLabels(true); // Numeros de slider

		panelCentro.add(slider);
		panelExtra.add(panelCentro, BorderLayout.EAST);

		// ----------- MENU EMERGENTE (Click derecho sobre area de texto) ---------
		JMenuItem cortarMenu = new JMenuItem("Cortar");
		JMenuItem copiarMenu = new JMenuItem("Copiar");
		JMenuItem pegarMenu = new JMenuItem("Pegar");

		cortarMenu.addActionListener(new DefaultEditorKit.CutAction());
		copiarMenu.addActionListener(new DefaultEditorKit.CopyAction());
		pegarMenu.addActionListener(new DefaultEditorKit.PasteAction());

		menuEmergente.add(cortarMenu);
		menuEmergente.add(copiarMenu);
		menuEmergente.add(pegarMenu);

		// -------- BARRA DE HERRAMIENTAS --------------

		// Cerrar pestaña actual
		URL url = EditorVisual.class.getResource("/img/marca-x.png");
		Utilidades.addButton(url, herramientas, "Cerrar Pestaña Actual").addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int seleccion = tPane.getSelectedIndex();
				if (seleccion != -1) {

					// Verifica si hay cambios en la pestaña actual
					if (hayCambios(seleccion)) {
						// Prompts al usuario para decidir qué hacer
						int respuesta = JOptionPane.showConfirmDialog(null,
								"Hay cambios sin guardar. ¿Desea guardar antes de cerrar?", "Confirmar Cierre",
								JOptionPane.YES_NO_CANCEL_OPTION);

						if (respuesta == JOptionPane.YES_OPTION) {
							// Guarda los cambios llamando al método de guardar existente
							guardarYActualizar(seleccion);
						} else if (respuesta == JOptionPane.CANCEL_OPTION) {
							// Cancelar el cierre
							return;
						}
						// Si la respuesta es NO, simplemente cerramos sin guardar
					}

					// Si existen pestañas abiertas, se elimina la pestaña seleccionada
					listScroll.get(tPane.getSelectedIndex()).setRowHeader(null);
					tPane.remove(seleccion);
					listAreaTexto.remove(seleccion);
					listScroll.remove(seleccion);
					listManager.remove(seleccion);
					listFile.remove(seleccion);
					contadorPanel--;
				}
				if (tPane.getSelectedIndex() == -1) {
					existePanel = false; // Si retorna -1 quiere decir que no existen paneles creados
					Utilidades.desactivaItems(items);
				}
			}
		});

		// Crear nueva pestaña
		url = EditorVisual.class.getResource("/img/mas (1).png");
		Utilidades.addButton(url, herramientas, "Nuevo Archivo").addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				crearNuevoPanel();
				if (existePanel)
					Utilidades.activaItems(items);
			}
		});

		// ------------------ PANEL ANALISIS LEXICO ---------------------------

		JPanel panelLexico = new JPanel();
		panelLexico.setBackground(new Color(128, 128, 128));

		// ------------------ PANEL ANALISIS LEXICO ---------------------------

		JPanel panelSintactico = new JPanel();
		panelSintactico.setBackground(new Color(128, 128, 128));

		// --------------------------------------------------------------------

		Utilidades.desactivaItems(items);
		panel.add(tPane, BorderLayout.CENTER);
		panel.add(panelExtra, BorderLayout.SOUTH);
		herramientas.setBackground(new Color(192, 192, 192));
		panel.add(herramientas, BorderLayout.WEST);

		tabbedPane.addTab("Área de trabajo", panel);
		tabbedPane.addTab("Análisis léxico", panelLexico);
		tabbedPane.addTab("Análisis Sintático", panelSintactico);
		panelSintactico.setLayout(null);
		
		JTextArea txtResultado = new JTextArea();
		txtResultado.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		txtResultado.setBounds(30, 92, 490, 223);
		panelSintactico.add(txtResultado);
		panelLexico.setLayout(null);
		
		JButton btnAnalizar = new JButton("Analizar");
		btnAnalizar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        JFileChooser chooser = new JFileChooser();
		        int resultado = chooser.showOpenDialog(null); // Guardamos la acción del usuario
		        
		        // Verificamos si el usuario seleccionó un archivo y le dio a "Aceptar"
		        if (resultado == JFileChooser.APPROVE_OPTION) {
		            try {
		                Reader lector = new BufferedReader(new FileReader(chooser.getSelectedFile()));
		                Lexer lexer = new Lexer(lector);
		                String resultadoTexto = "";
		                while(true) {
		                    Tokens tokens = lexer.yylex();
		                    if(tokens == null) {
		                        resultadoTexto += "FIN";
		                        txtResultado.setText(resultadoTexto);
		                        return;
		                    }
		                    switch (tokens) {
		                        case ERROR:
		                            resultadoTexto += "Simbolo no definido\n";
		                        break;
		                            
		                        case Identificador: case Numero: case Reservadas:
		                            resultadoTexto += lexer.lexeme + ": Es un " + tokens + "\n";
		                        break;
		                        
		                        default:
		                            resultadoTexto += "Token: " + tokens + "\n";
		                        break;
		                    }
		                }
		            } catch (IOException e1) {
		                e1.printStackTrace();
		            }
		        }
		    }
		});
		btnAnalizar.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		btnAnalizar.setBounds(30, 40, 490, 30);
		panelSintactico.add(btnAnalizar);
		
		

		JTextArea TextoSinEspacios = new JTextArea();
		TextoSinEspacios.setBackground(new Color(232, 232, 232));
		TextoSinEspacios.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		TextoSinEspacios.setEditable(false);
		TextoSinEspacios.setLineWrap(true);
		TextoSinEspacios.setWrapStyleWord(true);

		JScrollPane scrollLexico = new JScrollPane(TextoSinEspacios);
		scrollLexico.setBounds(30, 38, 346, 104);
		scrollLexico.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Siempre mostrar scroll
																						// vertical // vertical
		scrollLexico.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // No mostrar scroll
																							// horizontal // horizontal
		panelLexico.add(scrollLexico);

		JTextArea TextoSinComentarios = new JTextArea();
		TextoSinComentarios.setBackground(new Color(232, 232, 232));
		TextoSinComentarios.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		TextoSinComentarios.setEditable(false);
		TextoSinComentarios.setLineWrap(true);
		TextoSinComentarios.setWrapStyleWord(true);

		JScrollPane scrollLexico2 = new JScrollPane(TextoSinComentarios);
		scrollLexico2.setBounds(30, 163, 346, 104);
		scrollLexico2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Siempre mostrar scroll
																							// vertical // vertical
		scrollLexico2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // No mostrar scroll
																							// horizontal // horizontal
		panelLexico.add(scrollLexico2);

		JLabel lblNombreArchivo = new JLabel("No seleccionado");
		lblNombreArchivo.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		lblNombreArchivo.setBounds(112, 11, 213, 24);
		panelLexico.add(lblNombreArchivo);

		JLabel lblArchivo = new JLabel("Archivo: ");
		lblArchivo.setHorizontalAlignment(SwingConstants.CENTER);
		lblArchivo.setFont(new Font("Century Gothic", Font.BOLD, 17));
		lblArchivo.setBounds(20, 16, 82, 14);
		panelLexico.add(lblArchivo);

		JLabel lblTextoSinEspacios = new JLabel("   Código sin espacios en blanco");
		lblTextoSinEspacios.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		lblTextoSinEspacios.setBounds(30, 140, 238, 14);
		panelLexico.add(lblTextoSinEspacios);

		JLabel lblContadores = new JLabel("CONTADORES:");
		lblContadores.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblContadores.setBounds(86, 292, 158, 27);
		panelLexico.add(lblContadores);

		txtEspaciosEnBlanco = new JTextField();
		txtEspaciosEnBlanco.setHorizontalAlignment(SwingConstants.CENTER);
		txtEspaciosEnBlanco.setText("Espacios en blanco:");
		txtEspaciosEnBlanco.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		txtEspaciosEnBlanco.setEditable(false);
		txtEspaciosEnBlanco.setColumns(10);
		txtEspaciosEnBlanco.setBounds(72, 322, 184, 20);
		panelLexico.add(txtEspaciosEnBlanco);

		txtLetras = new JTextField();
		txtLetras.setText("Letras:");
		txtLetras.setHorizontalAlignment(SwingConstants.CENTER);
		txtLetras.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		txtLetras.setEditable(false);
		txtLetras.setColumns(10);
		txtLetras.setBounds(72, 352, 184, 20);
		panelLexico.add(txtLetras);

		txtCaracteresEspeciales = new JTextField();
		txtCaracteresEspeciales.setText("Caracteres especiales:");
		txtCaracteresEspeciales.setHorizontalAlignment(SwingConstants.CENTER);
		txtCaracteresEspeciales.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		txtCaracteresEspeciales.setEditable(false);
		txtCaracteresEspeciales.setColumns(10);
		txtCaracteresEspeciales.setBounds(72, 412, 184, 20);
		panelLexico.add(txtCaracteresEspeciales);

		txtNmeros = new JTextField();
		txtNmeros.setText("Números:");
		txtNmeros.setHorizontalAlignment(SwingConstants.CENTER);
		txtNmeros.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		txtNmeros.setEditable(false);
		txtNmeros.setColumns(10);
		txtNmeros.setBounds(72, 382, 184, 20);
		panelLexico.add(txtNmeros);

		txtComentarios = new JTextField();
		txtComentarios.setText("Comentarios:");
		txtComentarios.setHorizontalAlignment(SwingConstants.CENTER);
		txtComentarios.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		txtComentarios.setEditable(false);
		txtComentarios.setColumns(10);
		txtComentarios.setBounds(72, 472, 184, 20);
		panelLexico.add(txtComentarios);

		txtCaracteresInvlidos = new JTextField();
		txtCaracteresInvlidos.setText("Caracteres inválidos:");
		txtCaracteresInvlidos.setHorizontalAlignment(SwingConstants.CENTER);
		txtCaracteresInvlidos.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		txtCaracteresInvlidos.setEditable(false);
		txtCaracteresInvlidos.setColumns(10);
		txtCaracteresInvlidos.setBounds(72, 442, 184, 20);
		panelLexico.add(txtCaracteresInvlidos);

		cantidadEspaciosEnBlanco = new JTextField();
		cantidadEspaciosEnBlanco.setHorizontalAlignment(SwingConstants.CENTER);
		cantidadEspaciosEnBlanco.setText("0");
		cantidadEspaciosEnBlanco.setFont(new Font("Century Gothic", Font.BOLD, 14));
		cantidadEspaciosEnBlanco.setForeground(new Color(212, 212, 212));
		cantidadEspaciosEnBlanco.setBackground(new Color(35, 35, 35));
		cantidadEspaciosEnBlanco.setEditable(false);
		cantidadEspaciosEnBlanco.setBounds(275, 322, 60, 20);
		panelLexico.add(cantidadEspaciosEnBlanco);
		cantidadEspaciosEnBlanco.setColumns(10);

		cantidadLetras = new JTextField();
		cantidadLetras.setText("0");
		cantidadLetras.setHorizontalAlignment(SwingConstants.CENTER);
		cantidadLetras.setForeground(new Color(212, 212, 212));
		cantidadLetras.setFont(new Font("Century Gothic", Font.BOLD, 14));
		cantidadLetras.setEditable(false);
		cantidadLetras.setColumns(10);
		cantidadLetras.setBackground(new Color(35, 35, 35));
		cantidadLetras.setBounds(275, 352, 60, 20);
		panelLexico.add(cantidadLetras);

		cantidadNumeros = new JTextField();
		cantidadNumeros.setText("0");
		cantidadNumeros.setHorizontalAlignment(SwingConstants.CENTER);
		cantidadNumeros.setForeground(new Color(212, 212, 212));
		cantidadNumeros.setFont(new Font("Century Gothic", Font.BOLD, 14));
		cantidadNumeros.setEditable(false);
		cantidadNumeros.setColumns(10);
		cantidadNumeros.setBackground(new Color(35, 35, 35));
		cantidadNumeros.setBounds(275, 382, 60, 20);
		panelLexico.add(cantidadNumeros);

		cantidadEspeciales = new JTextField();
		cantidadEspeciales.setText("0");
		cantidadEspeciales.setHorizontalAlignment(SwingConstants.CENTER);
		cantidadEspeciales.setForeground(new Color(212, 212, 212));
		cantidadEspeciales.setFont(new Font("Century Gothic", Font.BOLD, 14));
		cantidadEspeciales.setEditable(false);
		cantidadEspeciales.setColumns(10);
		cantidadEspeciales.setBackground(new Color(35, 35, 35));
		cantidadEspeciales.setBounds(275, 412, 60, 20);
		panelLexico.add(cantidadEspeciales);

		cantidadInvalido = new JTextField();
		cantidadInvalido.setText("0");
		cantidadInvalido.setHorizontalAlignment(SwingConstants.CENTER);
		cantidadInvalido.setForeground(new Color(212, 212, 212));
		cantidadInvalido.setFont(new Font("Century Gothic", Font.BOLD, 14));
		cantidadInvalido.setEditable(false);
		cantidadInvalido.setColumns(10);
		cantidadInvalido.setBackground(new Color(35, 35, 35));
		cantidadInvalido.setBounds(275, 442, 60, 20);
		panelLexico.add(cantidadInvalido);

		cantidadComentarios = new JTextField();
		cantidadComentarios.setText("0");
		cantidadComentarios.setHorizontalAlignment(SwingConstants.CENTER);
		cantidadComentarios.setForeground(new Color(212, 212, 212));
		cantidadComentarios.setFont(new Font("Century Gothic", Font.BOLD, 14));
		cantidadComentarios.setEditable(false);
		cantidadComentarios.setColumns(10);
		cantidadComentarios.setBackground(new Color(35, 35, 35));
		cantidadComentarios.setBounds(275, 472, 60, 20);
		panelLexico.add(cantidadComentarios);

		ArrayList<String> nombres = new ArrayList<String>();
		nombres.add("");

		DefaultComboBoxModel<String> modeloLetras = new DefaultComboBoxModel<>(nombres.toArray(new String[0]));
		JComboBox<String> listaLetras = new JComboBox<>(modeloLetras);
		listaLetras.setBounds(408, 59, 200, 25);
		panelLexico.add(listaLetras);

		DefaultComboBoxModel<String> modeloNumeros = new DefaultComboBoxModel<>(nombres.toArray(new String[0]));
		JComboBox<String> listaNumeros = new JComboBox<>(modeloNumeros);
		listaNumeros.setBounds(408, 159, 200, 25);
		panelLexico.add(listaNumeros);

		DefaultComboBoxModel<String> modeloEspeciales = new DefaultComboBoxModel<>(nombres.toArray(new String[0]));
		JComboBox<String> listaEspeciales = new JComboBox<>(modeloEspeciales);
		listaEspeciales.setBounds(408, 259, 200, 25);
		panelLexico.add(listaEspeciales);

		DefaultComboBoxModel<String> modeloInvalidos = new DefaultComboBoxModel<>(nombres.toArray(new String[0]));
		JComboBox<String> listaInvalidos = new JComboBox<>(modeloInvalidos);
		listaInvalidos.setBounds(408, 359, 200, 25);
		panelLexico.add(listaInvalidos);

		DefaultComboBoxModel<String> modeloComentarios = new DefaultComboBoxModel<>(nombres.toArray(new String[0]));
		JComboBox<String> listaComentarios = new JComboBox<>(modeloComentarios);
		listaComentarios.setBounds(408, 459, 200, 25);
		panelLexico.add(listaComentarios);

		JLabel lblListaLetras = new JLabel("Letras");
		lblListaLetras.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblListaLetras.setBounds(408, 34, 46, 24);
		panelLexico.add(lblListaLetras);

		JLabel lblListaNumeros = new JLabel("Números");
		lblListaNumeros.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblListaNumeros.setBounds(408, 134, 150, 24);
		panelLexico.add(lblListaNumeros);

		JLabel lblListaEspeciales = new JLabel("Caracteres especiales");
		lblListaEspeciales.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblListaEspeciales.setBounds(408, 234, 188, 24);
		panelLexico.add(lblListaEspeciales);

		JLabel lblListaInvalidos = new JLabel("Caracteres inválidos");
		lblListaInvalidos.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblListaInvalidos.setBounds(408, 334, 188, 24);
		panelLexico.add(lblListaInvalidos);

		JLabel lblComentarios = new JLabel("Comentarios");
		lblComentarios.setFont(new Font("Century Gothic", Font.BOLD, 15));
		lblComentarios.setBounds(408, 434, 188, 24);
		panelLexico.add(lblComentarios);

		JLabel lblTextoSinComentarios = new JLabel("   Código sin comentarios");
		lblTextoSinComentarios.setFont(new Font("Century Gothic", Font.ITALIC, 12));
		lblTextoSinComentarios.setBounds(30, 267, 238, 14);
		panelLexico.add(lblTextoSinComentarios);

		// ---------------------------------------------------------------------

		// Agregar un ChangeListener al tabbedPane para actualizar textArea
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				cantidadEspaciosEnBlanco.setText("");
				cantidadLetras.setText("");
				cantidadNumeros.setText("");
				cantidadEspeciales.setText("");
				cantidadInvalido.setText("");
				cantidadComentarios.setText("");
				modeloLetras.removeAllElements();
				modeloNumeros.removeAllElements();
				modeloEspeciales.removeAllElements();
				modeloInvalidos.removeAllElements();
				modeloComentarios.removeAllElements();
				int selectedIndex = tabbedPane.getSelectedIndex();
				if (selectedIndex == 1) { // "Análisis léxico" es la segunda pestaña (índice 1)
					int panelActual = tPane.getSelectedIndex();
					if (panelActual != -1) {
						String texto = listAreaTexto.get(panelActual).getText();
						
						TextoSinEspacios.setText(Lexico.LimpiarTexto(texto));
						ArrayList<String> comentarios = new ArrayList<>();
						String codigoSinComentarios = Lexico.quitarComentarios(texto, comentarios);

						modeloComentarios.removeAllElements();
						for (String comentario : comentarios) {
							modeloComentarios.addElement(comentario);
						}

						TextoSinComentarios.setText(codigoSinComentarios);
						cantidadComentarios.setText(Integer.toString(comentarios.size()));

						for (String com : comentarios) {
							System.out.println(com);
						}

						// ACTUALIZAR LOS CONTADORES
						cantidadEspaciosEnBlanco.setText(Lexico.CantidadEspacios(codigoSinComentarios));
						cantidadLetras.setText(Lexico.CantidadLetras());
						cantidadNumeros.setText(Lexico.CantidadNumeros());
						cantidadEspeciales.setText(Lexico.CantidadEspeciales());

						modeloLetras.removeAllElements();
						for (String letra : Lexico.LetrasAlmacenadas()) {
							modeloLetras.addElement(letra);
						}

						modeloNumeros.removeAllElements();
						for (String numero : Lexico.NumerosAlmacenados()) {
							modeloNumeros.addElement(numero);
						}

						modeloEspeciales.removeAllElements();
						for (String caracter : Lexico.EspecialesAlmacenados()) {
							modeloEspeciales.addElement(caracter);
						}

						File archivoActual = listFile.get(panelActual); // Obtener el archivo correspondiente
						if (archivoActual != null) {
							NombreArchivoActual = archivoActual.getName();
							FileReader entrada;

							try {
								boolean permitido = true;
								if (hayCambios(panelActual) || archivoActual.getPath() == ""
										|| archivoActual.getPath() == null) {
									// Prompts al usuario para decidir qué hacer
									int respuesta = JOptionPane.showConfirmDialog(null,
											"No puede acceder al analizador lexico si no ha guardado cambios. ¿Desea guardar?",
											"Guardar Cambios", JOptionPane.YES_NO_CANCEL_OPTION);

									if (respuesta == JOptionPane.YES_OPTION) {
										// Guarda los cambios llamando al método de guardar existente
										guardarYActualizar(panelActual);

									} else {
										permitido = false;
										tabbedPane.setSelectedIndex(0);
										return;
									}
									// Si la respuesta es NO, simplemente cerramos sin guardar
								}
								if (permitido) {
									entrada = new FileReader(archivoActual.getPath());
									BufferedReader bf = new BufferedReader(entrada); // hereeee
									String linea;
									String TextoInvalidos = "";

									while ((linea = bf.readLine()) != null) {
										TextoInvalidos += linea;
									}

									modeloInvalidos.removeAllElements();
									for (String caracter : Lexico.InvalidosAlmacenados(TextoInvalidos)) {
										modeloInvalidos.addElement(caracter);
									}
									cantidadInvalido.setText(Lexico.CantidadInvalidos(TextoInvalidos));

									
									System.out.println(" TEXTO SIN COMENTARIOS");
									System.out.println(codigoSinComentarios);

									bf.close();

								}
							} catch (FileNotFoundException e1) {
								
								e1.printStackTrace();
							} catch (IOException e1) {
							
								e1.printStackTrace();
							}
						}

						lblNombreArchivo.setText(NombreArchivoActual);

					} else {
						lblNombreArchivo.setText("No seleccionado");
						TextoSinEspacios.setText("");
					}
				}
			}
		});

		// ------------------- ACCIONES DE LOS MENUSITEMS ---------------------

		nuevoArchivo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				crearNuevoPanel();
				if (existePanel)
					Utilidades.activaItems(items);
			}
		});

		abrirArchivo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				crearNuevoPanel();

				JFileChooser selectorArchivos = new JFileChooser();
				selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

				int resultado = selectorArchivos.showOpenDialog(listAreaTexto.get(tPane.getSelectedIndex()));

				if (resultado == JFileChooser.APPROVE_OPTION) {
					if (existePanel)
						Utilidades.activaItems(items);
					File archivo = selectorArchivos.getSelectedFile();

					// Verificar si el archivo ya está abierto
					int indiceArchivo = obtenerIndiceArchivoAbierto(archivo);
					if (indiceArchivo != -1) {
						// Si el archivo ya está abierto, selecciona la pestaña correspondiente
						tPane.setSelectedIndex(indiceArchivo);
					} else {
						// Si el archivo no está abierto, cargar el archivo en la pestaña actual
						cargarArchivo(archivo);
					}

				} else {
					// Eliminar área de texto por defecto al dar a cancelar
					eliminarAreaTexto();
				}
			}
		});

		guardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Guardar como si el archivo no existe
				if (listFile.get(tPane.getSelectedIndex()).getPath().equals("")) {
					JFileChooser guardarArchivos = new JFileChooser();
					int opc = guardarArchivos.showOpenDialog(null);

					if (opc == JFileChooser.APPROVE_OPTION) {
						File archivo = guardarArchivos.getSelectedFile();
						listFile.set(tPane.getSelectedIndex(), archivo);
						tPane.setTitleAt(tPane.getSelectedIndex(), archivo.getName());
						guardarCambios();
					}
				} else {
					guardarCambios();
				}
			}
		});

		guardarComo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser guardarArchivos = new JFileChooser();
				int opc = guardarArchivos.showOpenDialog(null);

				if (opc == JFileChooser.APPROVE_OPTION) {
					File archivo = guardarArchivos.getSelectedFile();
					listFile.set(tPane.getSelectedIndex(), archivo);
					tPane.setTitleAt(tPane.getSelectedIndex(), archivo.getName());
					guardarCambios();
				}
			}
		});

		deshacer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listManager.get(tPane.getSelectedIndex()).canUndo())
					listManager.get(tPane.getSelectedIndex()).undo();
			}
		});

		rehacer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (listManager.get(tPane.getSelectedIndex()).canRedo())
					listManager.get(tPane.getSelectedIndex()).redo();
			}
		});

		cortar.addActionListener(new DefaultEditorKit.CutAction());
		copiar.addActionListener(new DefaultEditorKit.CopyAction());
		pegar.addActionListener(new DefaultEditorKit.PasteAction());

		seleccionarTodo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listAreaTexto.get(tPane.getSelectedIndex()).selectAll();
			}
		});

		numeracion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numeracion2 = !numeracion2;
				Utilidades.viewNumeracion(contadorPanel, numeracion2, listAreaTexto, listScroll);
			}
		});

		normal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tipoFondo = "w";
				if (tPane.getTabCount() > 0)
					Utilidades.aFondo(contadorPanel, tipoFondo, slider.getValue(), listAreaTexto);
			}
		});

		dark.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tipoFondo = "d";
				if (tPane.getTabCount() > 0)
					Utilidades.aFondo(contadorPanel, tipoFondo, slider.getValue(), listAreaTexto);
			}
		});

		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Utilidades.tamTexto(slider.getValue(), contadorPanel, listAreaTexto);
			}
		});
	}

	/**
	 * CREA UN NUEVO PANEL
	 */
	public void crearNuevoPanel() {
		JPanel ventana = new JPanel();
		ventana.setLayout(new BorderLayout());
		listFile.add(new File(""));
		listAreaTexto.add(new JTextPane());
		listScroll.add(new JScrollPane(listAreaTexto.get(contadorPanel)));
		listManager.add(new UndoManager()); // Para rastrear los cambios del area de texto

		listAreaTexto.get(contadorPanel).getDocument().addUndoableEditListener(listManager.get(contadorPanel));
		listAreaTexto.get(contadorPanel).setComponentPopupMenu(menuEmergente);
		ventana.add(listScroll.get(contadorPanel), BorderLayout.CENTER);

		tPane.addTab("title", ventana);
		NombreArchivoActual = "title";

		Utilidades.viewNumeracionInicio(numeracion2, listAreaTexto.get(contadorPanel), listScroll.get(contadorPanel));
		tPane.setSelectedIndex(contadorPanel);
		Utilidades.aFondo(contadorPanel, tipoFondo, slider.getValue(), listAreaTexto);
		existePanel = true;
		contadorPanel++;
	}

	/**
	 * Método para obtener el índice de la pestaña donde se encuentra el archivo
	 * abierto
	 * 
	 * @param archivo
	 * @return Indice del archivo
	 */
	public int obtenerIndiceArchivoAbierto(File archivo) {
		for (int i = 0; i < tPane.getTabCount(); i++) {
			if (listFile.get(i).getPath().equals(archivo.getPath())) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Método para cargar el archivo en la pestaña actual
	 * 
	 * @param archivo
	 */
	public void cargarArchivo(File archivo) {
		try {
			listFile.set(tPane.getSelectedIndex(), archivo);
			FileReader entrada = new FileReader(archivo.getPath());
			BufferedReader bf = new BufferedReader(entrada);
			String linea;
			String titulo = archivo.getName();

			tPane.setTitleAt(tPane.getSelectedIndex(), titulo);

			while ((linea = bf.readLine()) != null) {
				Utilidades.append(linea + "\n", listAreaTexto.get(tPane.getSelectedIndex()));
			}
			Utilidades.aFondo(contadorPanel, tipoFondo, slider.getValue(), listAreaTexto);
			bf.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Método para eliminar la pestaña actual
	 */
	public void eliminarAreaTexto() {
		int seleccion = tPane.getSelectedIndex();
		if (seleccion != -1) {
			listAreaTexto.remove(seleccion);
			listScroll.remove(seleccion);
			listFile.remove(seleccion);
			tPane.remove(seleccion);
			contadorPanel--;
		}
	}

	/**
	 * Metódo para guardar cambios realizados en el area de texto
	 */
	public void guardarCambios() {
		try {
			FileWriter fw = new FileWriter(listFile.get(tPane.getSelectedIndex()).getPath());
			String texto = listAreaTexto.get(tPane.getSelectedIndex()).getText();

			for (int i = 0; i < texto.length(); i++) {
				fw.write(texto.charAt(i));
			}

			fw.close();

			// Actualiza el texto guardado
			lastSavedText = texto;

		} catch (IOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al guardar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Método que verifica los cambios
	private boolean hayCambios(int index) {
		String textoActual = listAreaTexto.get(index).getText();
		// Compara el texto actual con el último texto guardado
		return !textoActual.equals(lastSavedText);
	}

	// Método para guardar y actualizar
	private void guardarYActualizar(int seleccion) {
		// Obtén la ruta del archivo
		String rutaArchivo = listFile.get(seleccion).getPath();

		// Verifica si el archivo ya existe
		if (rutaArchivo == null || rutaArchivo.isEmpty()) {
			// Si el archivo no existe, usar "Guardar Como..."
			JFileChooser guardarArchivos = new JFileChooser();
			int opc = guardarArchivos.showSaveDialog(null);

			if (opc == JFileChooser.APPROVE_OPTION) {
				File archivo = guardarArchivos.getSelectedFile();
				listFile.set(seleccion, archivo); // Actualiza la lista de archivos
				tPane.setTitleAt(seleccion, archivo.getName()); // Actualiza el título de la pestaña

				// Llama al método de guardar que ya tienes
				guardarCambios();
			}
		} else {
			// Si el archivo existe, llama al método de guardar existente
			guardarCambios();
		}
	}
}
