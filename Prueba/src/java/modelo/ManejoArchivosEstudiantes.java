/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author ayoro
 */
public class ManejoArchivosEstudiantes {
    private static ManejoArchivosEstudiantes manejoArchivosEstudiantes;
    private RandomAccessFile raf;
    private RandomAccessFile rafTree;
    private int cont;
    private long finalPosT;
    
    private ManejoArchivosEstudiantes() throws FileNotFoundException, IOException{
        this.raf = new RandomAccessFile("prueeba3.txt", "rw"); //Maneja el archivo profesor.txt
        this.raf.seek(8);
        System.out.println("HOLA " + this.raf.getFilePointer());
        
        try {
            cont = this.raf.readInt();
            System.out.println("CONTADOR ES 0");
            finalPosT = 12;
            cont = 1;
        } catch (EOFException e) {
            System.out.println("NUEVO ARCHIVO");
            finalPosT = 12;
            cont = 1;
            this.raf.seek(12);
            this.raf.writeChar('@');
            for (int i = 14; i < 292; i = i + 2) {
                this.raf.writeChar('\u0000');
            }
        }
        
        this.rafTree = new RandomAccessFile("prueeba3.txt", "rw");//Maneja el árbol en el archivo profesor.txt
        
    }
    
    public static ManejoArchivosEstudiantes getManejoArchivosEstudiantes() throws IOException{
        if (manejoArchivosEstudiantes == null) {
            manejoArchivosEstudiantes = new ManejoArchivosEstudiantes();
        }
        return manejoArchivosEstudiantes;
    }
    
    public void crearArchivoEstudiante(int id, String name, String lastname, int telefono) throws IOException{
        this.raf.seek(this.raf.length());
        
        long posición = this.raf.getFilePointer();
        this.raf.writeInt(id);//Mete el ID en el archivo
        char nombre[] = name.toCharArray(); //Cambia de String a un arreglo de chars
        char apellido[] = lastname.toCharArray(); //Cambia de String a un arreglo de chars

        //Mete el nombre en el archivo con los espacios adicionales para completar el tamaño
        for (int i = 0; i < nombre.length; i++) {
            this.raf.writeChar(nombre[i]);
        }
        for (int i = nombre.length; i < 20; i++) {
            this.raf.writeChar('\u0000');
        }

        //Mete el apellido en el archivo con los espacios adicionales para completar el tamaño
        for (int i = 0; i < apellido.length; i++) {
            this.raf.writeChar(apellido[i]);
        }
        for (int i = apellido.length; i < 20; i++) {
            this.raf.writeChar('\u0000');
        }

        this.raf.writeInt(telefono);//Mete la extensión en el archivo
        
        arbol(id, posición);
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

    public void arbol(int id, long pos) throws FileNotFoundException, IOException {
        rafTree.seek(12);
        boolean flag = false;

        while (!flag) {
            System.out.println("id: " + id);
            long posicion = this.rafTree.getFilePointer();
            System.out.println(posicion);
            if (finalPosT == 12) {
                System.out.println("PRIMER IF");
                System.out.println(finalPosT);
                this.rafTree.seek(finalPosT);
                this.rafTree.writeInt(id);
                this.rafTree.writeLong(-1);
                this.rafTree.writeLong(-1);
                this.rafTree.writeLong(pos);
                System.out.println("se puso el primero");
                finalPosT = this.rafTree.getFilePointer();
                System.out.println("POSICION FINAL " + finalPosT);
                this.rafTree.seek(0);
                this.rafTree.writeLong(finalPosT);
                this.rafTree.writeInt(cont);
                flag = true;
            } else {
                System.out.println("ELSE PRIMER IF");
                int actual = this.rafTree.readInt();
//                int pruebaActual = ((actual-131071)/65536)+1;
                System.out.println("ID comparar " + id);
                System.out.println("ID actual: " + actual);
                this.rafTree.seek(posicion);
                if (actual < id) {
                    System.out.println("Entró primer condicional (El número metido es mayor que el que estaba)");
                    this.rafTree.seek(posicion + 12);
                    long derPos = this.rafTree.readLong();
                    this.rafTree.seek(posicion + 12);
                    System.out.println("cont = " + cont);
                    if (derPos == -1) {
                        this.rafTree.writeLong(finalPosT);
                        this.rafTree.seek(finalPosT);
                        this.rafTree.writeInt(id); //Revisar
                        this.rafTree.writeLong(-1);
                        this.rafTree.writeLong(-1);
                        this.rafTree.writeLong(pos);
                        flag = true;
                        cont++;
                        finalPosT = this.rafTree.getFilePointer();
                        System.out.println("POSICION FINAL " + finalPosT);
                        this.rafTree.seek(0);
                        this.rafTree.writeLong(finalPosT);
                        this.rafTree.writeInt(cont);
                        System.out.println("se añadio 1");
                    } else {
                        System.out.println("se fue a la pos: " + derPos);
                        //arbol(derPos, id, pos, cont);
                        this.rafTree.seek(derPos);
                    }
                } else {
                    System.out.println("Entró segundo condicional");
                    this.rafTree.seek(posicion + 4);
                    long izqPos = this.rafTree.readLong();
                    this.rafTree.seek(posicion + 4);
                    if (izqPos == -1) {
                        this.rafTree.writeLong(finalPosT);
                        this.rafTree.seek(finalPosT);
                        this.rafTree.writeInt(id); //Revisar
                        this.rafTree.writeLong(-1);
                        this.rafTree.writeLong(-1);
                        this.rafTree.writeLong(pos);
                        flag = true;
                        cont++;
                        finalPosT = this.rafTree.getFilePointer();
                        System.out.println("POSICION FINAL " + finalPosT);
                        this.rafTree.seek(0);
                        this.rafTree.writeLong(finalPosT);
                        this.rafTree.writeInt(cont);
                        System.out.println("se añadio 2");
                    } else {
                        //arbol(izqPos, id, pos, cont);
                        this.rafTree.seek(izqPos);
                    }
                }
            }
        }

    }
    
    public long buscarEstudiante(int id) throws IOException{
        System.out.println("entró");
        System.out.println("finalPosT: "+finalPosT);
        for (int i = 12; i < finalPosT; i+=28) {
            this.rafTree.seek(i);
            if (this.rafTree.readInt() == id) {
                this.rafTree.skipBytes(16);
                long posBus = this.rafTree.readLong();
                return posBus;
            }
        }
        return -1;
    }
    
    public int[] todosID() throws IOException{
        int cont = 0;
        int contador = 0;
        
        for (int i = 12; i < this.finalPosT; i += 28) {

            this.rafTree.seek(i);
            int x = this.rafTree.readInt();
            if (x > 0) {
                cont++;
            }
        }
        
        int[] ids = new int[cont];
        
        for (int i = 12; i < this.finalPosT; i+= 28) {
            this.rafTree.seek(i);
            int x = this.rafTree.readInt();
            ids [contador] = x;
        }
        
        return ids;
    }
}
