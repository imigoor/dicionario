package projeto_dicionario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class TelaDicionario extends JFrame {
    private Dicionario dicionario;
    private JTextField textFieldTermo;
    private JTextArea textAreaResultado;
    private JComboBox<String> comboIdiomas;

    public TelaDicionario() {
        setTitle("Dicionário TRAIN");
        setLayout(null);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel labelTermo = new JLabel("Digite a palavra:");
        labelTermo.setBounds(175, 20, 100, 25);
        add(labelTermo);

        textFieldTermo = new JTextField();
        textFieldTermo.setBounds(275, 20, 160, 25);
        add(textFieldTermo);

        JButton botaoTraduzir = new JButton("Traduzir");
        botaoTraduzir.setBounds(150, 60, 100, 25);
        add(botaoTraduzir);

        JButton botaoTraduzirDoPortugues = new JButton("Traduzir do Português");
        botaoTraduzirDoPortugues.setBounds(260, 60, 200, 25);
        add(botaoTraduzirDoPortugues);

        JButton botaoLocalizar = new JButton("Localizar");
        botaoLocalizar.setBounds(150, 100, 100, 25);
        add(botaoLocalizar);

        JButton botaoLocalizarPortugues = new JButton("Localizar do Português");
        botaoLocalizarPortugues.setBounds(260, 100, 200, 25);
        add(botaoLocalizarPortugues);

        textAreaResultado = new JTextArea();
        textAreaResultado.setEditable(false);
        textAreaResultado.setLineWrap(true);
        textAreaResultado.setWrapStyleWord(true);
        textAreaResultado.setFont(new Font("Arial", Font.PLAIN, 14));
        textAreaResultado.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textAreaResultado);
        scrollPane.setBounds(20, 140, 545, 150);
        add(scrollPane);

        JLabel labelWarningNotification = new JLabel("", SwingConstants.CENTER);
        labelWarningNotification.setBounds(30, 310, 520, 25);
        labelWarningNotification.setFont(new Font("Arial", Font.BOLD, 12));
        add(labelWarningNotification);

        comboIdiomas = new JComboBox<>();
        comboIdiomas.setBounds(20, 295, 150, 25);
        add(comboIdiomas);

        JLabel labelMensagem = new JLabel("Idioma atual:");
        labelMensagem.setBounds(410, 310, 100, 25);
        add(labelMensagem);

        JLabel labelBandeira = new JLabel();
        labelBandeira.setBounds(490, 310, 60, 30);
        labelBandeira.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelBandeira);

        try {
            dicionario = new Dicionario("ingles");

            atualizarBandeira("ingles", labelBandeira);

            for (String idioma : dicionario.getIdiomas()) {
                comboIdiomas.addItem(idioma);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dicionário: " + e.getMessage());
        }

        botaoTraduzir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String termo = textFieldTermo.getText();
                if (termo.isEmpty()) {
                    labelWarningNotification.setForeground(Color.RED);
                    labelWarningNotification.setText("Digite uma palavra!");
                    return;
                }

                ArrayList<String> resultados = dicionario.traduzirParaPortugues(termo);

                if (!resultados.isEmpty()) {
                    textAreaResultado.setText("Resultado(s):\n" + String.join("\n", resultados));
                    labelWarningNotification.setForeground(Color.BLACK);
                    labelWarningNotification.setText("Tradução encontrada com sucesso!");
                } else {
                        textAreaResultado.setText("Nenhum resultado encontrado.");
                        labelWarningNotification.setForeground(Color.RED);
                        labelWarningNotification.setText("Erro: Nenhuma tradução foi encontrada.");
                }
            }
        });

        botaoTraduzirDoPortugues.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String termo = textFieldTermo.getText();

                // Verifica se o campo de texto está vazio
                if (termo.isEmpty()) {
                    labelWarningNotification.setForeground(Color.RED);
                    labelWarningNotification.setText("Digite uma palavra!");
                    return;
                }

                // Traduz do português para o idioma selecionado
                ArrayList<String> resultados = dicionario.traduzirParaIdioma(termo);

                // Verifica se a lista de resultados não está vazia
                if (!resultados.isEmpty()) {
                    textAreaResultado.setText("Resultado(s):\n" + String.join("\n", resultados));
                    labelWarningNotification.setForeground(Color.BLACK);
                    labelWarningNotification.setText("Tradução do português para o idioma selecionado encontrada!");
                } else {
                    textAreaResultado.setText("Nenhum resultado encontrado.");
                    labelWarningNotification.setForeground(Color.RED);
                    labelWarningNotification.setText("Erro: Nenhuma tradução foi encontrada.");
                }
            }
        });

        botaoLocalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String termo = textFieldTermo.getText();
                if (termo.isEmpty()) {
                    labelWarningNotification.setForeground(Color.RED);
                    labelWarningNotification.setText("Digite uma palavra!");
                    return;
                }
                ArrayList<String> resultados = dicionario.localizarPalavraIdioma(termo);

                if (!resultados.isEmpty()) {
                    textAreaResultado.setText("Resultado(s):\n" + String.join("\n", resultados));
                    labelWarningNotification.setForeground(Color.BLACK);
                    labelWarningNotification.setText("Palavras localizadas no idioma atual!");
                } else {
                    textAreaResultado.setText("Nenhum resultado encontrado.");
                    labelWarningNotification.setForeground(Color.RED);
                    labelWarningNotification.setText("Erro: Nenhuma palavra localizada.");
                }
            }
        });

        botaoLocalizarPortugues.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String termo = textFieldTermo.getText();
                if (termo.isEmpty()) {
                    labelWarningNotification.setForeground(Color.RED);
                    labelWarningNotification.setText("Digite uma palavra!");
                    return;
                }

                ArrayList<String> resultados = dicionario.localizarPalavraPortugues(termo);

                if (!resultados.isEmpty()) {
                    textAreaResultado.setText("Resultado(s):\n" + String.join("\n", resultados));
                    labelWarningNotification.setForeground(Color.BLACK);
                    labelWarningNotification.setText("Palavras localizadas em português!");
                } else {
                    textAreaResultado.setText("Nenhum resultado encontrado.");
                    labelWarningNotification.setForeground(Color.RED);
                    labelWarningNotification.setText("Erro: Nenhuma palavra localizada.");
                }
            }
        });

        comboIdiomas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idiomaSelecionado = comboIdiomas.getSelectedItem().toString();
                    dicionario.setIdioma(idiomaSelecionado);
                    atualizarBandeira(idiomaSelecionado, labelBandeira);
                    textAreaResultado.setText("Idioma alterado para: " + idiomaSelecionado);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao mudar idioma: " + ex.getMessage());
                }
            }
        });

        setVisible(true);
    }

    private void atualizarBandeira(String idioma, JLabel labelBandeira) {
        ImageIcon bandeira = new ImageIcon(Objects.requireNonNull(getClass().getResource(String.format("/imagens/%s.png", idioma))));
        labelBandeira.setIcon(new ImageIcon(bandeira.getImage().getScaledInstance(60, 30, Image.SCALE_DEFAULT)));
    }

    public static void main(String[] args) {
        new TelaDicionario();
    }
}
