import javax.swing.*;
import java.awt.*;


public class Calculadora extends JFrame {

    private JTextField pantallaOperacion;
    private JTextField pantallaResultado;
    private JRadioButton radioGrados;
    private JRadioButton radioRadianes;

    // ---- Estado de la calculadora ----
    private double resultado = 0;
    private String operadorPendiente = null;
    private String numeroActual = "";
    private String textoOperacion = "";

    public Calculadora() {
        setTitle("Calculadora Cientifica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 600);
        setMinimumSize(new Dimension(420, 560));
        setLocationRelativeTo(null);

        getContentPane().setBackground(Estilo.FONDO);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.BOTH;

       
        pantallaOperacion = new JTextField("0");
        Estilo.aplicarPantalla(pantallaOperacion, Estilo.FUENTE_PANTALLA_OPERACION, Color.LIGHT_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.weightx = 1;
        gbc.weighty = 0;
        add(pantallaOperacion, gbc);

        pantallaResultado = new JTextField("0");
        Estilo.aplicarPantalla(pantallaResultado, Estilo.FUENTE_PANTALLA_RESULTADO, Estilo.TEXTO_CLARO);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.weightx = 1;
        gbc.weighty = 0;
        add(pantallaResultado, gbc);

  
        radioGrados = new JRadioButton("Grados", true);
        radioRadianes = new JRadioButton("Radianes");
        Estilo.aplicarRadio(radioGrados);
        Estilo.aplicarRadio(radioRadianes);

        ButtonGroup grupoAngulos = new ButtonGroup();
        grupoAngulos.add(radioGrados);
        grupoAngulos.add(radioRadianes);

        JPanel panelRadios = new JPanel();
        panelRadios.setBackground(Estilo.FONDO);
        panelRadios.add(radioGrados);
        panelRadios.add(radioRadianes);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        gbc.weightx = 1;
        gbc.weighty = 0;
        add(panelRadios, gbc);

        
        JButton botonSeno = Estilo.crearBoton("sin", Estilo.BOTON_OPERACION, Estilo.TEXTO_CLARO);
        botonSeno.addActionListener(e -> accionSeno());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(botonSeno, gbc);

        JButton botonCoseno = Estilo.crearBoton("cos", Estilo.BOTON_OPERACION, Estilo.TEXTO_CLARO);
        botonCoseno.addActionListener(e -> accionCoseno());
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(botonCoseno, gbc);

        JButton botonTangente = Estilo.crearBoton("tan", Estilo.BOTON_OPERACION, Estilo.TEXTO_CLARO);
        botonTangente.addActionListener(e -> accionTangente());
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(botonTangente, gbc);

        // ---- Fila 7 8 9 / C ----
        JButton boton7 = Estilo.crearBoton("7", Estilo.BOTON_NUMERO, Estilo.TEXTO_OSCURO);
        boton7.addActionListener(e -> escribirDigito("7"));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(boton7, gbc);

        JButton boton8 = Estilo.crearBoton("8", Estilo.BOTON_NUMERO, Estilo.TEXTO_OSCURO);
        boton8.addActionListener(e -> escribirDigito("8"));
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(boton8, gbc);

        JButton boton9 = Estilo.crearBoton("9", Estilo.BOTON_NUMERO, Estilo.TEXTO_OSCURO);
        boton9.addActionListener(e -> escribirDigito("9"));
        gbc.gridx = 2;
        gbc.gridy = 4;
        add(boton9, gbc);

        JButton botonDividir = Estilo.crearBoton("/", Estilo.BOTON_OPERACION, Estilo.TEXTO_CLARO);
        botonDividir.addActionListener(e -> accionDividir());
        gbc.gridx = 3;
        gbc.gridy = 4;
        add(botonDividir, gbc);

        JButton botonLimpiar = Estilo.crearBoton("C", Estilo.BOTON_OPERACION, Estilo.TEXTO_CLARO);
        botonLimpiar.addActionListener(e -> accionLimpiar());
        gbc.gridx = 4;
        gbc.gridy = 4;
        add(botonLimpiar, gbc);

        // ---- Fila 4 5 6 * <- ----
        JButton boton4 = Estilo.crearBoton("4", Estilo.BOTON_NUMERO, Estilo.TEXTO_OSCURO);
        boton4.addActionListener(e -> escribirDigito("4"));
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(boton4, gbc);

        JButton boton5 = Estilo.crearBoton("5", Estilo.BOTON_NUMERO, Estilo.TEXTO_OSCURO);
        boton5.addActionListener(e -> escribirDigito("5"));
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(boton5, gbc);

        JButton boton6 = Estilo.crearBoton("6", Estilo.BOTON_NUMERO, Estilo.TEXTO_OSCURO);
        boton6.addActionListener(e -> escribirDigito("6"));
        gbc.gridx = 2;
        gbc.gridy = 5;
        add(boton6, gbc);

        JButton botonMultiplicar = Estilo.crearBoton("*", Estilo.BOTON_OPERACION, Estilo.TEXTO_CLARO);
        botonMultiplicar.addActionListener(e -> accionMultiplicar());
        gbc.gridx = 3;
        gbc.gridy = 5;
        add(botonMultiplicar, gbc);

        JButton botonBorrar = Estilo.crearBoton("<-", Estilo.BOTON_OPERACION, Estilo.TEXTO_CLARO);
        botonBorrar.addActionListener(e -> accionBorrar());
        gbc.gridx = 4;
        gbc.gridy = 5;
        add(botonBorrar, gbc);

        // ---- Fila 1 2 3 - pi ----
        JButton boton1 = Estilo.crearBoton("1", Estilo.BOTON_NUMERO, Estilo.TEXTO_OSCURO);
        boton1.addActionListener(e -> escribirDigito("1"));
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(boton1, gbc);

        JButton boton2 = Estilo.crearBoton("2", Estilo.BOTON_NUMERO, Estilo.TEXTO_OSCURO);
        boton2.addActionListener(e -> escribirDigito("2"));
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(boton2, gbc);

        JButton boton3 = Estilo.crearBoton("3", Estilo.BOTON_NUMERO, Estilo.TEXTO_OSCURO);
        boton3.addActionListener(e -> escribirDigito("3"));
        gbc.gridx = 2;
        gbc.gridy = 6;
        add(boton3, gbc);

        JButton botonRestar = Estilo.crearBoton("-", Estilo.BOTON_OPERACION, Estilo.TEXTO_CLARO);
        botonRestar.addActionListener(e -> accionRestar());
        gbc.gridx = 3;
        gbc.gridy = 6;
        add(botonRestar, gbc);

        JButton botonPi = Estilo.crearBoton("π", Estilo.BOTON_OPERACION, Estilo.TEXTO_CLARO);
        botonPi.addActionListener(e -> accionPi());
        gbc.gridx = 4;
        gbc.gridy = 6;
        add(botonPi, gbc);

        JButton boton0 = Estilo.crearBoton("0", Estilo.BOTON_NUMERO, Estilo.TEXTO_OSCURO);
        boton0.addActionListener(e -> escribirDigito("0"));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(boton0, gbc);

        JButton botonPunto = Estilo.crearBoton(".", Estilo.BOTON_NUMERO, Estilo.TEXTO_OSCURO);
        botonPunto.addActionListener(e -> accionPunto());
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        add(botonPunto, gbc);

        JButton botonSumar = Estilo.crearBoton("+", Estilo.BOTON_OPERACION, Estilo.TEXTO_CLARO);
        botonSumar.addActionListener(e -> accionSumar());
        gbc.gridx = 3;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(botonSumar, gbc);


        JButton botonIgual = Estilo.crearBoton("=", Estilo.BOTON_IGUAL, Estilo.TEXTO_OSCURO);
        botonIgual.addActionListener(e -> accionIgual());
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 5;
        gbc.weighty = 1.4;
        add(botonIgual, gbc);
    }


    private void accionSumar() {
        aplicarOperador("+");
    }

    private void accionRestar() {
        aplicarOperador("-");
    }

    private void accionMultiplicar() {
        aplicarOperador("*");
    }

    private void accionDividir() {
        aplicarOperador("/");
    }

    private void accionSeno() {
        aplicarTrigonometria("sin");
    }

    private void accionCoseno() {
        aplicarTrigonometria("cos");
    }

    private void accionTangente() {
        aplicarTrigonometria("tan");
    }

    private void accionPi() {
        numeroActual = String.valueOf(Math.PI);
        textoOperacion = textoOperacion + "π";
        pantallaOperacion.setText(textoOperacion);
        pantallaResultado.setText(formatear(Math.PI));
    }

    private void accionPunto() {
        if (!numeroActual.contains(".")) {
            numeroActual = numeroActual + ".";
            textoOperacion = textoOperacion + ".";
            pantallaOperacion.setText(textoOperacion);
            pantallaResultado.setText(numeroActual);
        }
    }

    private void accionBorrar() {
        if (!numeroActual.isEmpty()) {
            numeroActual = numeroActual.substring(0, numeroActual.length() - 1);
        }
        if (!textoOperacion.isEmpty()) {
            textoOperacion = textoOperacion.substring(0, textoOperacion.length() - 1);
        }
        pantallaOperacion.setText(textoOperacion.isEmpty() ? "0" : textoOperacion);
        pantallaResultado.setText(numeroActual.isEmpty() ? "0" : numeroActual);
    }

    private void accionLimpiar() {
        resultado = 0;
        operadorPendiente = null;
        numeroActual = "";
        textoOperacion = "";
        pantallaOperacion.setText("0");
        pantallaResultado.setText("0");
    }

    private void accionIgual() {
        if (numeroActual.isEmpty()) {
            return;
        }
        double valor = Double.parseDouble(numeroActual);
        if (operadorPendiente != null) {
            resultado = calcular(resultado, valor, operadorPendiente);
        } else {
            resultado = valor;
        }
        pantallaResultado.setText(formatear(resultado));

        operadorPendiente = null;
        numeroActual = "";
        textoOperacion = "";
        pantallaOperacion.setText("0");
    }

    private void escribirDigito(String digito) {
        numeroActual = numeroActual + digito;
        textoOperacion = textoOperacion + digito;
        pantallaOperacion.setText(textoOperacion);
        pantallaResultado.setText(numeroActual);
    }

    private void aplicarOperador(String simbolo) {
        if (!numeroActual.isEmpty()) {
            double valor = Double.parseDouble(numeroActual);
            if (operadorPendiente != null) {
                resultado = calcular(resultado, valor, operadorPendiente);
            } else {
                resultado = valor;
            }
            numeroActual = "";
        }
        operadorPendiente = simbolo;
        textoOperacion = textoOperacion + simbolo;
        pantallaResultado.setText(formatear(resultado));
        pantallaOperacion.setText(textoOperacion);
    }

    private double calcular(double a, double b, String simbolo) {
        if (simbolo.equals("+")) {
            return a + b;
        } else if (simbolo.equals("-")) {
            return a - b;
        } else if (simbolo.equals("*")) {
            return a * b;
        } else {
            return a / b;
        }
    }

    private void aplicarTrigonometria(String funcion) {
        double valor = numeroActual.isEmpty() ? resultado : Double.parseDouble(numeroActual);
        double angulo = radioGrados.isSelected() ? Math.toRadians(valor) : valor;

        double resultadoFuncion;
        if (funcion.equals("sin")) {
            resultadoFuncion = Math.sin(angulo);
        } else if (funcion.equals("cos")) {
            resultadoFuncion = Math.cos(angulo);
        } else {
            resultadoFuncion = Math.tan(angulo);
        }

        textoOperacion = textoOperacion + funcion + "(" + formatear(valor) + ")";
        numeroActual = String.valueOf(resultadoFuncion);
        pantallaOperacion.setText(textoOperacion);
        pantallaResultado.setText(formatear(resultadoFuncion));
    }

    private String formatear(double valor) {
        if (Double.isNaN(valor) || Double.isInfinite(valor)) {
            return "Error";
        }
        if (Math.abs(valor - Math.rint(valor)) < 1e-9) {
            return String.valueOf((long) Math.rint(valor));
        }
        return String.valueOf(Math.round(valor * 1e10) / 1e10);
    }

    public static void main(String[] args) {
        Calculadora ventana = new Calculadora();
        ventana.setVisible(true);
    }
}
