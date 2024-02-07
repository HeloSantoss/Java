
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SistemaElevador extends JFrame {

    private JButton[] botoesAndares;
    private JLabel[] indicadoresElevadores;
    private int numeroAndares = 6;
    private int numeroElevadores = 2;
    private int[] elevadores;

    public SistemaElevador() {
        super("Sistema de Elevadores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Altera o layout para BoxLayout com orientação vertical
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        elevadores = new int[numeroElevadores];

        indicadoresElevadores = new JLabel[numeroElevadores];
        for (int i = 0; i < numeroElevadores; i++) {
            indicadoresElevadores[i] = new JLabel("E" + (i + 1) + ": ");
            add(indicadoresElevadores[i]);
        }

        botoesAndares = new JButton[numeroAndares];
        for (int i = 0; i < numeroAndares; i++) {
            final int andar = i + 1;

            // Adiciona um botão de andar com uma imagem GIF
            ImageIcon gifIcon = new ImageIcon("img/elevador.gif");

            JButton botaoAndar = new JButton(new ImageIcon(gifIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)));

            botaoAndar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chamarElevador(andar);
                }
            });

            add(botaoAndar);
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void chamarElevador(int andarChamado) {
        int elevadorMaisProximo = encontrarElevadorMaisProximo(andarChamado);

        // Simulação do elevador se movendo até o andar chamado
        System.out.println("Elevador " + (elevadorMaisProximo + 1) + " a caminho do Andar " + andarChamado);

        // Atualiza o indicador do elevador
        elevadores[elevadorMaisProximo] = andarChamado;
        indicadoresElevadores[elevadorMaisProximo].setText("E" + (elevadorMaisProximo + 1) + ": Andar " + andarChamado);
    }

    private int encontrarElevadorMaisProximo(int andarChamado) {
        int elevadorMaisProximo = 0;
        int distanciaElevadorAtual = Math.abs(andarChamado - elevadores[0]);

        for (int i = 1; i < numeroElevadores; i++) {
            int distanciaElevadorI = Math.abs(andarChamado - elevadores[i]);

            if (distanciaElevadorI < distanciaElevadorAtual) {
                elevadorMaisProximo = i;
                distanciaElevadorAtual = distanciaElevadorI;
            }
        }

        return elevadorMaisProximo;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemaElevador());
    }
}
