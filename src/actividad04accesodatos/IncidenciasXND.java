/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad04accesodatos;

import clasesPojo.Empleado;
import clasesPojo.Incidencia;
import PideDatos.PideDatos;
import clasesPojo.Historial;
import enums.Prioridad;
import enums.TipoEvento;
import excepciones.ExcepcionGestorBD;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.OutputKeys;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XQueryService;


/**
 *
 * @author USUARIO
 */
public class IncidenciasXND {
    
    protected static String driver = "org.exist.xmldb.DatabaseImpl";
    public static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private Database database;
    private Collection col;
    private final String usuario;
    private final String usuarioPwd;
    private final String collection;
    private final String nombre_recurso;
    private final String nombre_recurso1;
    FuncionesVariadas fv = new FuncionesVariadas();

    
    public IncidenciasXND() {
        this.database = null;
        this.usuario = "admin";
        this.usuarioPwd = "root";
        collection = "/db/Incidencias/Incidencias/";
        nombre_recurso = "empleados.xml";
        nombre_recurso1 = "incidencias.xml";
        
    }
    
    public Collection conectarBDEmpleados() throws ExcepcionGestorBD {
        try {
            System.out.println("Intento Conectar...");
            Class cl = Class.forName(driver);
            System.out.println("Conecta el driver...");
            //Se crea un objeto Database
            database = (Database) cl.newInstance();
            DatabaseManager.registerDatabase(database);
            System.out.println("Ahora obtiene la colección " + URI + collection);
            //Ahora se obtiene la colección  (URI + collection) 
            //con el usuario y password que tiene acceso a ella.
            col = DatabaseManager.getCollection(URI + collection, usuario, usuarioPwd);
            /*
            if (col.getResourceCount() == 0) {
                //si la collección no tiene recursos no podrá devolver ninguno
                System.out.println("La colección no tiene recursos. No puede devolver ninguno [FIN]");
                return null;

            } else {
                System.out.println("...La colección no es nula...");
                Resource res = null;
                Resource res1 = null;
                res = (Resource) col.getResource(nombre_recurso);
                System.out.println("De la colección saca el recurso que tiene la variable nombre_recurso:" + nombre_recurso);
                XMLResource xmlres = (XMLResource) res;
                System.out.println("La salida es:\n" + xmlres.getContent());
                System.out.println("El tipo es:" + xmlres.getResourceType());
                return col;
            }
            */
            return col;
        } catch (ClassNotFoundException e) {
            throw new ExcepcionGestorBD("No se encuentra la clase del driver");
        } catch (InstantiationException e) {
            throw new ExcepcionGestorBD("Error instanciando el driver");
        } catch (IllegalAccessException e) {
            throw new ExcepcionGestorBD("Se ha producido una IllegalAccessException");
        } catch (XMLDBException e) {
            throw new ExcepcionGestorBD("error XMLDB :" + e.getMessage());
        }
    }
      
    public Collection conectarBDIncidencias() throws ExcepcionGestorBD {
        try {
            System.out.println("Intento Conectar...");
            Class cl = Class.forName(driver);
            System.out.println("Conecta el driver...");
            //Se crea un objeto Database
            database = (Database) cl.newInstance();
            DatabaseManager.registerDatabase(database);
            System.out.println("Ahora obtiene la colección " + URI + collection);
            //Ahora se obtiene la colección  (URI + collection) 
            //con el usuario y password que tiene acceso a ella.
            col = DatabaseManager.getCollection(URI + collection, usuario, usuarioPwd);
            /*
            if (col.getResourceCount() == 0) {
                //si la collección no tiene recursos no podrá devolver ninguno
                System.out.println("La colección no tiene recursos. No puede devolver ninguno [FIN]");
                return null;

            } else {
                System.out.println("...La colección no es nula...");
                Resource res1 = null;
                res1 = (Resource) col.getResource(nombre_recurso1);
                System.out.println("De la colección saca el recurso que tiene la variable nombre_recurso:" + nombre_recurso1);
                XMLResource xmlres1 = (XMLResource) res1;
                //System.out.println("La salida es:\n" + xmlres1.getContent());
                //System.out.println("El tipo es:" + xmlres1.getResourceType());
                return col;
            }
            */
            return col;
        } catch (ClassNotFoundException e) {
            throw new ExcepcionGestorBD("No se encuentra la clase del driver");
        } catch (InstantiationException e) {
            throw new ExcepcionGestorBD("Error instanciando el driver");
        } catch (IllegalAccessException e) {
            throw new ExcepcionGestorBD("Se ha producido una IllegalAccessException");
        } catch (XMLDBException e) {
            throw new ExcepcionGestorBD("error XMLDB :" + e.getMessage());
        }
    }
    
    public void ejecutarXUpdate(String consulta, String contexto) throws ExcepcionGestorBD {
       Collection col;
       ResourceSet result = null;
       try {
            col = DatabaseManager.getCollection(URI + collection, usuario, usuarioPwd);
            XQueryService service = (XQueryService) col.getService("XQueryService",
                    "1.0");
            service.setProperty(OutputKeys.INDENT, "yes");
            service.setProperty(OutputKeys.ENCODING, "UTF-8");
            CompiledExpression compiled = service.compile(consulta);
            result = service.execute(compiled);
        } catch (XMLDBException e) {
            try {
                throw new ExcepcionGestorBD("Error ejecutando query: " + e.getMessage());
            } catch (ExcepcionGestorBD ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    public ResourceSet ejecutarQuery(String consulta, String contexto) throws ExcepcionGestorBD {
        ResourceSet resultado = null;
        Collection col;
        try{if(contexto == null){
                col = DatabaseManager.getCollection(URI);
            }else{
                col = DatabaseManager.getCollection(URI + contexto);
            }
        
            XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
            service.setProperty(OutputKeys.INDENT, "yes");
            service.setProperty(OutputKeys.ENCODING, "UTF-8");
            
            CompiledExpression compiled = service.compile(consulta);
            
            resultado = service.execute(compiled);
            
            ResourceIterator iterator = resultado.getIterator();
            
            if(!iterator.hasMoreResources()){
                System.out.println("Sin resultados para la consulta indicada. \n");
                resultado = null;
            }/*else{
                while(iterator.hasMoreResources()){
                    Resource res = iterator.nextResource();
                    System.out.println((String) res.getContent() + "\n");
                }
            }*/
        }catch (XMLDBException e){
            System.out.println("Error al ejecutar query: " + e.getMessage());
        }
        
        return resultado;
    }
    
    public boolean ejecutarQueryBoolean(String consulta, String contexto) throws ExcepcionGestorBD {
        ResourceSet resultado = null;
        boolean resultadoBoolean = false;
        Collection col;
        try{if(contexto == null){
                col = DatabaseManager.getCollection(URI);
            }else{
                col = DatabaseManager.getCollection(URI + contexto);
            }
        
            XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
            service.setProperty(OutputKeys.INDENT, "yes");
            service.setProperty(OutputKeys.ENCODING, "UTF-8");
            
            CompiledExpression compiled = service.compile(consulta);
            
            resultado = service.execute(compiled);
            
            ResourceIterator iterator = resultado.getIterator();
            
            if(!iterator.hasMoreResources()){
                resultadoBoolean = false;
                
            }else{
                resultadoBoolean = true;
                
            }
        }catch (XMLDBException e){
            System.out.println("Error al ejecutar query: " + e.getMessage());
        }
        return resultadoBoolean;
    }
   
    public void insertarEmpleado(Collection col){
        Empleado empleadoInsertar = crearEmpleado();
        
        boolean existe = comprobarExistenciaEmpleado(empleadoInsertar, collection);
        //boolean existe = false;
        if(!existe){        
            String consulta = "update insert <empleado><usuario>" + empleadoInsertar.getNombreusuario() + "</usuario> <password>"
                    + "" + empleadoInsertar.getPassword() + "</password> <nombre>" + empleadoInsertar.getNombre() + "</nombre> <apellidos>"
                    + "" + empleadoInsertar.getApellidos() + "</apellidos> <direccion>" + empleadoInsertar.getDireccion() + "</direccion> <telefono>"
                    + "" + empleadoInsertar.getTelefono() + "</telefono></empleado> into /empleados";
            
            try {
                ejecutarXUpdate(consulta, collection);
            } catch (ExcepcionGestorBD ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println("Empleado insertado con éxito, \n");
                 
        }else{
            System.out.println("El empleado indicado ya existe, no se introducirá.\n");
        }     
    }
    
    public void validarEmpleado(){
        Menus m = new Menus();
        Empleado login = new Empleado();
        Empleado empleadoLogin = null;
        try {
            System.out.println("A continuación introduzca los datos de login: \n");
            String nombreUsuario = PideDatos.pideString("Nombre de usuario: \n");
            String password = PideDatos.pideString("Password: \n");
            login.setNombreusuario(nombreUsuario);
            login.setPassword(password);
            //LE PASO EL OBJETO EMPLEADO CON LOS DATOS DEL USUARIO Y SI EXISTE ESE USUARIO CON ESE PASSWORD DEVUELVE TRUE         
            empleadoLogin = comprobarLoginEmpleado(login, collection);
        } catch (IOException ex) {
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(empleadoLogin.getNombreusuario() != null){
            
            try {String fechaHora = fv.obtenerFechaHora();
                HistorialXND hXND = new HistorialXND();
                hXND.insertarEvento(TipoEvento.I, empleadoLogin, fechaHora);
            } catch (ExcepcionGestorBD ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            m.menuLogueado(empleadoLogin);
            
        }else{
            System.out.println("Nombre de usuario o contraseña incorrectos. \n");
        }
        
    }
    
    public void modificarPerfilEmpleado(){
        Empleado empleadoModificar = new Empleado();
        String nuevoNombre = "";
        String nuevoApellido = "";
        String nuevaDireccion = "";
        String nuevoTelefono = "";
        try {
            empleadoModificar.setNombreusuario(PideDatos.pideString("Introduzca el nombre de usuario del empleado para modificar perfil: \n"));
        } catch (IOException ex) {
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean existe = comprobarExistenciaEmpleado(empleadoModificar, collection);
        if(!existe){
            System.out.println("El empleado que desea modificar no existe. \n");
        }else{
            try {
            nuevoNombre = PideDatos.pideString("Introduzca el nuevo nombre del empleado: \n");
            nuevoApellido = PideDatos.pideString("Introduzca el nuevo apellido del empleado: \n");
            nuevaDireccion = PideDatos.pideString("Introduzca la nueva dirección del empleado: \n");
            nuevoTelefono = PideDatos.pideString("Introduzca el nuevo número de teléfono del empleado: \n");
            } catch (IOException ex) {
                System.out.println("Error " + ex.getMessage());
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
            String query = "update replace /empleados/empleado[usuario='" + empleadoModificar.getNombreusuario() + "'] /nombre with <nombre>" + nuevoNombre + "</nombre>";
            String query1 = "update replace /empleados/empleado[usuario='" + empleadoModificar.getNombreusuario() + "'] /apellidos with <apellidos>" + nuevoApellido + "</apellidos>";
            String query2 = "update replace /empleados/empleado[usuario='" + empleadoModificar.getNombreusuario() + "'] /direccion with <direccion>" + nuevaDireccion + "</direccion>";
            String query3 = "update replace /empleados/empleado[usuario='" + empleadoModificar.getNombreusuario() + "'] /telefono with <telefono>" + nuevoTelefono + "</telefono>";
            try {
                ejecutarXUpdate(query, collection);
                ejecutarXUpdate(query1, collection);
                ejecutarXUpdate(query2, collection);
                ejecutarXUpdate(query3, collection);
            } catch (ExcepcionGestorBD ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Empleado modificado con éxito. \n");
            
        }
    }
    
    public void modificarContraseñaEmpleado(){
        Empleado empleadoModificar = new Empleado();
        String nuevoPassword = "";
        
        try {
            empleadoModificar.setNombreusuario(PideDatos.pideString("Introduzca el nombre de usuario del empleado para modificar contraseña: \n"));
        } catch (IOException ex) {
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean existe = comprobarExistenciaEmpleado(empleadoModificar, collection);
        if(!existe){
            System.out.println("El empleado que desea modificar no existe. \n");
        }else{
            try {
            nuevoPassword = PideDatos.pideString("Introduzca el nuevo password del empleado: \n");
            
            } catch (IOException ex) {
                System.out.println("Error " + ex.getMessage());
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
            String query = "update replace /empleados/empleado[usuario='" + empleadoModificar.getNombreusuario() + "'] /password with <password>" + nuevoPassword + "</password>";
            
            try {
                ejecutarXUpdate(query, collection);
                
            } catch (ExcepcionGestorBD ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Password modificado con éxito. \n");
            
        }
    }
    
    public void eliminarEmpleado(){
        Empleado empleadoModificar = new Empleado();
             
        try {
            empleadoModificar.setNombreusuario(PideDatos.pideString("Introduzca el nombre de usuario del empleado para eliminar: \n"));
        } catch (IOException ex) {
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean existe = comprobarExistenciaEmpleado(empleadoModificar, collection);
        if(!existe){
            System.out.println("El empleado que desea eliminar no existe. \n");
        }else{
            
            String query = "update delete /empleados/empleado[usuario='" + empleadoModificar.getNombreusuario() + "']";
            
            try {
                ejecutarXUpdate(query, collection);
                
            } catch (ExcepcionGestorBD ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Empleado eliminado con éxito. \n");
        }
    }
    
    public Empleado crearEmpleado(){
        Empleado empleado = new Empleado();
        try {//CREO EL EMPLEADO PARA AÑADIR
            empleado.setNombreusuario(PideDatos.pideString("Introduzca un nombre de usuario: \n"));
            empleado.setNombre(PideDatos.pideString("Introduzca el nombre: \n"));
            empleado.setApellidos(PideDatos.pideString("Introduzca el apellido: \n"));
            empleado.setDireccion(PideDatos.pideString("Introduzca la dirección: \n"));
            empleado.setTelefono(PideDatos.pideString("Introduzca el número de teléfono: \n"));
            empleado.setPassword(PideDatos.pideString("Introduzca un password"));
        } catch (IOException ex) {
            System.out.println("Eror creando empleado " + ex.getMessage());
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return empleado;
    }
    
    public boolean comprobarExistenciaEmpleado(Empleado e, String collection){
        String query = "for $empleado in //empleados/empleado/usuario where $empleado = '" + e.getNombreusuario() + "' return $empleado";
        boolean existeEmpleado = false;
        try {
            existeEmpleado = ejecutarQueryBoolean(query, collection);
        } catch (ExcepcionGestorBD ex) {
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existeEmpleado;
    }
    
    public Empleado comprobarLoginEmpleado(Empleado e, String collection){
        
        String query = "for $empleado in //empleados/empleado where $empleado/usuario = '" + e.getNombreusuario() + "' and $empleado/password = '" + e.getPassword() + "' return $empleado";
        ResourceSet existeEmpleado = null;
        try {
            existeEmpleado = ejecutarQuery(query, collection);
        } catch (ExcepcionGestorBD ex) {
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Empleado empleadoLogin = new Empleado();
        String[] datos = null;
        ArrayList<String> datosLimpios = new ArrayList<String>();
        if(existeEmpleado != null){
            try {ResourceIterator iterator = existeEmpleado.getIterator();
                while(iterator.hasMoreResources()){
                    Resource res = iterator.nextResource();
                    String dato = (String) res.getContent();
                    datos = dato.split(">");
                    for(int i = 0; i < datos.length - 1; i++){
                        if(i % 2 == 0){
                            String[] datos1 = datos[i].split("<");
                            datosLimpios.add(datos1[0]);
                        }
                    }
                }
            } catch (XMLDBException ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
            empleadoLogin.setNombreusuario(datosLimpios.get(1));
            empleadoLogin.setPassword(datosLimpios.get(2));
            empleadoLogin.setNombre(datosLimpios.get(3));
            empleadoLogin.setApellidos(datosLimpios.get(4));
            empleadoLogin.setDireccion(datosLimpios.get(5));
            empleadoLogin.setTelefono(datosLimpios.get(6));
        }
        
        return empleadoLogin;
    }
    
    public Empleado crearObjetoEmpleado(Empleado e, String collection){
                
        String query = "for $empleado in //empleados/empleado where $empleado/usuario = '" + e.getNombreusuario() + "' return $empleado";
        ResourceSet existeEmpleado = null;
        try {
            existeEmpleado = ejecutarQuery(query, collection);
        } catch (ExcepcionGestorBD ex) {
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Empleado empleado = new Empleado();
        String[] datos = null;
        ArrayList<String> datosLimpios = new ArrayList<String>();
        if(existeEmpleado != null){
            try {ResourceIterator iterator = existeEmpleado.getIterator();
                while(iterator.hasMoreResources()){
                    Resource res = iterator.nextResource();
                    String dato = (String) res.getContent();
                    datos = dato.split(">");
                    for(int i = 0; i < datos.length - 1; i++){
                        if(i % 2 == 0){
                            String[] datos1 = datos[i].split("<");
                            datosLimpios.add(datos1[0]);
                        }
                    }
                }
            } catch (XMLDBException ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
            empleado.setNombreusuario(datosLimpios.get(1));
            empleado.setPassword(datosLimpios.get(2));
            empleado.setNombre(datosLimpios.get(3));
            empleado.setApellidos(datosLimpios.get(4));
            empleado.setDireccion(datosLimpios.get(5));
            empleado.setTelefono(datosLimpios.get(6));
        }
        
        return empleado;
    }
    
     //NO USADA
    public String imprimirResultado(ResourceSet resultado){
        ResourceIterator iterator;
        String resultadoString = "";
        try {
            iterator = resultado.getIterator();
            while(iterator.hasMoreResources()){
                    Resource res = iterator.nextResource();
                    resultadoString = (String) res.getContent();
            }
        } catch (XMLDBException ex) {
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultadoString;
    }
    
    public void obtenerIncidenciaPorId(){
        Integer idIncidencia = PideDatos.pideEntero("Introduzca el id de la incidencia que quiere mostrar: \n");
        boolean existe = comprobarExistenciaIncidencia(idIncidencia);
        if(existe){
            
            String query = "for $incidencia in //incidencias/incidencia[id = '" + idIncidencia + "'] let $id := $incidencia/id/text() let $origen := $incidencia/origen/text() "
                    + "let $destino := $incidencia/destino/text() let $tipo := $incidencia/tipo/text() "
                    + "let $detalle := $incidencia/detalle/text() let $fechahora := $incidencia/fechahora/text()"
                    + "return concat ($id , ',' , $origen , ',' , $destino , ',' , $tipo , ',' , $detalle , ',' , $fechahora)";
            
            try {
                ResourceSet resultado = ejecutarQuery(query, collection);
                try {System.out.println("A continuación se muestra la incidencia: \n");
                    ResourceIterator iterator = resultado.getIterator();
                    Resource res = iterator.nextResource();
                    String resultadoConcatenado = (String) res.getContent();
                    formatearIncidencia(resultadoConcatenado);
                
                } catch (XMLDBException ex) {
                    Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } catch (ExcepcionGestorBD ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("No hay incidencias para mostrar con el id indicado.\n");
        }
        
    }
    
    public void formatearIncidencia(String resultadoConcatenado){
        String[] resultadoSplitado = resultadoConcatenado.split(",");
        System.out.println("*****************************************");
        System.out.println("Id de la incidencia: " + resultadoSplitado[0] + "\n");
        System.out.println("Empleado Origen: " + resultadoSplitado[1] + "\n");
        System.out.println("Empleado Destino: " + resultadoSplitado[2] + "\n");
        System.out.println("Tipo: " + resultadoSplitado[3] + "\n");
        System.out.println("Detalle: " + resultadoSplitado[4] + "\n");
        System.out.println("Fecha y hora: " + resultadoSplitado[5] + "\n");
        System.out.println("*****************************************");
    }
       
    public void obtenerListadoIncidencias(){
                
        String query = "for $incidencia in //incidencias/incidencia let $id := $incidencia/id/text()let $origen := $incidencia/origen/text() "
                    + "let $destino := $incidencia/destino/text() let $tipo := $incidencia/tipo/text() "
                    + "let $detalle := $incidencia/detalle/text() let $fechahora := $incidencia/fechahora/text()"
                    + "return concat ($id , ',' , $origen , ',' , $destino , ',' , $tipo , ',' , $detalle , ',' , $fechahora)";
        
        try {
            ResourceSet resultado = ejecutarQuery(query, collection);
            ResourceIterator iterator = resultado.getIterator();
            while(iterator.hasMoreResources()){
                    Resource res = iterator.nextResource();
                    String incidenciaConcatenada = (String) res.getContent();
                    formatearIncidencia(incidenciaConcatenada);
                    
            }

        } catch (XMLDBException ex) {
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExcepcionGestorBD ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertarIncidencia(Empleado empleadoLogin){
        Incidencia nuevaIncidencia = crearIncidencia(empleadoLogin);
                    
        ResourceSet result = null;
        String consulta = "update insert <incidencia><id>" + nuevaIncidencia.getIdincidencia() + "</id> <origen>"
                + "" + nuevaIncidencia.getEmpleadoOrigen().getNombreusuario() + "</origen> <destino>" + nuevaIncidencia.getEmpleadoDestino().getNombreusuario() + "</destino> <tipo>"
                + "" + nuevaIncidencia.getTipo() + "</tipo> <detalle>" + nuevaIncidencia.getDetalle() + "</detalle> <fechahora>"
                + "" + nuevaIncidencia.getFechahora() + "</fechahora></incidencia> into /incidencias";
               
        try {
            col = DatabaseManager.getCollection(URI + collection, usuario, usuarioPwd);
            XQueryService service = (XQueryService) col.getService("XQueryService",
                    "1.0");
            service.setProperty(OutputKeys.INDENT, "yes");
            service.setProperty(OutputKeys.ENCODING, "UTF-8");
            CompiledExpression compiled = service.compile(consulta);
            result = service.execute(compiled);
            System.out.println("Incidencia insetada con éxito.\n");
        } catch (XMLDBException e) {
            try {
                throw new ExcepcionGestorBD("Error ejecutando query: " + e.getMessage());
            } catch (ExcepcionGestorBD ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(nuevaIncidencia.getTipo().equals(Prioridad.URGENTE)){
            try {String fechaHora = fv.obtenerFechaHora();
                HistorialXND hXND = new HistorialXND();
                hXND.insertarEvento(TipoEvento.U, empleadoLogin, fechaHora);
            } catch (ExcepcionGestorBD ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
           
    }
    
    public void seleccionarIncidenciasPorEmpleadoDestino(Empleado empleadoLogin){
        Empleado empleadoDestino = new Empleado();
        boolean existe = false;
        try {
            empleadoDestino.setNombreusuario(PideDatos.pideString("Introduzca el nombre de usuario del empleado destino a obtener incidencias: \n"));
        } catch (IOException ex) {
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
        existe = comprobarExistenciaEmpleado(empleadoDestino, collection);
        
        if(!existe){
            System.out.println("El empleado destino introducido no existe. \n");
        }else{
            String query = "for $incidencia in //incidencias/incidencia[destino = '" + empleadoDestino.getNombreusuario() + "'] let $id := $incidencia/id/text() let $origen := $incidencia/origen/text() "
                    + "let $destino := $incidencia/destino/text() let $tipo := $incidencia/tipo/text() "
                    + "let $detalle := $incidencia/detalle/text() let $fechahora := $incidencia/fechahora/text()"
                    + "return concat ($id , ',' , $origen , ',' , $destino , ',' , $tipo , ',' , $detalle , ',' , $fechahora)";
                           
            try {
                ResourceSet resultado = ejecutarQuery(query, collection);
                if(resultado != null){
                    ResourceIterator iterator = resultado.getIterator();
                    while(iterator.hasMoreResources()){
                            Resource res = iterator.nextResource();
                            String incidenciaConcatenada = (String) res.getContent();
                            formatearIncidencia(incidenciaConcatenada);
                    }
                }else{
                    System.out.println("No existen incidencias para la consulta realizada. \n");
                }
            } catch (ExcepcionGestorBD ex) {
                    Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            } catch (XMLDBException ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {String fechaHora = fv.obtenerFechaHora();
                HistorialXND hXND = new HistorialXND();
                hXND.insertarEvento(TipoEvento.C, empleadoLogin, fechaHora);
            } catch (ExcepcionGestorBD ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void seleccionarIncidenciasPorEmpleadoOrigen(){
        Empleado empleadoOrigen = new Empleado();
        boolean existe = false;
        try {
            empleadoOrigen.setNombreusuario(PideDatos.pideString("Introduzca el nombre de usuario del empleado origen a obtener incidencias: \n"));
        } catch (IOException ex) {
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
        existe = comprobarExistenciaEmpleado(empleadoOrigen, collection);
        
        if(!existe){
            System.out.println("El empleado destino introducido no existe. \n");
        }else{
            String query = "for $incidencia in //incidencias/incidencia[origen = '" + empleadoOrigen.getNombreusuario() + "'] let $id := $incidencia/id/text() let $origen := $incidencia/origen/text() "
                    + "let $destino := $incidencia/destino/text() let $tipo := $incidencia/tipo/text() "
                    + "let $detalle := $incidencia/detalle/text() let $fechahora := $incidencia/fechahora/text()"
                    + "return concat ($id , ',' , $origen , ',' , $destino , ',' , $tipo , ',' , $detalle , ',' , $fechahora)";
               
            try {
                ResourceSet resultado = ejecutarQuery(query, collection);
                if(resultado != null){
                    ResourceIterator iterator = resultado.getIterator();
                    while(iterator.hasMoreResources()){
                            Resource res = iterator.nextResource();
                            String incidenciaConcatenada = (String) res.getContent();
                            formatearIncidencia(incidenciaConcatenada);
                    }
                }else{
                    System.out.println("No existen incidencias para la consulta realizada. \n");
                }
            } catch (ExcepcionGestorBD ex) {
                    Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            } catch (XMLDBException ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
            //hBDOR.insertarEvento(TipoEvento.C, empleadoLogin, fv, gDao);
        }
        
    }
    
    public boolean comprobarExistenciaIncidencia(int idIncidencia){
        boolean existe = false;
        String query = "for $incidencia in //incidencias/incidencia/id where $incidencia = '" + idIncidencia + "' return $incidencia";

        try {
            existe = ejecutarQueryBoolean(query, collection);
        } catch (ExcepcionGestorBD ex) {
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe; 
    }
    
    public Incidencia crearIncidencia(Empleado empleadoLogin){
        Incidencia nuevaIncidencia = new Incidencia();
        Empleado empleadoDestino = new Empleado();
        FuncionesVariadas fv = new FuncionesVariadas();
                
        boolean existe = false;
        
        try {nuevaIncidencia.setIdincidencia(obtenerSiguienteId());
            nuevaIncidencia.setEmpleadoOrigen(empleadoLogin);
            do{empleadoDestino.setNombreusuario(PideDatos.pideString("Introduzca el nombre de usuario del empleado destino; \n"));
                existe = comprobarExistenciaEmpleado(empleadoDestino, collection);
                if(existe){
                    empleadoDestino = crearObjetoEmpleado(empleadoDestino, collection);
                    nuevaIncidencia.setEmpleadoDestino(empleadoDestino);
                }else{
                    System.out.println("El empleado introducido no existe");
                }
            }while(!existe);
            nuevaIncidencia.setFechahora(fv.obtenerFechaHora());
            nuevaIncidencia.setDetalle(PideDatos.pideString("Introduzca la descripción de la incidencia: \n"));
            nuevaIncidencia.setTipo(fv.solicitarPrioridad());
        } catch (IOException ex) {
            System.out.println("Error insertando incidencia " + ex.getMessage());
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nuevaIncidencia;
    }
    
    public void obtenerNumeroIncidenciasBD(){
        int contadorIncidencias = 0;
        String query = "for $incidencia in //incidencias/incidencia return $incidencia";
                
        try {ResourceSet resultado = ejecutarQuery(query, collection);
            if(resultado != null){
                
                ResourceIterator iterator = resultado.getIterator();
                while(iterator.hasMoreResources()){
                        Resource res = iterator.nextResource();
                        contadorIncidencias ++;
                }
                    
                }else{
                    System.out.println("No existen incidencias en la base de datos. \n");
                }
            System.out.println("El número de incidencias registradas en la base de datos es: " + contadorIncidencias + "\n");

        } catch (XMLDBException ex) {
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExcepcionGestorBD ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }  
    
    public int obtenerSiguienteId(){
        String query = "for $incidencia in //incidencias/incidencia/id return $incidencia";
        int contadorId = 1;
        try {
            ResourceSet resultado = ejecutarQuery(query, collection);
            ResourceIterator iterator = resultado.getIterator();
            while(iterator.hasMoreResources()){
                Resource res = iterator.nextResource();
                contadorId ++;
                    
            }

        } catch (XMLDBException ex) {
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExcepcionGestorBD ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(contadorId);
        return contadorId;
    }
}