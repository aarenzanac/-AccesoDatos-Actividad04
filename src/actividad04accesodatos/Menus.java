/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad04accesodatos;

import PideDatos.PideDatos;
import clasesPojo.Empleado;
import excepciones.ExcepcionGestorBD;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author alex
 */
public class Menus {
    
    IncidenciasXND iXND = new IncidenciasXND();
       
        
    public void menuPrincipal() throws ExcepcionGestorBD{
        System.out.println("Bienvenido al programa de Gestión de Incidencias.\n");
        System.out.println("A continuación,elija una de las siguientes opciones:\n");
        
                    
        int opcion = 7;
        do{System.out.println("Opciones: \n"
                + "1 - Insertar un nuevo empleado.\n"
                + "2 - Loguear empleado.\n"
                + "3 - Modificar un empleado.\n"
                + "4 - Cambiar contraseña a un empleado.\n"
                + "5 - Eliminar un empleado.\n"
                + "6 - Salir.\n");
             
                opcion = PideDatos.pideEntero();
                
               
                switch (opcion){
                    case 1:
                        System.out.println("Ha elegio insertar un nuevo empleado.\n");
                        iXND.insertarEmpleado(iXND.conectarBDEmpleados());
                        continue;
                        
                    case 2:
                        System.out.println("Ha elegido loguear un empleado.\n");
                        iXND.validarEmpleado();
                        continue;
                        
                    case 3:
                        System.out.println("Ha elegido modificar un empleado.\n");
                       iXND.modificarPerfilEmpleado();
                        continue;
                        
                    case 4:
                        System.out.println("Ha elegido cambiar contraseña a un empleado.\n");
                        iXND.modificarContraseñaEmpleado();
                        continue;
                        
                    case 5:
                        System.out.println("Ha elegido eliminar un empleado.\n");
                        iXND.eliminarEmpleado();
                        continue;
                                           
                    case 6:
                        System.out.println("Ha elegido salir de la aplicación. Hasta pronto.\n");
                        break;
                        
                    default:
                    System.out.println("La opción introducida no es correcta. Elija una opción del 1 al 6, por favor.");
                    System.out.println("-----------------------------------------------------------------------");
                }

        }while(opcion != 6);
        
    }
    
    
    public void menuLogueado(Empleado empleadoLogin){
        System.out.println("Bienvenido a la intranet.\n");
        System.out.println("A continuación,elija una de las siguientes opciones:\n");
        
                    
        int opcion = 8;
        do{System.out.println("Opciones: \n"
                + "1 - Obtener incidencia a partir del id.\n"
                + "2 - Obtener listado de incidencias.\n"
                + "3 - Insetar nueva incidencia.\n"
                + "4 - Obtener incidencias creadas para un empleado.\n"
                + "5 - Obtener incidencias creadas por un empleado.\n"
                + "6 - Acceder al menú Historial.\n"
                + "7 - Desloguearse y salir al menú anterior.\n");
             
                opcion = PideDatos.pideEntero();
                
               
                switch (opcion){
                    case 1:
                        System.out.println("Ha elegio obtener incidencia a partir de un id.\n");
                        iXND.obtenerIncidenciaPorId();
                        continue;
                        
                    case 2:
                        System.out.println("Ha elegido obtener listado de incidencias.\n");
                        iXND.obtenerListadoIncidencias();
                        continue;
                        
                    case 3:
                        System.out.println("Ha elegido insertar una nueva incidencia.\n");
                        iXND.insertarIncidencia(empleadoLogin);
                        continue;
                        
                    case 4:
                        System.out.println("Ha elegido obtener incidencias creadas para un empleado.\n");
                        iXND.seleccionarIncidenciasPorEmpleadoDestino(empleadoLogin);
                        continue;
                        
                    case 5:
                        System.out.println("Ha elegido Obtener incidencias creadas por un empleado.\n");
                        iXND.seleccionarIncidenciasPorEmpleadoOrigen();
                        continue;
                    
                    case 6:
                        System.out.println("Ha elegido acceder al menú Historial.\n");
                        menuHistorial(empleadoLogin);
                        continue;    
                    case 7:
                        System.out.println("Ha elegido desloguearse y retornar al menú anterior.\n");
                        return;
                        
                        
                    default:
                    System.out.println("La opción introducida no es correcta. Elija una opción del 1 al 6, por favor.");
                    System.out.println("-----------------------------------------------------------------------");
                }

        }while(opcion != 6);
        
    }
    
    public void menuHistorial(Empleado empleadoLogin){
        
        System.out.println("Menú Historial.\n");
        System.out.println("A continuación,elija una de las siguientes opciones:\n");
        
                    
        int opcion = 5;
        do{System.out.println("Opciones: \n"
                + "1 - Obtener listado de inicios de sesión .\n"
                + "2 - Obtener listado de empleados que han consultado sus iniciones de sesión. \n"
                + "3 - Obtener el número de incidencias que hay en la BBDD. \n"
                + "4 - Retornar al menú anterior.\n");
             
                opcion = PideDatos.pideEntero();
                
               
                switch (opcion){
                    case 1:
                        System.out.println("Ha elegido obtener el listado de inicios de sesión.\n");
                        try {
                            HistorialXND hXND = new HistorialXND();
                            hXND.obtenerListadoIniciosSesion();
                        } catch (ExcepcionGestorBD ex) {
                            Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        continue;
                        
                    case 2:
                        System.out.println("Ha elegido obtener listado de empleados que han consultado sus iniciones de sesión\n");
                        try {
                            HistorialXND hXND = new HistorialXND();
                            hXND.obtenerEmpleadosConsultaIncidencias();
                        } catch (ExcepcionGestorBD ex) {
                            Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        continue;
                        
                    case 3:
                        System.out.println("Ha elegido obtener el número de incidencias que hay en la BBDD.\n");
                        iXND.obtenerNumeroIncidenciasBD();
                        continue;
                                                                  
                    case 4:
                        System.out.println("Ha elegido retornar al menú anteior.\n");
                        menuLogueado(empleadoLogin);
                        return;
                        
                        
                    default:
                    System.out.println("La opción introducida no es correcta. Elija una opción del 1 al 6, por favor.");
                    System.out.println("-----------------------------------------------------------------------");
                }

        }while(opcion != 6);
        
    }
}