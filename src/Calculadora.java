import javax.swing.*;
import java.awt.*;

/**
 * Calculadora cientifica hecha con Swing y GridBagLayout "a mano":
 * cada boton se crea, se coloca (gridx, gridy...) y se conecta con
 * su propio metodo, sin bucles ni arrays de textos.
 */
public class Calculadora extends JFrame {

    // ---- Colores del diseño ----
    // El fondo es el mas oscuro de todos, luego los botones de operacion,
    // luego los numericos (mas claros) y el igual en naranja.
    private static final Color COLOR_FONDO = new Color(30, 30, 30);
    private static final Color COLOR_PANTALLA = new Color(15, 15, 15);
    private static final Color COLOR_NUMERO = new Color(220, 220, 220);
    private static final Color COLOR_OPERACION = new Color(70, 70, 70);
    private static final Color COLOR_IGUAL = new Color(255, 140, 0);
    private static final Color TEXTO_CLARO = Color.WHITE;
    private static final Color TEXTO_OSCURO = Color.BLACK;

    private JTextField pantallaOperacion;
    private JTextField pantallaResultado;
    private JRadioButton radioGrados;
    private JRadioButton radioRadianes;

    // Aqui se va guardando la operacion que escribe el usuario
    private String expresion = "";

    public Calculadora() {
        setTitle("Calculadora Cientifica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 600);
        setMinimumSize(new Dimension(420, 560));
        setLocationRelativeTo(null);

        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.BOTH;

        // ---- Pantalla: la operacion en una linea y el resultado en otra ----
        pantallaOperacion = new JTextField("0");
        pantallaOperacion.setEditable(false);
        pantallaOperacion.setHorizontalAlignment(JTextField.RIGHT);
        pantallaOperacion.setFont(new Font("Consolas", Font.PLAIN, 18));
        pantallaOperacion.setBackground(COLOR_PANTALLA);
        pantallaOperacion.setForeground(Color.LIGHT_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.weightx = 1;
        gbc.weighty = 0;
        add(pantallaOperacion, gbc);

        pantallaResultado = new JTextField("0");
        pantallaResultado.setEditable(false);
        pantallaResultado.setHorizontalAlignment(JTextField.RIGHT);
        pantallaResultado.setFont(new Font("Consolas", Font.BOLD, 32));
        pantallaResultado.setBackground(COLOR_PANTALLA);
        pantallaResultado.setForeground(TEXTO_CLARO);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.weightx = 1;
        gbc.weighty = 0;
        add(pantallaResultado, gbc);

        // ---- Radiobuttons de grados / radianes ----
        radioGrados = new JRadioButton("Grados", true);
        radioRadianes = new JRadioButton("Radianes");
        radioGrados.setForeground(TEXTO_CLARO);
        radioRadianes.setForeground(TEXTO_CLARO);
        radioGrados.setOpaque(false);
        radioRadianes.setOpaque(false);

        ButtonGroup grupoAngulos = new ButtonGroup();
        grupoAngulos.add(radioGrados);
        grupoAngulos.add(radioRadianes);

        JPanel panelRadios = new JPanel();
        panelRadios.setBackground(COLOR_FONDO);
        panelRadios.add(radioGrados);
        panelRadios.add(radioRadianes);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        gbc.weightx = 1;
        gbc.weighty = 0;
        add(panelRadios, gbc);

        // ---- Fila de funciones trigonometricas y parentesis ----
        JButton botonSeno = crearBoton("sin(", COLOR_OPERACION, TEXTO_CLARO);
        botonSeno.addActionListener(e -> accionSeno());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(botonSeno, gbc);

        JButton botonCoseno = crearBoton("cos(", COLOR_OPERACION, TEXTO_CLARO);
        botonCoseno.addActionListener(e -> accionCoseno());
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(botonCoseno, gbc);

        JButton botonTangente = crearBoton("tan(", COLOR_OPERACION, TEXTO_CLARO);
        botonTangente.addActionListener(e -> accionTangente());
        gbc.gridx = 2;
        gbc.gridy = 3;
        add(botonTangente, gbc);

        JButton botonAbrePar = crearBoton("(", COLOR_OPERACION, TEXTO_CLARO);
        botonAbrePar.addActionListener(e -> accionAbrirParentesis());
        gbc.gridx = 3;
        gbc.gridy = 3;
        add(botonAbrePar, gbc);

        JButton botonCierraPar = crearBoton(")", COLOR_OPERACION, TEXTO_CLARO);
        botonCierraPar.addActionListener(e -> accionCerrarParentesis());
        gbc.gridx = 4;
        gbc.gridy = 3;
        add(botonCierraPar, gbc);

        // ---- Fila 7 8 9 / C ----
        JButton boton7 = crearBoton("7", COLOR_NUMERO, TEXTO_OSCURO);
        boton7.addActionListener(e -> escribir("7"));
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(boton7, gbc);

        JButton boton8 = crearBoton("8", COLOR_NUMERO, TEXTO_OSCURO);
        boton8.addActionListener(e -> escribir("8"));
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(boton8, gbc);

        JButton boton9 = crearBoton("9", COLOR_NUMERO, TEXTO_OSCURO);
        boton9.addActionListener(e -> escribir("9"));
        gbc.gridx = 2;
        gbc.gridy = 4;
        add(boton9, gbc);

        JButton botonDividir = crearBoton("/", COLOR_OPERACION, TEXTO_CLARO);
        botonDividir.addActionListener(e -> accionDividir());
        gbc.gridx = 3;
        gbc.gridy = 4;
        add(botonDividir, gbc);

        JButton botonLimpiar = crearBoton("C", COLOR_OPERACION, TEXTO_CLARO);
        botonLimpiar.addActionListener(e -> accionLimpiar());
        gbc.gridx = 4;
        gbc.gridy = 4;
        add(botonLimpiar, gbc);

        // ---- Fila 4 5 6 * <- ----
        JButton boton4 = crearBoton("4", COLOR_NUMERO, TEXTO_OSCURO);
        boton4.addActionListener(e -> escribir("4"));
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(boton4, gbc);

        JButton boton5 = crearBoton("5", COLOR_NUMERO, TEXTO_OSCURO);
        boton5.addActionListener(e -> escribir("5"));
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(boton5, gbc);

        JButton boton6 = crearBoton("6", COLOR_NUMERO, TEXTO_OSCURO);
        boton6.addActionListener(e -> escribir("6"));
        gbc.gridx = 2;
        gbc.gridy = 5;
        add(boton6, gbc);

        JButton botonMultiplicar = crearBoton("*", COLOR_OPERACION, TEXTO_CLARO);
        botonMultiplicar.addActionListener(e -> accionMultiplicar());
        gbc.gridx = 3;
        gbc.gridy = 5;
        add(botonMultiplicar, gbc);

        JButton botonBorrar = crearBoton("<-", COLOR_OPERACION, TEXTO_CLARO);
        botonBorrar.addActionListener(e -> accionBorrar());
        gbc.gridx = 4;
        gbc.gridy = 5;
        add(botonBorrar, gbc);

        // ---- Fila 1 2 3 - pi ----
        JButton boton1 = crearBoton("1", COLOR_NUMERO, TEXTO_OSCURO);
        boton1.addActionListener(e -> escribir("1"));
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(boton1, gbc);

        JButton boton2 = crearBoton("2", COLOR_NUMERO, TEXTO_OSCURO);
        boton2.addActionListener(e -> escribir("2"));
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(boton2, gbc);

        JButton boton3 = crearBoton("3", COLOR_NUMERO, TEXTO_OSCURO);
        boton3.addActionListener(e -> escribir("3"));
        gbc.gridx = 2;
        gbc.gridy = 6;
        add(boton3, gbc);

        JButton botonRestar = crearBoton("-", COLOR_OPERACION, TEXTO_CLARO);
        botonRestar.addActionListener(e -> accionRestar());
        gbc.gridx = 3;
        gbc.gridy = 6;
        add(botonRestar, gbc);

        JButton botonPi = crearBoton("π", COLOR_OPERACION, TEXTO_CLARO);
        botonPi.addActionListener(e -> accionPi());
        gbc.gridx = 4;
        gbc.gridy = 6;
        add(botonPi, gbc);

        // ---- Fila 0 . + (uso no simple del grid: 0 y + ocupan 2 columnas) ----
        JButton boton0 = crearBoton("0", COLOR_NUMERO, TEXTO_OSCURO);
        boton0.addActionListener(e -> escribir("0"));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(boton0, gbc);

        JButton botonPunto = crearBoton(".", COLOR_NUMERO, TEXTO_OSCURO);
        botonPunto.addActionListener(e -> accionPunto());
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        add(botonPunto, gbc);

        JButton botonSumar = crearBoton("+", COLOR_OPERACION, TEXTO_CLARO);
        botonSumar.addActionListener(e -> accionSumar());
        gbc.gridx = 3;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(botonSumar, gbc);

        // ---- Igual: ocupa todas las columnas, mas ancho que el resto ----
        JButton botonIgual = crearBoton("=", COLOR_IGUAL, TEXTO_OSCURO);
        botonIgual.addActionListener(e -> accionIgual());
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 5;
        gbc.weighty = 1.4;
        add(botonIgual, gbc);
    }

    /** Da estilo a un boton: color de fondo, color de letra y tipografia. */
    private JButton crearBoton(String texto, Color fondo, Color textoColor) {
        JButton boton = new JButton(texto);
        boton.setBackground(fondo);
        boton.setForeground(textoColor);
        boton.setFont(new Font("SansSerif", Font.BOLD, 18));
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        return boton;
    }

    // ---- Un metodo por cada boton de operar ----

    private void accionSumar() {
        escribir("+");
    }

    private void accionRestar() {
        escribir("-");
    }

    private void accionMultiplicar() {
        escribir("*");
    }

    private void accionDividir() {
        escribir("/");
    }

    private void accionSeno() {
        escribir("sin(");
    }

    private void accionCoseno() {
        escribir("cos(");
    }

    private void accionTangente() {
        escribir("tan(");
    }

    private void accionAbrirParentesis() {
        escribir("(");
    }

    private void accionCerrarParentesis() {
        escribir(")");
    }

    private void accionPi() {
        escribir("π");
    }

    private void accionPunto() {
        escribir(".");
    }

    private void accionBorrar() {
        if (!expresion.isEmpty()) {
            expresion = expresion.substring(0, expresion.length() - 1);
        }
        pantallaOperacion.setText(expresion.isEmpty() ? "0" : expresion);
    }

    private void accionLimpiar() {
        expresion = "";
        pantallaOperacion.setText("0");
        pantallaResultado.setText("0");
    }

    private void accionIgual() {
        if (expresion.isEmpty()) {
            return;
        }
        try {
            Evaluador evaluador = new Evaluador(expresion, radioGrados.isSelected());
            double resultado = evaluador.evaluar();
            pantallaResultado.setText(formatear(resultado));
        } catch (Exception ex) {
            pantallaResultado.setText("Error");
        }
        expresion = "";
        pantallaOperacion.setText("0");
    }

    /** Añade texto a la expresion y actualiza la linea de arriba. */
    private void escribir(String texto) {
        expresion = expresion + texto;
        pantallaOperacion.setText(expresion);
    }

    /** Si el resultado es entero lo muestro sin decimales. */
    private String formatear(double valor) {
        if (Double.isNaN(valor) || Double.isInfinite(valor)) {
            return "Error";
        }
        if (Math.abs(valor - Math.rint(valor)) < 1e-9) {
            return String.valueOf((long) Math.rint(valor));
        }
        return String.valueOf(Math.round(valor * 1e10) / 1e10);
    }

    /**
     * Lee la expresion de izquierda a derecha respetando la prioridad
     * de los operadores, los parentesis, pi y las funciones sin/cos/tan.
     */
    private static class Evaluador {

        private String texto;
        private boolean enGrados;
        private int posicion = 0;

        Evaluador(String texto, boolean enGrados) {
            this.texto = texto.replace(" ", "");
            this.enGrados = enGrados;
        }

        double evaluar() {
            double valor = leerSumas();
            if (posicion != texto.length()) {
                throw new RuntimeException("Expresion mal escrita");
            }
            return valor;
        }

        // sumas y restas
        private double leerSumas() {
            double valor = leerProductos();
            while (posicion < texto.length() && (actual() == '+' || actual() == '-')) {
                char operador = texto.charAt(posicion++);
                double siguiente = leerProductos();
                if (operador == '+') {
                    valor = valor + siguiente;
                } else {
                    valor = valor - siguiente;
                }
            }
            return valor;
        }

        // multiplicaciones y divisiones (tienen mas prioridad)
        private double leerProductos() {
            double valor = leerSigno();
            while (posicion < texto.length() && (actual() == '*' || actual() == '/')) {
                char operador = texto.charAt(posicion++);
                double siguiente = leerSigno();
                if (operador == '*') {
                    valor = valor * siguiente;
                } else {
                    if (siguiente == 0) {
                        throw new ArithmeticException("Division por cero");
                    }
                    valor = valor / siguiente;
                }
            }
            return valor;
        }

        // numeros con signo delante, por ejemplo -5 o -(2+3)
        private double leerSigno() {
            if (posicion < texto.length() && actual() == '-') {
                posicion++;
                return -leerSigno();
            }
            if (posicion < texto.length() && actual() == '+') {
                posicion++;
                return leerSigno();
            }
            return leerValor();
        }

        // un numero, pi, un parentesis o una funcion
        private double leerValor() {
            if (posicion >= texto.length()) {
                throw new RuntimeException("Expresion incompleta");
            }
            char c = actual();

            if (c == '(') {
                posicion++;
                double valor = leerSumas();
                esperar(')');
                return valor;
            }
            if (Character.isDigit(c) || c == '.') {
                return leerNumero();
            }
            if (c == 'π') {
                posicion++;
                return Math.PI;
            }
            if (texto.startsWith("sin", posicion)) {
                return Math.sin(leerArgumento());
            }
            if (texto.startsWith("cos", posicion)) {
                return Math.cos(leerArgumento());
            }
            if (texto.startsWith("tan", posicion)) {
                return Math.tan(leerArgumento());
            }
            throw new RuntimeException("Caracter inesperado: " + c);
        }

        /** Salta el nombre de la funcion, lee "(angulo)" y lo pasa a radianes. */
        private double leerArgumento() {
            posicion = posicion + 3;
            esperar('(');
            double angulo = leerSumas();
            esperar(')');
            return enGrados ? Math.toRadians(angulo) : angulo;
        }

        private double leerNumero() {
            int inicio = posicion;
            while (posicion < texto.length()
                    && (Character.isDigit(texto.charAt(posicion)) || texto.charAt(posicion) == '.')) {
                posicion++;
            }
            return Double.parseDouble(texto.substring(inicio, posicion));
        }

        private char actual() {
            return texto.charAt(posicion);
        }

        private void esperar(char c) {
            if (posicion >= texto.length() || texto.charAt(posicion) != c) {
                throw new RuntimeException("Falta '" + c + "'");
            }
            posicion++;
        }
    }

    public static void main(String[] args) {
        Calculadora ventana = new Calculadora();
        ventana.setVisible(true);
    }
}
