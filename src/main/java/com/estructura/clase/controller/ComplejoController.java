package com.estructura.clase.controller;


import com.estructura.clase.base.models.Complejo;
import java.util.Random;

public class ComplejoController {
    private static final int FILAS = 5;
    private static final int COLUMNAS = 3;
    private static final Random random = new Random();

    private Complejo[][] matriz;

    // Genera un número aleatorio entre 0 y 100
    private float getRandomNumber() {
        return random.nextFloat() * 100;
    }

    // Crea un nuevo número complejo aleatorio
    private Complejo createRandomComplejo() {
        return new Complejo(getRandomNumber(), getRandomNumber());
    }

    public Complejo[][] getComplejo() {
        if (matriz == null) {
            matriz = new Complejo[FILAS][COLUMNAS];
            for (int i = 0; i < FILAS; i++) {
                matriz[i][0] = createRandomComplejo();
                matriz[i][1] = createRandomComplejo();
                matriz[i][2] = new Complejo();
            }
        }
        return matriz;
    }

    // Operaciones básicas con números complejos
    public Complejo sumar(Complejo a, Complejo b) {
        return new Complejo(
            a.getNum() + b.getNum(),
            a.getImag() + b.getImag()
        );
    }

    public Complejo restar(Complejo a, Complejo b) {
        return new Complejo(
            a.getNum() - b.getNum(),
            a.getImag() - b.getImag()
        );
    }

    public Complejo multiplicar(Complejo a, Complejo b) {
        float real = a.getNum() * b.getNum() - a.getImag() * b.getImag();
        float imag = a.getNum() * b.getImag() + a.getImag() * b.getNum();
        return new Complejo(real, imag);
    }

    public Complejo dividir(Complejo a, Complejo b) {
        float denom = b.getNum() * b.getNum() + b.getImag() * b.getImag();
        if (denom == 0) {
            throw new ArithmeticException("No se puede dividir por cero");
        }

        float real = (a.getNum() * b.getNum() + a.getImag() * b.getImag()) / denom;
        float imag = (a.getImag() * b.getNum() - a.getNum() * b.getImag()) / denom;
        return new Complejo(real, imag);
    }

    // Realiza todas las operaciones para la matriz
    public Complejo[] operar() {
        Complejo[] resultados = new Complejo[FILAS * 4];
        Complejo[][] matriz = getComplejo();
        int index = 0;

        for (int i = 0; i < FILAS; i++) {
            Complejo num1 = matriz[i][0];
            Complejo num2 = matriz[i][1];

            resultados[index++] = sumar(num1, num2);
            resultados[index++] = restar(num1, num2);
            resultados[index++] = multiplicar(num1, num2);
            resultados[index++] = dividir(num1, num2);
        }

        return resultados;
    }
}