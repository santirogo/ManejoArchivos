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
    DataFactory df;

    public ManejoArchivos() throws FileNotFoundException {
        this.raf = new RandomAccessFile("profesor.txt", "rw"); //Maneja el archivo profesor.txt
        this.df = new DataFactory();
    }
    
    public void newFile(int num) throws FileNotFoundException, IOException{ //Función inservible por el momento
        System.out.println("");
        raf.seek(raf.length());
        
        for (int i = 0; i < num; i++) {
            
            raf.writeInt(df.getNumberBetween(0,999999999));
            String nameAux = df.getFirstName();
            char namesAux [] = nameAux.toCharArray();
            int nameSize = namesAux.length*2;
            boolean flagName = false;
            boolean flagLastName = false;
            
            while (!flagName) {
                if (nameSize < 40) {
                    raf.writeChars(nameAux);
                    raf.seek(raf.getFilePointer()+(40-nameSize));
                    flagName = true;
                }
                System.out.println("1");
            }
            
            String lastNameAux = df.getLastName();
            char lNamesAux [] = lastNameAux.toCharArray();
            int lNameSize = lNamesAux.length*2;
            
            while (!flagLastName) {
                if (lNameSize < 40) {
                    raf.writeChars(lastNameAux);
                    raf.seek(raf.getFilePointer()+(40-lNameSize));
                    flagName = true;
                }
                System.out.println("2");
            }
            
            raf.writeInt(df.getNumber());
        }
        
        
    }
    
    public void crearArchivoProfesor(int id, String name, String lastname, int ext) throws IOException{
        raf.seek(raf.length());//Salta en el archivo hasta la última posición
        
        raf.writeInt(id);//Mete el ID en el archivo
        char nombre [] = name.toCharArray(); //Cambia de String a un arreglo de chars
        char apellido [] = lastname.toCharArray(); //Cambia de String a un arreglo de chars
        
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
}
