/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author ayoro
 */
public class ManejoArchivosInscripciones {
    private RandomAccessFile raf;
    private RandomAccessFile rafTree;
    private int cont;
    private int pk;
    private static ManejoArchivosInscripciones manejoArchivosInscripciones;
    
    private ManejoArchivosInscripciones() throws FileNotFoundException, IOException{
        this.raf = new RandomAccessFile("prueba3", "rw");
        this.raf.seek(0);
        this.rafTree = new RandomAccessFile("prueba3", "rw");
        this.raf.writeChar('@');
        for (int i = 2; i < 360; i = i + 2) {
            this.raf.writeChar('\u0000');
        }
        this.cont = 1;
        this.pk = 1;
    }
    
    public static ManejoArchivosInscripciones getManejoArchivosInscripciones() throws IOException{
        if (manejoArchivosInscripciones == null) {
            manejoArchivosInscripciones = new ManejoArchivosInscripciones();
        }
        return manejoArchivosInscripciones;
    }
    
    public void crearArchivoInscripciones(int idEst, int idCurso, String fechaIns, String fechaFin, double nota, long posIdEst, long posIdCurso) throws IOException{
        this.raf.seek(this.raf.length());//Salta en el archivo hasta la última posición
        long posición = this.raf.getFilePointer();
        this.raf.writeInt(pk);//PK artificial
        this.raf.writeInt(idEst);//Mete el ID en el archivo
        this.raf.writeInt(idCurso);
        
        char fechaInscrip [] = fechaIns.toCharArray(); //Cambia de String a un arreglo de chars
        char fechaFinal [] = fechaFin.toCharArray();
        //Mete el nombre en el archivo con los espacios adicionales para completar el tamaño
        for (int i = 0; i < fechaInscrip.length; i++) {
            this.raf.writeChar(fechaInscrip[i]);
        }
        for (int i = fechaInscrip.length; i < 20; i++) {
            this.raf.writeChar('\u0000');
        }
        
        for (int i = 0; i < fechaFinal.length; i++) {
            this.raf.writeChar(fechaFinal[i]);
        }
        for (int i = fechaFinal.length; i < 20; i++) {
            this.raf.writeChar('\u0000');
        }
        
        this.raf.writeDouble(nota);
        
        arbol(idEst, idCurso, posición);
        this.pk++;
    }
    
    public int leerEntero(long posByte) throws IOException {
        raf.seek(posByte); //Salta a la posición a leer
        return raf.readInt(); //retorna el dato leído
    }

    public char[] leerChars(long posByte) throws IOException {
        char var[] = new char[20]; //Crea un nuevo vector para colocar las letras de la palabra
        raf.seek(posByte); //Salta a la posición a leer
        for (int i = 0; i < 20; i++) { //lee 20 veces una variable de tipo char
            var[i] = raf.readChar();
        }
        return var;
    }

    public long getTamanoLista() throws IOException {
        return this.raf.length(); //Retorna el tamaño del archivo
    }

    public void arbol(int idEst, int idCurso, long posIdEst) throws FileNotFoundException, IOException {
        rafTree.seek(0);
        boolean flag = false;
        
        while (!flag) {
            System.out.println("id: "+idEst);
            long posicion = this.rafTree.getFilePointer();
            System.out.println(posicion);
            if (rafTree.readChar() == '@') {
                this.rafTree.seek(0);
                this.rafTree.writeInt(pk);
                this.rafTree.writeInt(idEst);
                this.rafTree.writeInt(idCurso);
                this.rafTree.writeLong(-1);
                this.rafTree.writeLong(-1);
                this.rafTree.writeLong(posIdEst);
                System.out.println("se puso el primero");
                flag = true;
            } else {
                int actual = this.rafTree.readInt();
                int pruebaActual = ((actual-131071)/65536)+1;
                System.out.println("ID comparar "+pruebaActual);
                System.out.println("ID actual: "+actual);
                this.rafTree.seek(posicion);
                if (pruebaActual < idEst) {
                    System.out.println("Entró primer condicional (El número metido es mayor que el que estaba)");
                    this.rafTree.seek(posicion + 20);
                    long derPos = this.rafTree.readLong();
                    this.rafTree.seek(posicion + 20);
                    System.out.println("cont = "+cont);
                    if (derPos == -1) {
                        this.rafTree.writeLong(cont * 36);
                        this.rafTree.seek(cont * 36);
                        this.rafTree.writeInt(pk);
                        this.rafTree.writeInt(idEst);
                        this.rafTree.writeInt(idCurso);
                        this.rafTree.writeLong(-1);
                        this.rafTree.writeLong(-1);
                        this.rafTree.writeLong(posIdEst);
                        flag = true;
                        cont++;
                        System.out.println("se añadio 1");
                    } else {
                        System.out.println("se fue a la pos: "+derPos);
                        //arbol(derPos, id, pos, cont);
                        this.rafTree.seek(derPos);
                    }
                }else{
                    System.out.println("Entró segundo condicional");
                    this.rafTree.seek(posicion + 12);
                    long izqPos = this.rafTree.readLong();
                    this.rafTree.seek(posicion + 12);
                    if (izqPos == -1) {
                        this.rafTree.writeLong(cont * 36);
                        this.rafTree.seek(cont * 36);
                        this.rafTree.writeInt(pk);
                        this.rafTree.writeInt(idEst);
                        this.rafTree.writeInt(idCurso);
                        this.rafTree.writeLong(-1);
                        this.rafTree.writeLong(-1);
                        this.rafTree.writeLong(posIdEst);
                        flag = true;
                        cont++;
                        System.out.println("se añadio 2");
                    } else {
                        //arbol(izqPos, id, pos, cont);
                        this.rafTree.seek(izqPos);
                    }
                }
            }
        }
        
    }
    
    public int [] buscarEstudiante(int idCurso) throws IOException{
        int tamano = 0;
        int contador = 0;
        
        for (int i = 0; i < 360; i+=36) {
            this.rafTree.seek(i+8);
            if (this.rafTree.readInt() == idCurso) {
                tamano++;
            }
        }
        
        int estudiantes [] = new int[tamano];
        
        for (int i = 0; i < 360; i+=36) {
            this.rafTree.seek(i+8);
            if (this.rafTree.readInt() == idCurso) {
                this.rafTree.seek(i+4);
                int est = this.rafTree.readInt();
                estudiantes[contador] = est;
                contador++;
            }
        }
        return estudiantes;
    }
}
