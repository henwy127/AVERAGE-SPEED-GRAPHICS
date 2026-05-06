package Interface;

import java.util.ArrayList;

public class Funcionamento {

    /**
     * Calcula a média aritmética dos valores recebidos.
     * Usada para a viagem de ida, considerando que cada parte tem a mesma duração (tempo igual).
     *
     * @param valores Lista de strings representando as velocidades.
     * @return Média aritmética das velocidades.
     */
    public static double calcularMediaAritmetica(ArrayList<String> valores) {
        double soma = 0;
        int n = valores.size();
        for (String val : valores) {
            // Substitui vírgula por ponto para aceitar ambos e converte para double
            soma += Double.parseDouble(val.replace(",", "."));
        }
        return soma / n;
    }

    /**
     * Calcula a média harmônica dos valores recebidos.
     * Usada para a viagem de volta, considerando que cada parte tem a mesma distância (distância igual).
     *
     * @param valores Lista de strings representando as velocidades.
     * @return Média harmônica das velocidades.
     */
    public static double calcularMediaHarmonica(ArrayList<String> valores) {
        double somaInversos = 0;
        int n = valores.size();
        for (String val : valores) {
            somaInversos += 1.0 / Double.parseDouble(val.replace(",", "."));
        }
        return n / somaInversos;
    }

    /**
     * Retorna um resumo HTML das médias de velocidades:
     * - Média aritmética da ida (tempo igual)
     * - Média harmônica da volta (distância igual)
     * - Média total, pelo método da média harmônica entre ida e volta
     *
     * @param ida   Lista de strings de velocidades da ida
     * @param volta Lista de strings de velocidades da volta
     * @return      Texto pronto para ser exibido na interface com resultados
     */
    public static String resultado(ArrayList<String> ida, ArrayList<String> volta) {
        double mediaIda = calcularMediaAritmetica(ida);           // Média aritmética da ida
        double mediaVolta = calcularMediaHarmonica(volta);        // Média harmônica da volta
        // Média total entre ida e volta (harmônica das duas médias)
        double mediaTotal = 2 * mediaIda * mediaVolta / (mediaIda + mediaVolta);

        // Retorna o resultado formatado em HTML (para multiline no JLabel)
        return "<html>Velocidade média na Ida (tempo igual): " + String.format("%.2f", mediaIda) + " km/h<br>"
                + "Velocidade média na Volta (distância igual): " + String.format("%.2f", mediaVolta) + " km/h<br>"
                + "Velocidade média Total: " + String.format("%.2f", mediaTotal) + " km/h</html>";
    }
}