/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.fluttercode.datafactory.impl.DataFactory;

/**
 *
 * @author ayoro
 */
public class ManejoArchivosClase {
    RandomAccessFile raf;
    RandomAccessFile rafTree;
    DataFactory df;

    public ManejoArchivosClase() throws FileNotFoundException, IOException {
        this.raf = new RandomAccessFile("clase.txt", "rw"); //Maneja el archivo profesor.txt
        this.raf.seek(0);
        for (int i = 0; i < 200; i = i+2) {
            this.raf.writeChar('\u0000');
        }
        this.rafTree = new RandomAccessFile("clase.txt", "rw");//Maneja el árbol en el archivo profesor.txt
        this.df = new DataFactory();
    }
    
    public void crearArchivoClase(int id, String name, int idProfe, long posProfe) throws IOException{
        raf.seek(raf.length());//Salta en el archivo hasta la última posición
        long posición = raf.getFilePointer();
        raf.writeInt(id);//Mete el ID en el archivo
        char nombre [] = name.toCharArray(); //Cambia de String a un arreglo de chars
        
        //Mete el nombre en el archivo con los espacios adicionales para completar el tamaño
        for (int i = 0; i < nombre.length; i++) {
            raf.writeChar(nombre[i]);
        }
        for (int i = nombre.length; i < 20; i++) {
            raf.writeChar('\u0000');
        }
        
        raf.writeInt(idProfe);
        raf.writeLong(posProfe);
        
        agregarNodoArbol(0, id, posición); //Agrego el nodo en el árbol
    }
    
    public int leerEntero(long posByte) throws IOException{
        raf.seek(posByte); //Salta a la posición a leer
        return raf.readInt(); //retorna el dato leído
    }
    
    public char [] leerChars(long posByte) throws IOException{
        char var [] = new char [20]; //Crea un nuevo vector para colocar las letras de la palabra
        raf.seek(posByte); //Salta a la posición a leer
        for (int i = 0; i < 20; i++) { //lee 20 veces una variable de tipo char
            var [i] = raf.readChar();
        }
        return var;
    }
    
    public long getTamanoLista() throws IOException{
        return this.raf.length(); //Retorna el tamaño del archivo
    }
    
    public void agregarNodoArbol(int raizP, int id, long bytPos) throws IOException{ // Ojo con raizP 
        rafTree.seek(raizP); // Me coloco en la posición donde voy a empezar a escribir
        if (this.rafTree.length()==0 || this.rafTree.readChar() == '\u0000') {
            rafTree.seek(raizP); // Me coloco en la posición donde voy a empezar a escribir
            this.rafTree.writeInt(id);// Se coloca el id en la primera posición
            this.rafTree.writeInt(-1);// Si se tiene -1 en algún campo significa vacío
            this.rafTree.writeInt(-1);// En este caso todo es vacío porque es la primera que se pone
            this.rafTree.writeLong(-1);
            this.rafTree.writeLong(-1);
        }else{
            long pos = rafTree.getFilePointer(); //Se obtiene la posición del raf para usar como referencia
            int raiz = rafTree.readInt(); // Se lee el número a comparar para saber en dónde se pone
            if (id > raiz) { 
                rafTree.seek(pos + 8); // Voy a la posición del campo donde está el ID de la derecha
                int posDer = rafTree.readInt(); // Leo el número para saber si está vacío o si no y lo guardo
                if (posDer == -1) { // Si está vacío
                    rafTree.seek(pos + 8); // Vuelvo a la posición del ID de la derecha
                    rafTree.writeInt(id); // Escribo el ID
                    rafTree.seek(pos + 20); // Salto hacia donde se encuentra el campo con el # de byte derecha
                    rafTree.writeLong(bytPos); // Escribo la posición correspondiente en el archivo
                }else{
                    if (posDer > id) { 
                        rafTree.seek(pos + 8); // Salto a la posición donde se encuentra el ID de la derecha
                        rafTree.writeInt(id); // Sobreescribo el ID
                        rafTree.seek(pos + 20); // Salto a la posición de la posición en bytes de ID derecha
                        long posByteDer = rafTree.readLong(); //Guardo la posición
                        rafTree.seek(pos + 20); //Vuelvo a la misma posición
                        rafTree.writeLong(bytPos); //Sobreescribo la posición
                        agregarNodoArbol(raizP+28, posDer, posByteDer); //Vuelvo a comenzar pero con las dos que estaban antes
                    }else if(posDer < id){
                        agregarNodoArbol(raizP+28, id, bytPos); // Vuelvo a comenzar con las mismas variable
                    }
                }
            }else if(id < raiz){
                rafTree.seek(pos + 4); // Voy a la posición del campo donde está el ID de la izquierda
                int posIzq = rafTree.readInt(); // Leo el número para saber si está vacío o si no y lo guardo
                if (posIzq == -1) { // Si está vacío
                    rafTree.seek(pos + 4); // Vuelvo a la posición del ID de la izquierda
                    rafTree.writeInt(id); // Escribo el ID
                    rafTree.seek(pos + 12); // Salto hacia donde se encuentra el campo con el # de byte izquierda
                    rafTree.writeLong(bytPos); //Escribo la posición correspondiente en el archivo
                }else{
                    if (posIzq < id) {
                        rafTree.seek(pos + 4); // Salto a la posición donde se encuentra el ID de la izquierda
                        rafTree.writeInt(id); // Sobreescribo el ID
                        rafTree.seek(pos + 12); // Salto a la posición de la posición en bytes de ID izquierda
                        long posByteIzq = rafTree.readLong(); // Guardo la posición
                        rafTree.seek(pos + 12); // Vuelvo a la misma posición
                        rafTree.writeLong(bytPos); //Sobreescribo la posición
                        agregarNodoArbol(raizP+28, posIzq, posByteIzq); // Vuelvo a comenzar pero con las dos que estaban antes
                    }else if(posIzq > id){
                        agregarNodoArbol(raizP+28, id, bytPos); // Vuelvo a comenzar con las mismas variable
                    }
                }
            }
        }
    }
}
