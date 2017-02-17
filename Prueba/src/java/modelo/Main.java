/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;

/**
 *
 * @author black
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ManejoArchivos ma = new ManejoArchivos();
        ma.buscar(567);
    }
}
