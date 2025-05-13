package morsecoder;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MorseBST {
    private Node root;

    public MorseBST() {
        this.root = new Node('\0');
    }

    public void insert(char letter, String morseCode) {
        Node currentNode = root;
        for (char symbol : morseCode.toCharArray()) {
            if (symbol == '.') {
                if (currentNode.left == null) {
                    currentNode.left = new Node('\0');
                }
                currentNode = currentNode.left;
            } else if (symbol == '-') {
                if (currentNode.right == null) {
                    currentNode.right = new Node('\0');
                }
                currentNode = currentNode.right;
            } else {
                System.err.println("Símbolo inválido no código Morse durante inserção: " + symbol);
                return;
            }
        }
        currentNode.letter = Character.toUpperCase(letter);
    }

    public String codificar(String texto) {
        StringBuilder codigoMorseCompleto = new StringBuilder();
        String textoUpper = texto.toUpperCase();

        for (int i = 0; i < textoUpper.length(); i++) {
            char caractere = textoUpper.charAt(i);

            if (caractere == ' ') {
                if (codigoMorseCompleto.length() > 0 && !codigoMorseCompleto.toString().endsWith(" / ")) {
                    if (codigoMorseCompleto.length() > 0 && codigoMorseCompleto.charAt(codigoMorseCompleto.length() - 1) == ' ') {
                        codigoMorseCompleto.setLength(codigoMorseCompleto.length() - 1);
                    }
                    codigoMorseCompleto.append(" / ");
                }
            } else {
                String codigoDoCaractere = encontrarCodigoMorse(this.root, caractere, "");
                if (codigoDoCaractere != null) {
                    codigoMorseCompleto.append(codigoDoCaractere);
                } else {
                    codigoMorseCompleto.append("?");
                }
                if (i < textoUpper.length() - 1 && textoUpper.charAt(i+1) != ' ') {
                    codigoMorseCompleto.append(" ");
                }
            }
        }
        if (codigoMorseCompleto.length() > 0 && codigoMorseCompleto.charAt(codigoMorseCompleto.length() - 1) == ' ') {
            codigoMorseCompleto.setLength(codigoMorseCompleto.length() - 1);
        }
        if(codigoMorseCompleto.toString().endsWith(" / ")) {
            codigoMorseCompleto.setLength(codigoMorseCompleto.length() - 3);
        }

        return codigoMorseCompleto.toString();
    }

    private String encontrarCodigoMorse(Node noAtual, char letraAlvo, String caminhoAtual) {
        if (noAtual == null) {
            return null;
        }

        if (noAtual.letter == letraAlvo && (noAtual != this.root || !caminhoAtual.isEmpty() || letraAlvo == '\0')) {
            if (noAtual == this.root && noAtual.letter == '\0' && letraAlvo == '\0' && caminhoAtual.isEmpty()) {
                return "";
            }
            if (noAtual.letter != '\0' || !caminhoAtual.isEmpty()) {
                return caminhoAtual;
            }
        }

        String resultadoEsquerda = encontrarCodigoMorse(noAtual.left, letraAlvo, caminhoAtual + ".");
        if (resultadoEsquerda != null) {
            return resultadoEsquerda;
        }

        String resultadoDireita = encontrarCodigoMorse(noAtual.right, letraAlvo, caminhoAtual + "-");
        if (resultadoDireita != null) {
            return resultadoDireita;
        }

        return null;
    }

    public String decode(String morseSequence) {
        StringBuilder decodedText = new StringBuilder();
        String[] morseWords = morseSequence.trim().split(" / ");

        for (int i = 0; i < morseWords.length; i++) {
            String morseWord = morseWords[i];
            if (morseWord.isEmpty()) continue;

            String[] morseChars = morseWord.trim().split(" ");
            for (String morseChar : morseChars) {
                if (morseChar.isEmpty()) continue;
                char decodedChar = decodeSingleChar(morseChar);
                if (decodedChar != '\0') {
                    decodedText.append(decodedChar);
                } else {
                    decodedText.append("?");
                }
            }
            if (i < morseWords.length - 1) {
                decodedText.append(" ");
            }
        }
        return decodedText.toString();
    }

    private char decodeSingleChar(String morseChar) {
        Node currentNode = root;
        for (char symbol : morseChar.toCharArray()) {
            if (symbol == '.') {
                if (currentNode.left == null) return '\0';
                currentNode = currentNode.left;
            } else if (symbol == '-') {
                if (currentNode.right == null) return '\0';
                currentNode = currentNode.right;
            } else {
                return '\0';
            }
        }
        return currentNode.letter;
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    public void drawTree(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        drawNode(gc, root, canvas.getWidth() / 2, 40, canvas.getWidth() / 4, 1);
    }

    private void drawNode(GraphicsContext gc, Node node, double x, double y, double xOffset, int level) {
        if (node == null) {
            return;
        }

        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - 15, y - 15, 30, 30);

        if (node.letter != '\0') {
            gc.setFill(Color.BLACK);
            gc.fillText(String.valueOf(node.letter), x - 5, y + 5);
        }

        double verticalSpacing = 120;

        if (node.left != null) {
            double newX = x - xOffset;
            double newY = y + verticalSpacing;
            gc.strokeLine(x, y + 15, newX, newY - 15);
            drawNode(gc, node.left, newX, newY, xOffset / 2, level + 1);
        }

        if (node.right != null) {
            double newX = x + xOffset;
            double newY = y + verticalSpacing;
            gc.strokeLine(x, y + 15, newX, newY - 15);
            drawNode(gc, node.right, newX, newY, xOffset / 2, level + 1);
        }
    }
}
