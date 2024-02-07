
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class Elevador extends JFrame {

    private JButton[] botoesAndares;
    private JButton botaoDestino;
    private final JButton[] botoesAdicionais;
    private final JLabel[] indicadoresAndares;
    private int numeroAndares = 8;
    private int elevador1 = 1;
    private int elevador2 = 8;
    private int elevadorAtual;

    public Elevador() {
        super("Elevador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        class BackgroundPanel extends JPanel {

            private Image backgroundImage;

            public BackgroundPanel() {
                backgroundImage = new ImageIcon("C:\\Users\\heloi\\OneDrive\\Área de Trabalho\\Heloisa\\Elevador-Java\\img\\sala (2).png").getImage();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }

        indicadoresAndares = new JLabel[numeroAndares];
        botoesAndares = new JButton[numeroAndares];
        botoesAdicionais = new JButton[numeroAndares];

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        setContentPane(backgroundPanel);

        JPanel elevadorPanel = new JPanel();
        elevadorPanel.setLayout(new BoxLayout(elevadorPanel, BoxLayout.Y_AXIS));

        JLabel labelElevador1 = new JLabel("Elevador 1: ");
        elevadorPanel.add(labelElevador1);

        JLabel indicadorElevador1 = new JLabel(String.valueOf(elevador1));
        elevadorPanel.add(indicadorElevador1);

        JLabel labelElevador2 = new JLabel("Elevador 2: ");
        elevadorPanel.add(labelElevador2);

        JLabel indicadorElevador2 = new JLabel(String.valueOf(elevador2));
        elevadorPanel.add(indicadorElevador2);

        BackgroundPanel containerBotoes = new BackgroundPanel();
        containerBotoes.setPreferredSize(new Dimension(100, 290));
        containerBotoes.setLayout(new BoxLayout(containerBotoes, BoxLayout.Y_AXIS));
        containerBotoes.setBorder(new LineBorder(Color.BLACK, 2));
        containerBotoes.setBackground(Color.GRAY);

        for (int i = 0; i < numeroAndares; i++) {
            int andar = 6 - i;

            if (andar >= -2 && andar <= 6) {
                botoesAndares[i] = new JButton(String.valueOf(andar));
                indicadoresAndares[i] = new JLabel(" ");
                botoesAdicionais[i] = new JButton(String.valueOf(andar)); // Tornando todos os botões com valores negativos válidos

                botoesAndares[i].setBackground(Color.GRAY);
                botoesAndares[i].setBorderPainted(false);
                botoesAdicionais[i].setBackground(Color.GRAY);
                botoesAdicionais[i].setBorderPainted(false);

                botoesAndares[i].addActionListener((ActionEvent e) -> {
                    chamarElevador(andar);
                });

                botoesAdicionais[i].addActionListener((ActionEvent e) -> {
                    chamarBotaoAdicional(andar);
                });

                JPanel pairPanel = new JPanel();
                pairPanel.setLayout(new BoxLayout(pairPanel, BoxLayout.X_AXIS));
                pairPanel.add(botoesAdicionais[i]);
                pairPanel.add(botoesAndares[i]);

                containerBotoes.add(pairPanel);
            }
        }

        elevadorPanel.add(containerBotoes);

        botaoDestino = new JButton("escolher andar") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isArmed()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(getForeground());
                g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };

        botaoDestino.setFont(new Font("Arial", Font.BOLD, 14));
        botaoDestino.setBackground(Color.BLACK);
        botaoDestino.setForeground(Color.WHITE);
        botaoDestino.setOpaque(true);
        botaoDestino.setBorderPainted(false);
        botaoDestino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                perguntarAndar();
            }
        });

        // Centraliza o botão "escolher andar"
        containerBotoes.add(Box.createVerticalGlue());
        containerBotoes.add(botaoDestino);
        containerBotoes.add(Box.createVerticalGlue());

        elevadorPanel.add(botaoDestino);

        ImageIcon gifIcon = createImageIcon("/img/marrom.png", 50, 50);
        if (gifIcon != null) {
            JPanel imagePanel = new JPanel();
            imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.X_AXIS));
            JLabel gifLabel = new JLabel(gifIcon);
            JLabel gifLabel2 = new JLabel(gifIcon);
            imagePanel.add(gifLabel);
            imagePanel.add(gifLabel2);
            containerBotoes.add(imagePanel);
        }

        add(elevadorPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void chamarElevador(int andarChamado) {
        int distanciaElevador1 = Math.abs(andarChamado - elevador1);
        int distanciaElevador2 = Math.abs(andarChamado - elevador2);

        if (distanciaElevador1 <= distanciaElevador2) {
            elevadorAtual = elevador1;
            moverElevador(elevador1, andarChamado);
        } else {
            elevadorAtual = elevador2;
            moverElevador(elevador2, andarChamado);
        }
    }

    private void moverElevador(int elevador, int andarChamado) {
        JButton[] botoesAtuais = botoesAndares;

        if (elevador != 0) {
            // Restaura a cor do botão do andar anterior
            if (6 - elevador >= 0 && 6 - elevador < numeroAndares) {
                botoesAtuais[6 - elevador].setBackground(Color.GRAY);
            }
        }

        // Destaca o botão do andar escolhido
        destacarBotaoElevador(botoesAtuais, andarChamado);

        // Atualiza o indicador do elevador
        if (elevador == elevador1) {
            indicadoresAndares[6 - andarChamado].setText(String.valueOf(elevador1));
        } else if (elevador == elevador2) {
            indicadoresAndares[6 - andarChamado].setText(String.valueOf(elevador2));
        }
    }

    private void chamarBotaoAdicional(int andar) {
        JOptionPane.showMessageDialog(this, "Botão Adicional " + andar + " clicado!");
    }

    private void perguntarAndar() {
        String andarEscolhido = JOptionPane.showInputDialog(this, "Digite o andar desejado:");
        if (andarEscolhido != null) {
            try {
                int andar = Integer.parseInt(andarEscolhido);
                if (andar >= -2 && andar <= 6) {
                    chamarElevador(andar);
                } else {
                    JOptionPane.showMessageDialog(this, "Andar inválido!");
                    // Restaura a cor do botão do andar atual para cinza
                    if (elevadorAtual != 0) {
                        botoesAndares[6 - elevadorAtual].setBackground(Color.GRAY);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Digite um número válido para o andar!");
                // Restaura a cor do botão do andar atual para cinza
                if (elevadorAtual != 0) {
                    botoesAndares[6 - elevadorAtual].setBackground(Color.GRAY);
                }
            }
        } else {
            // Usuário cancelou, restaura a cor do botão do andar atual para cinza
            if (elevadorAtual != 0) {
                botoesAndares[6 - elevadorAtual].setBackground(Color.GRAY);
            }
        }
    }

    private ImageIcon createImageIcon(String path, int width, int height) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            ImageIcon originalIcon = new ImageIcon(imgURL);
            final Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.err.println("Imagem não encontrada: " + path);
            return null;
        }
    }

    private void destacarBotaoElevador(JButton[] botoes, int andar) {
        for (int i = 0; i < botoes.length; i++) {
            int andarAtual = 6 - i;
            if (andarAtual == andar) {
                botoes[i].setBackground(Color.YELLOW);
            } else {
                botoes[i].setBackground(Color.GRAY);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Elevador());
    }
}
