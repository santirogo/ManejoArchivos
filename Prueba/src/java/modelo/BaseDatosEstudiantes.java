/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.util.ArrayList;
public class BaseDatosEstudiantes {

    private ArrayList<String>nombres;
    private ArrayList<String>apellidos;
    private ArrayList<Double>promedio;
    private ArrayList<String>sexo;
    
    public BaseDatosEstudiantes() {
        this.nombres = new ArrayList<String>();
        this.apellidos = new ArrayList<String>();
        this.promedio = new ArrayList<Double>();
        this.sexo = new ArrayList<String>();
    }
    
    public void agregarEstudiante(String nombre, String apellido, double promedio, String sexo){
        this.nombres.add(nombre);
        this.apellidos.add(apellido);
        this.promedio.add(promedio);
        this.sexo.add(sexo);
    }
    
    public ArrayList getNombresEstudiantes(){
        return nombres;
    }
    
    public ArrayList getApellidosEstudiantes(){
        return apellidos;
    }
    
    public ArrayList getPromedioEstudiantes(){
        return promedio;
    }
    
    public ArrayList getSexoEstudiantes(){
        return sexo;
    }
}
