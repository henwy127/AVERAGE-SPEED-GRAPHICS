package Interface;

import java.util.ArrayList;

public class Funcionamento {
    public static double calcularMediaAritmetica(ArrayList<String> valores) {
        double soma = 0;
        int n = valores.size();
        for (String val : valores) {
            soma += Double.parseDouble(val.replace(",", "."));
        }
        return soma / n;
    }

    public static double calcularMediaHarmonica(ArrayList<String> valores) {
        double somaInversos = 0;
        int n = valores.size();
        for (String val : valores) {
            somaInversos += 1.0 / Double.parseDouble(val.replace(",", "."));
        }
        return n / somaInversos;
    }

    public static String resultado(ArrayList<String> ida, ArrayList<String> volta) {
        double mediaIda = calcularMediaAritmetica(ida);
        double mediaVolta = calcularMediaHarmonica(volta);
        double mediaTotal = 2 * mediaIda * mediaVolta / (mediaIda + mediaVolta);
        return "<html>Velocidade média na Ida (tempo igual): " + String.format("%.2f", mediaIda) + " km/h<br>"
                + "Velocidade média na Volta (distância igual): " + String.format("%.2f", mediaVolta) + " km/h<br>"
                + "Velocidade média Total: " + String.format("%.2f", mediaTotal) + " km/h</html>";
    }
}