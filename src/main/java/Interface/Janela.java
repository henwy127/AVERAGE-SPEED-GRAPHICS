package Interface;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.border.LineBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Janela {
    // Listas públicas para armazenar os campos de texto das velocidades da ida e volta
    public ArrayList<JTextField> camposIda = new ArrayList<>();
    public ArrayList<JTextField> camposVolta = new ArrayList<>();

    // Variáveis para gráfico e interface do gráfico da ida e labels de resultado
    private ChartPanel chartPanelIda = null;
    private JPanel painelGraficoIda = null;
    private JLabel labelResultado;
    private ArrayList<Double> ultimosValoresIdaValidos = new ArrayList<>();
    private JLabel labelGraficoIda;

    /**
     * Retorna a representação ordinal do número (ex: 1 = Primeira, 2 = Segunda, etc.).
     * Usada para deixar os labels dos trechos mais claros.
     */
    private String getOrdinal(int numero) {
        String[] ordinais = {
                "Primeira", "Segunda", "Terceira", "Quarta", "Quinta",
                "Sexta", "Sétima", "Oitava", "Nona", "Décima"
        };
        if (numero >= 1 && numero <= ordinais.length) {
            return ordinais[numero - 1];
        }
        return numero + "ª";
    }

    /**
     * Atualiza o gráfico da viagem de ida conforme o usuário digita nos campos.
     * Só desenha o gráfico se todos os valores estão preenchidos e são válidos.
     * Mostra os valores das velocidades em cada trecho definido.
     */
    private void atualizarGraficoIda() {
        ArrayList<String> valoresIda = getValoresIda();
        ArrayList<Double> valoresValidos = new ArrayList<>();
        boolean todosValidos = true;

        // Validação das entradas da ida
        for (int i = 0; i < valoresIda.size(); i++) {
            String txt = valoresIda.get(i).trim();
            try {
                if (txt.isEmpty()) {
                    todosValidos = false; break;
                }
                double valor = Double.parseDouble(txt.replace(",", "."));
                if (valor < 0) {
                    todosValidos = false; break;
                }
                valoresValidos.add(valor);
            } catch (Exception e) {
                todosValidos = false; break;
            }
        }

        // Só atualiza se todos são válidos
        if (todosValidos) {
            ultimosValoresIdaValidos = new ArrayList<>(valoresValidos);
        } else {
            return;
        }

        painelGraficoIda.removeAll();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < ultimosValoresIdaValidos.size(); i++) {
            dataset.addValue(ultimosValoresIdaValidos.get(i), "Velocidade", getOrdinal(i + 1));
        }
        JFreeChart chart = ChartFactory.createLineChart(
                "Velocidades - Viagem de Ida (tempo igual)",
                "Trecho", "Velocidade (km/h)", dataset
        );
        chartPanelIda = new ChartPanel(chart);
        painelGraficoIda.setLayout(new java.awt.BorderLayout());
        painelGraficoIda.add(chartPanelIda, java.awt.BorderLayout.CENTER);
        painelGraficoIda.revalidate();
        painelGraficoIda.repaint();
    }

    /**
     * Método getter para retornar os textos nos campos de ida.
     */
    public ArrayList<String> getValoresIda() {
        ArrayList<String> valores = new ArrayList<>();
        for (JTextField tf : camposIda) valores.add(tf.getText());
        return valores;
    }

    /**
     * Método getter para retornar os textos nos campos de volta.
     */
    public ArrayList<String> getValoresVolta() {
        ArrayList<String> valores = new ArrayList<>();
        for (JTextField tf : camposVolta) valores.add(tf.getText());
        return valores;
    }

    /**
     * Método principal que constrói e exibe toda a interface gráfica.
     * Cria todos os botões, campos, labels e faz toda a lógica gráfica dinâmica.
     */
    public void AbrirJanela() {
        // Criação da janela e componentes principais
        JFrame frame = new JFrame("Simulador de Velocidade Escalar Média");
        JLabel texto = new JLabel("Viagem de Ida:");
        JLabel texto3 = new JLabel("Viagem de Volta:");
        JLabel texto1 = new JLabel("Número de partes para a viagem:");
        labelGraficoIda = new JLabel("GRÁFICO DA VIAGEM DE IDA");
        labelGraficoIda.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));

        painelGraficoIda = new JPanel();
        painelGraficoIda.setLayout(null);
        frame.add(painelGraficoIda);

        frame.setLayout(null);

        texto.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18));
        texto3.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 18));
        texto1.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 15));

        String[] opcoes = {"2", "3", "4", "5", "6", "7", "8", "9", "10"};
        JComboBox<String> CaixadeOpcoes = new JComboBox<>(opcoes);

        texto.setBounds(40, 70, 300, 30);
        texto3.setBounds(570, 70, 300, 30);
        texto1.setBounds(10, 20, 300, 30);

        int xCombo = texto1.getX() + 220;
        int yCombo = texto1.getY();
        CaixadeOpcoes.setBounds(xCombo, yCombo, 100, 30);

        int margemLateral = 40;
        int espacamentoEntreQuadros = 100;
        int larguraLabel = 265;
        int espacoLabelCampo = 10;
        int larguraCampo = 120;
        int alturaCampo = 30;
        int espacamentoCampos = 36;

        int xLabelIda = margemLateral;
        int xCampoIda = xLabelIda + larguraLabel + espacoLabelCampo;
        int xLabelVolta = xCampoIda + larguraCampo + espacamentoEntreQuadros;
        int xCampoVolta = xLabelVolta + larguraLabel + espacoLabelCampo;
        int yCamposInicial = 115;

        int larguraBotao = 160;
        int alturaBotao = 40;

        // Listas locais para labels dinâmicas de ida e volta
        ArrayList<JLabel> labelsIda = new ArrayList<>();
        ArrayList<JLabel> labelsVolta = new ArrayList<>();

        // Botões principais
        JButton botao = new JButton("Reset");
        JButton botaoCalcular = new JButton("Calcular");

        // Painel para mostrar o resultado dos cálculos
        JPanel painelResultado = new JPanel();
        painelResultado.setLayout(null);
        painelResultado.setOpaque(false);
        painelResultado.setBorder(new LineBorder(new java.awt.Color(255, 44, 44), 1, false));

        labelResultado = new JLabel();
        labelResultado.setVerticalAlignment(SwingConstants.TOP);
        painelResultado.add(labelResultado);

        // Botão CALCULAR: valida, chama o método/calculo e mostra resultado
        botaoCalcular.addActionListener(e -> {
            try {
                ArrayList<String> ida = getValoresIda();
                ArrayList<String> volta = getValoresVolta();

                double minV = 0;
                double maxV = 310;

                // Validação dos valores da ida
                for (int i = 0; i < ida.size(); i++) {
                    String s = ida.get(i).replace(",", ".").trim();
                    double d;
                    try {
                        d = Double.parseDouble(s);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                                "Erro ao digitar valor na Ida (campo " + getOrdinal(i+1) + "):\n" +
                                        "Digite apenas números válidos, sem letras ou caracteres especiais.",
                                "Erro na Ida", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (d < minV) {
                        JOptionPane.showMessageDialog(null,
                                "Erro ao digitar valor na Ida (campo " + getOrdinal(i+1) + "):\n" +
                                        "Não são permitidos valores negativos.",
                                "Erro na Ida", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (d > maxV) {
                        JOptionPane.showMessageDialog(null,
                                "Erro ao digitar valor na Ida (campo " + getOrdinal(i+1) + "):\n" +
                                        "As velocidades devem estar entre " + minV + " e " + maxV + " km/h.",
                                "Erro na Ida", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Validação dos valores da volta
                for (int i = 0; i < volta.size(); i++) {
                    String s = volta.get(i).replace(",", ".").trim();
                    double d;
                    try {
                        d = Double.parseDouble(s);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,
                                "Erro ao digitar valor na Volta (campo " + getOrdinal(i+1) + "):\n" +
                                        "Digite apenas números válidos, sem letras ou caracteres especiais.",
                                "Erro na Volta", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (d < minV) {
                        JOptionPane.showMessageDialog(null,
                                "Erro ao digitar valor na Volta (campo " + getOrdinal(i+1) + "):\n" +
                                        "Não são permitidos valores negativos.",
                                "Erro na Volta", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (d > maxV) {
                        JOptionPane.showMessageDialog(null,
                                "Erro ao digitar valor na Volta (campo " + getOrdinal(i+1) + "):\n" +
                                        "As velocidades devem estar entre " + minV + " e " + maxV + " km/h.",
                                "Erro na Volta", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Se tudo válido, realiza o cálculo usando classe Funcionamento (sua lógica)
                String res = Funcionamento.resultado(ida, volta);
                labelResultado.setText(res);
            } catch (Exception ex) {
                labelResultado.setText("Erro: " + ex.getMessage());
            }
        });

        // Botão RESET: apenas limpa todos os campos da ida e volta
        botao.addActionListener(e -> {
            for (JTextField tf : camposIda) tf.setText("");
            for (JTextField tf : camposVolta) tf.setText("");
        });

        /**
         * Função dinâmica: sempre que a quantidade de partes mudar, remove (do frame) e recria todos os campos.
         * Também adiciona DocumentListener nos campos para atualizar o gráfico ao digitar.
         */
        Runnable atualizarCampos = () -> {
            for (JTextField tf : camposIda) frame.remove(tf);
            for (JTextField tf : camposVolta) frame.remove(tf);
            for (JLabel l : labelsIda) frame.remove(l);
            for (JLabel l : labelsVolta) frame.remove(l);
            frame.remove(botao);
            frame.remove(botaoCalcular);
            frame.remove(labelResultado);
            frame.remove(painelResultado);

            camposIda.clear();
            camposVolta.clear();
            labelsIda.clear();
            labelsVolta.clear();

            int qtd = Integer.parseInt((String) CaixadeOpcoes.getSelectedItem());

            int ultimoY = yCamposInicial;
            for (int i = 0; i < qtd; i++) {
                int yAtual = yCamposInicial + (i * espacamentoCampos);

                JLabel labelIda = new JLabel("Velocidade na " + getOrdinal(i+1) + " parte (Em KM/H):");
                labelIda.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
                labelIda.setBounds(xLabelIda, yAtual, larguraLabel, alturaCampo);
                labelsIda.add(labelIda);
                frame.add(labelIda);

                JTextField tfIda = new JTextField();
                tfIda.setBounds(xCampoIda, yAtual, larguraCampo, alturaCampo);
                camposIda.add(tfIda);
                frame.add(tfIda);

                // Atualiza gráfico ao digitar na ida
                tfIda.getDocument().addDocumentListener(new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) { atualizarGraficoIda(); }
                    public void removeUpdate(DocumentEvent e) { atualizarGraficoIda(); }
                    public void insertUpdate(DocumentEvent e) { atualizarGraficoIda(); }
                });

                JLabel labelVolta = new JLabel("Velocidade na " + getOrdinal(i+1) + " parte (Em KM/H):");
                labelVolta.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
                labelVolta.setBounds(xLabelVolta, yAtual, larguraLabel, alturaCampo);
                labelsVolta.add(labelVolta);
                frame.add(labelVolta);

                JTextField tfVolta = new JTextField();
                tfVolta.setBounds(xCampoVolta, yAtual, larguraCampo, alturaCampo);
                camposVolta.add(tfVolta);
                frame.add(tfVolta);

                // Atualiza gráfico ao digitar na volta (se desejar futuramente, igual ao da ida)
                tfVolta.getDocument().addDocumentListener(new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) { atualizarGraficoIda(); }
                    public void removeUpdate(DocumentEvent e) { atualizarGraficoIda(); }
                    public void insertUpdate(DocumentEvent e) { atualizarGraficoIda(); }
                });

                ultimoY = yAtual;
            }

            int yBotao = ultimoY + alturaCampo + 42;
            int xBotao = (xCampoVolta + larguraCampo / 2 + xCampoIda) / 2 - larguraBotao / 2;

            botao.setBounds(xBotao, yBotao, larguraBotao, alturaBotao);
            int xBotaoCalcular = xBotao + larguraBotao + 20;
            botaoCalcular.setBounds(xBotaoCalcular, yBotao, larguraBotao, alturaBotao);

            int yPainelGrafico = yBotao + alturaBotao + 60;
            int xPainelGrafico = margemLateral;
            painelGraficoIda.setBounds(xPainelGrafico, yPainelGrafico, 680, 250);

            int yLabelGrafico = yPainelGrafico - 40;
            int xLabelGrafico = xPainelGrafico;
            labelGraficoIda.setBounds(xLabelGrafico, yLabelGrafico, 400, 29);

            frame.add(labelGraficoIda);

            painelGraficoIda.removeAll();
            atualizarGraficoIda();
            painelGraficoIda.revalidate();
            painelGraficoIda.repaint();

            painelResultado.setBounds(xBotaoCalcular + larguraBotao + 20, yBotao, 355, 90);
            labelResultado.setBounds(10, 10, 330, 70);

            frame.add(botao);
            frame.add(botaoCalcular);
            frame.add(painelResultado);

            frame.revalidate();
            frame.repaint();
        };

        // Quando o usuário muda o número de partes, recria campos e redesenha interface
        CaixadeOpcoes.addActionListener(e -> atualizarCampos.run());

        frame.setSize(1350, 520);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(texto);
        frame.add(texto1);
        frame.add(texto3);
        frame.add(CaixadeOpcoes);

        frame.setVisible(true);

        atualizarCampos.run();
        atualizarGraficoIda();
    }
}