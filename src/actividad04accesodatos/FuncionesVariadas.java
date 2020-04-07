/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad04accesodatos;

import PideDatos.PideDatos;
import enums.Prioridad;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author USUARIO
 */
public class FuncionesVariadas {
             
        
    public static Prioridad crearPrioridad(String prioridadString){
        
        if(prioridadString.equals("URGENTE")){
        return Prioridad.URGENTE;
        }else{
        return Prioridad.NORMAL;
        }
    }
    
    public Prioridad solicitarPrioridad() throws IOException{
        Prioridad prioridad = null;
        String prioridadString = null;
        do{
            prioridadString = PideDatos.pideString("Introduzca la prioridad (NORMAL O URGENTE): \n").toUpperCase();
            if(!prioridadString.equals("URGENTE") && !prioridadString.equals("NORMAL")){
                System.out.println("Valor incorrecto. Debe ser Normal o Urgente.\n");
            }
        }while(!prioridadString.equals("URGENTE") && !prioridadString.equals("NORMAL"));
                 
               
        switch(prioridadString){
            case("URGENTE"):
                return Prioridad.URGENTE;
                
            case("NORMAL"):
                return Prioridad.NORMAL;
        }
        
        return prioridad;
    }
    
    public String obtenerFechaHora(){
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String fechaHora = hourdateFormat.format(date).toString();
        //System.out.println(fechaHora);
        return fechaHora;
    }
}