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

    
}
