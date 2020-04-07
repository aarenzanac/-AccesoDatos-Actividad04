/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PideDatos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author USUARIO
 */
public class PideDatos {
       
    public static double pideDouble(String pregunta){
      
        Double numeroDouble = null;
      
        while (numeroDouble == null){
            try{
                //muestra la pregunta introducida anteriormente y pregunta un número entero por teclado
                System.out.println(pregunta);
                Scanner entrada = new Scanner(System.in);
                numeroDouble = entrada.nextDouble();
                
                // capturamos el error en caso de que se introduzca un valor no valido (texto o decimal)
            }catch(InputMismatchException e){
                System.out.println("El número introducido no es válido. Por favor, introduazca caracteres numéricos enteros válidos.\n");
            }
        // retornamos numeroDoble    
        } return numeroDouble;
    }
    
    public static int pideEntero(){
       
        Integer numeroEntero = null;
        
        do{
            try{
                //solicitamos al usuario un número por teclado
                Scanner entrada = new Scanner(System.in);
                numeroEntero = entrada.nextInt();
                
                        
            //si el número no es correcto (decimal o texto por ejemplo) que muestre el siguiente mensaje de error y vuelta a empezar
            }catch (InputMismatchException e){            
                System.out.println("El dato introducido no es correcto. Por favor, introduzca un número vaĺido.\n");
            }
        }while (numeroEntero == null);
    //retorna el número entero introducido pero no lo muestra por pantalla    
    return numeroEntero;    
    }
    
    public static int pideEntero(String cadena){
        int numero = -1;
       
        while (numero == -1){
            try{
                //muestra la pregunta introducida anteriormente y pregunta un número entero por teclado
                System.out.println(cadena);
                Scanner entrada = new Scanner(System.in);
                numero = entrada.nextInt();
                
            // capturamos el error en caso de que se introduzca un valor no valido (texto o decimal)
            }catch(InputMismatchException e){
                System.out.println("El número introducido no es válido. Por favor, introduazca caracteres numéricos enteros válidos.\n");
            }
        // retornamos numero   
        } return numero;
    }
    
    //Creo la función de pedir String para la actividad 2
    public static String pideString(String cadena) throws IOException{
        System.out.println(cadena + "\n");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String valorIntroducido = br.readLine();
        
        return valorIntroducido;
    }
}
