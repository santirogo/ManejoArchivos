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
public class ManejoArchivosCursos {
    private RandomAccessFile raf;
    private RandomAccessFile rafTree;
    private int cont;
    private static ManejoArchivosCursos manejoArchivosCursos;

    private ManejoArchivosCursos() throws FileNotFoundException, IOException {
        this.raf = new RandomAccessFile("clase.txt", "rw"); //Maneja el archivo profesor.txt
        this.raf.seek(0);
        this.raf.writeChar('@');
        for (int i = 2; i < 280; i = i+2) {
            this.raf.writeChar('\u0000');
        }
        this.rafTree = new RandomAccessFile("clase.txt", "rw");//Maneja el árbol en el archivo profesor.txt
        this.cont = 1;
    }
    
    public static ManejoArchivosCursos getManejoArchivosCursos() throws IOException{
        if (manejoArchivosCursos == null) {
            manejoArchivosCursos = new ManejoArchivosCursos();
        }
        return manejoArchivosCursos;
    }
    
    public void crearArchivoClase(int codigo, String name, int duracion, int idProfe, long posProfe) throws IOException{
        this.raf.seek(this.raf.length());//Salta en el archivo hasta la última posición
        long posición = this.raf.getFilePointer();
        this.raf.writeInt(codigo);//Mete el ID en el archivo
        char nombre [] = name.toCharArray(); //Cambia de String a un arreglo de chars
        
        //Mete el nombre en el archivo con los espacios adicionales para completar el tamaño
        for (int i = 0; i < nombre.length; i++) {
            this.raf.writeChar(nombre[i]);
        }
        for (int i = nombre.length; i < 20; i++) {
            this.raf.writeChar('\u0000');
        }
        this.raf.writeInt(duracion);
        this.raf.writeInt(idProfe);
        this.raf.writeLong(posProfe);
        
        arbol(codigo, posición);
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
    
    public void arbol(int id, long pos) throws FileNotFoundException, IOException {
        rafTree.seek(0);
        boolean flag = false;
        
        while (!flag) {
            System.out.println("id: "+id);
            long posicion = this.rafTree.getFilePointer();
            System.out.println(posicion);
            if (rafTree.readChar() == '@') {
                this.rafTree.seek(0);
                this.rafTree.writeInt(id);
                this.rafTree.writeLong(-1);
                this.rafTree.writeLong(-1);
                this.rafTree.writeLong(pos);
                System.out.println("se puso el primero");
                flag = true;
            } else {
                int actual = this.rafTree.readInt();
                int pruebaActual = ((actual-131071)/65536)+1;
                System.out.println("ID comparar "+pruebaActual);
                System.out.println("ID actual: "+actual);
                this.rafTree.seek(posicion);
                if (pruebaActual < id) {
                    System.out.println("Entró primer condicional (El número metido es mayor que el que estaba)");
                    this.rafTree.seek(posicion + 12);
                    long derPos = this.rafTree.readLong();
                    this.rafTree.seek(posicion + 12);
                    System.out.println("cont = "+cont);
                    if (derPos == -1) {
                        this.rafTree.writeLong(cont * 28);
                        this.rafTree.seek(cont * 28);
                        this.rafTree.writeInt(id); //Revisar
                        this.rafTree.writeLong(-1);
                        this.rafTree.writeLong(-1);
                        this.rafTree.writeLong(pos);
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
                    this.rafTree.seek(posicion + 4);
                    long izqPos = this.rafTree.readLong();
                    this.rafTree.seek(posicion + 4);
                    if (izqPos == -1) {
                        this.rafTree.writeLong(cont * 28);
                        this.rafTree.seek(cont * 28);
                        this.rafTree.writeInt(id); //Revisar
                        this.rafTree.writeLong(-1);
                        this.rafTree.writeLong(-1);
                        this.rafTree.writeLong(pos);
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
    
    public int [] buscarCursos(int idProf) throws IOException{
        
        int repeticiones = 0;
        int contador = 0;
        
        for (int i = 0; i < 280; i+=28) {
            this.rafTree.seek(i+20);
            long posCurso = this.rafTree.readLong();
            this.raf.seek(posCurso+48);
            int idProfe = this.raf.readInt();
            if (idProfe == idProf) {
                repeticiones++;
            }
        }
        
        int cursos [] = new int[repeticiones];
        
        for (int i = 0; i < 280; i+=28) {
            this.rafTree.seek(i+20);
            long posCurso = this.rafTree.readLong();
            this.raf.seek(posCurso+48);
            int idProfe = this.raf.readInt();
            if (idProfe == idProf) {
                this.rafTree.seek(i);
                cursos[contador] =  this.rafTree.readInt();
                contador++;
            }
        }
        return cursos;
    }
    
    public int [] buscarCursosExceptoDe(String name) throws IOException{
        char cursos [] = name.toCharArray();
        char primeraLetra = cursos[0];
        int tamano = 0;
        int contador = 0;
        
        for (int i = 0; i < 280; i+=28) {
            this.rafTree.seek(i+20);
            long posMover = this.rafTree.readLong();
            this.raf.seek(posMover+4);
            char letra = this.raf.readChar();
            if (letra != primeraLetra) {
                tamano++;
            }
        }
        
        int cursosSinName [] = new int[tamano];
        
        for (int i = 0; i < 280; i+=28) {
            this.rafTree.seek(i+20);
            long posMover = this.rafTree.readLong();
            this.raf.seek(posMover+4);
            char letra = this.raf.readChar();
            if (letra != primeraLetra) {
                this.raf.seek(posMover);
                int cod = this.raf.readInt();
                cursosSinName[contador] = cod;
                contador++;
            }
        }
        return cursosSinName;
    }
    
    public long buscarPosicionCursos(int cod) throws IOException{
        for (int i = 0; i < 280; i+=28) {
            this.rafTree.seek(i);
            int codigo = this.rafTree.readInt();
            if (cod == codigo) {
                this.rafTree.seek(i+20);
                return this.rafTree.readLong();
            }
        }
        return -1;
    }
    
    public int [] buscarCursosDuracionExcepto(String name) throws IOException{
        char curso [] = name.toCharArray();
        char letraR1 = curso[0];
        char letraR2 = curso[1];
        int tamano = 0;
        int contador = 0;
        
        for (int i = 0; i < 280; i+=28) {
            this.rafTree.seek(i+20);
            long posMover = this.rafTree.readLong();
            this.raf.seek(posMover+4);
            char letra1 = this.raf.readChar();
            char letra2 = this.raf.readChar();
            this.raf.seek(posMover+44);
            int dur = this.raf.readInt();
            if (letraR1!=letra1 && letraR2!=letra2 && dur > 10) {
                tamano++;
            }
        }
        int cursos [] = new int[tamano];
        
        for (int i = 0; i < 280; i+=28) {
            this.rafTree.seek(i+20);
            long posMover = this.rafTree.readLong();
            this.raf.seek(posMover+4);
            char letra1 = this.raf.readChar();
            char letra2 = this.raf.readChar();
            this.raf.seek(posMover+44);
            int dur = this.raf.readInt();
            if (letraR1!=letra1 && letraR2!=letra2 && dur > 10) {
                this.raf.seek(posMover);
                cursos[contador] = this.raf.readInt();
                contador++;
            }
        }
        return cursos;
    }
}
