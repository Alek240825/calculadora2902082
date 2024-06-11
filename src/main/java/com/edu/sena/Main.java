package com.edu.sena;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Calculadora");
            System.out.println("---------");
            System.out.println("1. Realizar una operación");
            System.out.println("2. Salir");
            System.out.print("Opción: ");
            int option = scanner.nextInt();

            if (option == 2) {
                System.out.println("Adiós!");
                break;
            } else if (option == 1) {
                System.out.print("Ingrese una expresión matemática: ");
                scanner.nextLine(); // Consume el salto de línea
                String expresion = scanner.nextLine();

                try {
                    double resultado = evaluarExpresion(expresion);
                    System.out.println("Resultado: " + resultado);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    public static double evaluarExpresion(String expresion) throws Exception {
        Stack<Double> valores = new Stack<>();
        Stack<Character> operadores = new Stack<>();

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            if (c == ' ') {
                continue;
            }

            if (c == '(') {
                operadores.push(c);
            } else if (c == ')') {
                while (operadores.peek() != '(') {
                    procesarOperador(valores, operadores);
                }
                operadores.pop();
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operadores.isEmpty() && getPrecedencia(operadores.peek()) >= getPrecedencia(c)) {
                    procesarOperador(valores, operadores);
                }
                operadores.push(c);
            } else {
                int startPos = i;
                while (i < expresion.length()
                        && (expresion.charAt(i) >= '0' && expresion.charAt(i) <= '9' || expresion.charAt(i) == '.')) {
                    i++;
                }
                double valor = Double.parseDouble(expresion.substring(startPos, i));
                valores.push(valor);
                i--;
            }
        }

        while (!operadores.isEmpty()) {
            procesarOperador(valores, operadores);
        }

        return valores.pop();
    }

    private static void procesarOperador(Stack<Double> valores, Stack<Character> operadores) {
        double valor2 = valores.pop();
        double valor1 = valores.pop();
        char operador = operadores.pop();

        switch (operador) {
            case '+':
                valores.push(valor1 + valor2);
                break;
            case '-':
                valores.push(valor1 - valor2);
                break;
            case '*':
                valores.push(valor1 * valor2);
                break;
            case '/':
                valores.push(valor1 / valor2);
                break;
        }
    }

    private static int getPrecedencia(char operador) {
        switch (operador) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }
}