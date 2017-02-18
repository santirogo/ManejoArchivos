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
 * @author Labing
 */
public class ManejoArchivos {

    RandomAccessFile raf;
    RandomAccessFile rafTree;
    DataFactory df;
    int cont;

    public ManejoArchivos() throws FileNotFoundException, IOException {
        this.raf = new RandomAccessFile("prueba.txt", "rw"); //Maneja el archivo profesor.txt
        this.raf.seek(0);
        this.raf.writeChar('@');
        for (int i = 2; i < 280; i = i + 2) {
            this.raf.writeChar('\u0000');
            //this.raf.writeInt(-1);
            //this.raf.skipBytes(280);
        }
        this.rafTree = new RandomAccessFile("prueba.txt", "rw");//Maneja el árbol en el archivo profesor.txt
        this.df = new DataFactory();
        this.cont = 1;
    }

    public void newFile(int num) throws FileNotFoundException, IOException { //Función inservible por el momento
        System.out.println("");
        raf.seek(raf.length());

        for (int i = 0; i < num; i++) {

            raf.writeInt(df.getNumberBetween(0, 999999999));
            String nameAux = df.getFirstName();
            char namesAux[] = nameAux.toCharArray();
            int nameSize = namesAux.length * 2;
            boolean flagName = false;
            boolean flagLastName = false;

            while (!flagName) {
                if (nameSize < 40) {
                    raf.writeChars(nameAux);
                    raf.seek(raf.getFilePointer() + (40 - nameSize));
                    flagName = true;
                }
                System.out.println("1");
            }

            String lastNameAux = df.getLastName();
            char lNamesAux[] = lastNameAux.toCharArray();
            int lNameSize = lNamesAux.length * 2;

            while (!flagLastName) {
                if (lNameSize < 40) {
                    raf.writeChars(lastNameAux);
                    raf.seek(raf.getFilePointer() + (40 - lNameSize));
                    flagName = true;
                }
                System.out.println("2");
            }

            raf.writeInt(df.getNumber());
        }

    }

    public void crearArchivoProfesor(int id, String name, String lastname, int ext) throws IOException {
        raf.seek(raf.length());//Salta en el archivo hasta la última posición
        long posición = raf.getFilePointer();
        raf.writeInt(id);//Mete el ID en el archivo
        char nombre[] = name.toCharArray(); //Cambia de String a un arreglo de chars
        char apellido[] = lastname.toCharArray(); //Cambia de String a un arreglo de chars

        //Mete el nombre en el archivo con los espacios adicionales para completar el tamaño
        for (int i = 0; i < nombre.length; i++) {
            raf.writeChar(nombre[i]);
        }
        for (int i = nombre.length; i < 20; i++) {
            raf.writeChar('\u0000');
        }

        //Mete el apellido en el archivo con los espacios adicionales para completar el tamaño
        for (int i = 0; i < apellido.length; i++) {
            raf.writeChar(apellido[i]);
        }
        for (int i = apellido.length; i < 20; i++) {
            raf.writeChar('\u0000');
        }

        raf.writeInt(ext);//Mete la extensión en el archivo

        //agregarNodoArbol(0, id, posición); //Agrego el nodo en el árbol
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

    public void agregarNodoArbol(int raizP, int id, long bytPos) throws IOException { // Ojo con raizP 
        rafTree.seek(raizP); // Me coloco en la posición donde voy a empezar a escribir
        if (this.rafTree.length() == 0 || this.rafTree.readChar() == '\u0000') {
            rafTree.seek(raizP); // Me coloco en la posición donde voy a empezar a escribir
            this.rafTree.writeInt(id);// Se coloca el id en la primera posición
            this.rafTree.writeLong(-1);// Si se tiene -1 en algún campo significa vacío
            this.rafTree.writeLong(-1);// En este caso todo es vacío porque es la primera que se pone
            this.rafTree.writeLong(-1);
        } else {
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
                } else {
                    if (posDer > id) {
                        rafTree.seek(pos + 8); // Salto a la posición donde se encuentra el ID de la derecha
                        rafTree.writeInt(id); // Sobreescribo el ID
                        rafTree.seek(pos + 20); // Salto a la posición de la posición en bytes de ID derecha
                        long posByteDer = rafTree.readLong(); //Guardo la posición
                        rafTree.seek(pos + 20); //Vuelvo a la misma posición
                        rafTree.writeLong(bytPos); //Sobreescribo la posición
                        agregarNodoArbol(raizP + 28, posDer, posByteDer); //Vuelvo a comenzar pero con las dos que estaban antes
                    } else if (posDer < id) {
                        agregarNodoArbol(raizP + 28, id, bytPos); // Vuelvo a comenzar con las mismas variable
                    }
                }
            } else if (id < raiz) {
                rafTree.seek(pos + 4); // Voy a la posición del campo donde está el ID de la izquierda
                int posIzq = rafTree.readInt(); // Leo el número para saber si está vacío o si no y lo guardo
                if (posIzq == -1) { // Si está vacío
                    rafTree.seek(pos + 4); // Vuelvo a la posición del ID de la izquierda
                    rafTree.writeInt(id); // Escribo el ID
                    rafTree.seek(pos + 12); // Salto hacia donde se encuentra el campo con el # de byte izquierda
                    rafTree.writeLong(bytPos); //Escribo la posición correspondiente en el archivo
                } else {
                    if (posIzq < id) {
                        rafTree.seek(pos + 4); // Salto a la posición donde se encuentra el ID de la izquierda
                        rafTree.writeInt(id); // Sobreescribo el ID
                        rafTree.seek(pos + 12); // Salto a la posición de la posición en bytes de ID izquierda
                        long posByteIzq = rafTree.readLong(); // Guardo la posición
                        rafTree.seek(pos + 12); // Vuelvo a la misma posición
                        rafTree.writeLong(bytPos); //Sobreescribo la posición
                        agregarNodoArbol(raizP + 28, posIzq, posByteIzq); // Vuelvo a comenzar pero con las dos que estaban antes
                    } else if (posIzq > id) {
                        agregarNodoArbol(raizP + 28, id, bytPos); // Vuelvo a comenzar con las mismas variable
                    }
                }
            }
        }
    }

    public void arbol(int id, long pos) throws FileNotFoundException, IOException {
        rafTree.seek(0);
        boolean flag = false;
        
        while (!flag) {
            long posicion = this.rafTree.getFilePointer();
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
                if (actual > id) {
                    this.rafTree.seek(12);
                    long derPos = this.rafTree.readLong();
                    if (derPos == -1) {
                        this.rafTree.seek(this.rafTree.getFilePointer() - 8);
                        this.rafTree.writeLong(cont * 28);
                        this.rafTree.seek(cont * 28);
                        this.rafTree.writeInt(id); //Revisar
                        this.rafTree.writeFloat(-1);
                        this.rafTree.writeFloat(-1);
                        this.rafTree.writeLong(pos);
                        flag = true;
                        cont++;
                        System.out.println("se añadio 1");
                    } else {
                        //arbol(derPos, id, pos, cont);
                        this.rafTree.seek(derPos);
                    }
                }else{
                    this.rafTree.seek(4);
                    long izqPos = this.rafTree.readLong();
                    if (izqPos == -1) {
                        this.rafTree.seek(this.rafTree.getFilePointer() - 8);
                        this.rafTree.writeLong(cont * 28);
                        this.rafTree.seek(cont * 28);
                        this.rafTree.writeInt(id); //Revisar
                        this.rafTree.writeFloat(-1);
                        this.rafTree.writeFloat(-1);
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
    
    public void buscar(int id) throws IOException{
        for (int i = 0; i < 280; i = i + 28) {
            System.out.println("asdfadfvg");
            this.rafTree.seek(i);
            System.out.println(this.rafTree.readInt());
            this.rafTree.seek(i);
            if (this.rafTree.readInt() == id) {
                System.out.println("asdjklcfnsdñasdvmlavnadlfkvnadlfkv");
                this.rafTree.skipBytes(16);
                long posBus = this.rafTree.readLong();
                this.raf.seek(posBus);
                System.out.println("ID: "+this.raf.readInt());
                System.out.print("Nombre: ");
                
                for (int j = 0; j < 20; j++) {
                    System.out.print(this.raf.readChar());
                }
                System.out.println();
                System.out.print("Apellido");
                for (int j = 0; j < 20; j++) {
                    System.out.print(this.raf.readChar());
                }
                System.out.println();
                System.out.print("Ext: "+this.raf.readInt());
                
            }
        }
    }
}
