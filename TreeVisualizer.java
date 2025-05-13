package morsecoder;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import java.util.Scanner;

public class TreeVisualizer extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Codificador/Decodificador Morse com Visualização de Árvore");

        MorseBST bst = new MorseBST();
        populateMorseTree(bst);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Árvore Morse construída e populada.");

        boolean continuar = true;
        while(continuar) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Codificar Texto para Morse");
            System.out.println("2. Decodificar Morse para Texto");
            System.out.println("3. Apenas Visualizar a Árvore e Sair do Console");
            System.out.println("4. Sair do Programa");
            System.out.print("Opção: ");
            String escolha = scanner.nextLine();

            switch (escolha) {
                case "1":
                    System.out.print("Digite o texto para codificar: ");
                    String textoInput = scanner.nextLine();
                    String morseCodificado = bst.codificar(textoInput);
                    System.out.println("Texto Codificado em Morse: " + morseCodificado);
                    break;
                case "2":
                    System.out.print("Digite a sequência Morse para decodificar (letras separadas por espaço, palavras por ' / '): ");
                    String morseInput = scanner.nextLine();
                    String textoDecodificado = bst.decode(morseInput);
                    System.out.println("Texto Decodificado: " + textoDecodificado);
                    break;
                case "3":
                    System.out.println("Visualizando a árvore...");
                    continuar = false;
                    break;
                case "4":
                    System.out.println("Saindo do programa.");
                    scanner.close();
                    primaryStage.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

            if (continuar && !escolha.equals("3")) {
                System.out.print("Deseja realizar outra operação de console? (s/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("s")) {
                    System.out.println("Prosseguindo para visualização da árvore e encerramento do console.");
                    continuar = false;
                }
            }
        }
        scanner.close();

        final double canvasWidth = 1920;
        final double canvasHeight = 1080;

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        bst.drawTree(canvas);

        Group rootGroup = new Group();
        rootGroup.getChildren().add(canvas);

        Scene scene = new Scene(rootGroup, canvasWidth, canvasHeight);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void populateMorseTree(MorseBST bst) {
        bst.insert('A', ".-"); bst.insert('B', "-..."); bst.insert('C', "-.-.");
        bst.insert('D', "-.."); bst.insert('E', ".");    bst.insert('F', "..-.");
        bst.insert('G', "--."); bst.insert('H', "...."); bst.insert('I', "..");
        bst.insert('J', ".---"); bst.insert('K', "-.-");  bst.insert('L', ".-..");
        bst.insert('M', "--");   bst.insert('N', "-.");   bst.insert('O', "---");
        bst.insert('P', ".--."); bst.insert('Q', "--.-"); bst.insert('R', ".-.");
        bst.insert('S', "...");  bst.insert('T', "-");    bst.insert('U', "..-");
        bst.insert('V', "...-"); bst.insert('W', ".--");  bst.insert('X', "-..-");
        bst.insert('Y', "-.--"); bst.insert('Z', "--..");
        bst.insert('0', "-----"); bst.insert('1', ".----"); bst.insert('2', "..---");
        bst.insert('3', "...--"); bst.insert('4', "....-"); bst.insert('5', ".....");
        bst.insert('6', "-...."); bst.insert('7', "--..."); bst.insert('8', "---..");
        bst.insert('9', "----.");
        bst.insert('.', ".-.-.-"); bst.insert(',', "--..--"); bst.insert('?', "..--..");
        bst.insert('!', "-.-.--"); bst.insert('/', "-..-.");  bst.insert('(', "-.--.");
        bst.insert(')', "-.--.-"); bst.insert('&', ".-...");  bst.insert(':', "---...");
        bst.insert(';', "-.-.-."); bst.insert('=', "-...-");  bst.insert('+', ".-.-.");
        bst.insert('-', "-....-"); bst.insert('_', "..--.-"); bst.insert('"', ".-..-.");
        bst.insert('@', ".--.-.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
