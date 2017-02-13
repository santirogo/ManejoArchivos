/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.util.ArrayList;
public class BaseDatosProfesores {

    private ArrayList<String>nombres;
    private ArrayList<String>apellidos;
    private ArrayList<Integer>id;
    private ArrayList<Integer>ext;
    
    public BaseDatosProfesores() {
        this.nombres = new ArrayList<String>();
        this.apellidos = new ArrayList<String>();
        this.id = new ArrayList<Integer>();
        this.ext = new ArrayList<Integer>();
    }
    
    public void agregarProfesor(int id, String nombre, String apellido, int ext){
        this.id.add(id);
        this.nombres.add(nombre);
        this.apellidos.add(apellido);
        this.ext.add(ext);
    }
    
    public ArrayList getNombresProfesor(){
        return nombres;
    }
    
    public ArrayList getApellidosProfesor(){
        return apellidos;
    }

    public ArrayList<Integer> getId() {
        return id;
    }

    public ArrayList<Integer> getExt() {
        return ext;
    }
    
}
