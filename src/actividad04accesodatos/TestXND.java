/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad04accesodatos;

import excepciones.ExcepcionGestorBD;
import org.xmldb.api.base.Collection;

/**
 *
 * @author alex
 */
public class TestXND {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ExcepcionGestorBD {
        System.out.println("START...");
        Collection col;
        IncidenciasXND iXND = new IncidenciasXND();
        HistorialXND hXND = new HistorialXND();
        Menus menu = new Menus();
        
        
        //Conecta a la base de datos y muestra el contenido de un recurso (definido en las variables de LibroXML)
        col = iXND.conectarBDEmpleados();
        col = iXND.conectarBDIncidencias(); 
        col = hXND.conectarBDHistorial();
        
        System.out.println("-----------------------------------------\n");
        
        menu.menuPrincipal();
    }
    
}
