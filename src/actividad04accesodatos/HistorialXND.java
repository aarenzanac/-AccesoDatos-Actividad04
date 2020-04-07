package actividad04accesodatos;


import PideDatos.PideDatos;
import static actividad04accesodatos.IncidenciasXND.URI;
import clasesPojo.Empleado;
import clasesPojo.Historial;
import clasesPojo.Incidencia;
import java.util.ArrayList;
import enums.TipoEvento;
import excepciones.ExcepcionGestorBD;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.xml.transform.OutputKeys;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;


/**
 *
 * @author USUARIO
 */
public class HistorialXND {
    
    protected static String driver = "org.exist.xmldb.DatabaseImpl";
    public static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private Database database;
    private Collection col;
    private final String usuario;
    private final String usuarioPwd;
    private String collection;
    private String nombre_recurso;
    FuncionesVariadas fv = new FuncionesVariadas();
    
    
     public HistorialXND() throws ExcepcionGestorBD {
        this.database = null;
        this.usuario = "admin";
        this.usuarioPwd = "root";
        collection = "/db/Incidencias/Historial/";
        nombre_recurso = "historial.xml";
        
    }
    
    public Collection conectarBDHistorial() throws ExcepcionGestorBD {
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
            /*if (col.getResourceCount() == 0) {
                //si la collección no tiene recursos no podrá devolver ninguno
                System.out.println("La colección no tiene recursos..."
                        + "No puede devolver ninguno [FIN]");
                return null;

            } else {
                System.out.println("...La colección no es nula...");
                Resource res = null;
                res = (Resource) col.getResource(nombre_recurso);
                System.out.println("De la colección saca el recurso que tiene la "
                        + "variable nombre_recurso:" + nombre_recurso);
                XMLResource xmlres = (XMLResource) res;
                System.out.println("La salida es:\n" + xmlres.getContent());
                System.out.println("El tipo es:" + xmlres.getResourceType());
                
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
    
    public void insertarEvento(TipoEvento tipo, Empleado e, String fechaHora){
        
        Historial nuevoEvento = new Historial();
        
        nuevoEvento.setIdevento(obtenerSiguienteId());
        nuevoEvento.setEmpleado(e);
        nuevoEvento.setTipo(tipo);
        nuevoEvento.setFechahora(fv.obtenerFechaHora());
                    
        ResourceSet result = null;
        String consulta = "update insert <evento> <id>" + nuevoEvento.getIdevento()+ "</id> <tipoevento>"
                + "" + nuevoEvento.getTipo() + "</tipoevento> <fechahora>" + nuevoEvento.getFechahora() + "</fechahora> <empleado>"
                + "" + nuevoEvento.getEmpleado().getNombreusuario() + "</empleado> </evento> into /historial";
               
        try {
            col = DatabaseManager.getCollection(URI + collection, usuario, usuarioPwd);
            XQueryService service = (XQueryService) col.getService("XQueryService",
                    "1.0");
            service.setProperty(OutputKeys.INDENT, "yes");
            service.setProperty(OutputKeys.ENCODING, "UTF-8");
            CompiledExpression compiled = service.compile(consulta);
            result = service.execute(compiled);
            System.out.println("Evento insertado con éxito.\n");
        } catch (XMLDBException ex1) {
            try {
                throw new ExcepcionGestorBD("Error ejecutando query: " + ex1.getMessage());
            } catch (ExcepcionGestorBD ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    
    }
    
    public int obtenerSiguienteId(){
        String query = "for $historial in //historial/evento/id return $historial";
        int contadorId = 0;
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
    
    public void formatearEvento(String resultadoConcatenado){
        String[] resultadoSplitado = resultadoConcatenado.split(",");
        System.out.println("*****************************************");
        System.out.println("Id del evento: " + resultadoSplitado[0] + "\n");
        System.out.println("Tipo del evento: " + resultadoSplitado[1] + "\n");
        System.out.println("Fecha y hora del evento: " + resultadoSplitado[2] + "\n");
        System.out.println("Creado por: " + resultadoSplitado[3] + "\n");
        System.out.println("*****************************************");
    }
    
    public void obtenerListadoIniciosSesion(){
        String query = "for $evento in //historial/evento[tipoevento = 'I'] let $id := $evento/id/text() "
                + "let $tipoevento := $evento/tipoevento/text() let $fechahora := $evento/fechahora/text() let $empleado := $evento/empleado/text() "
                + "return concat ($id , ',' , $tipoevento , ',' , $fechahora , ',' , $empleado)";
        
        
        try {ResourceSet resultado = ejecutarQuery(query, collection);
            if(resultado != null){
                
                ResourceIterator iterator = resultado.getIterator();
                while(iterator.hasMoreResources()){
                        Resource res = iterator.nextResource();
                        String resultadoConcatenado = (String) res.getContent();
                        formatearEvento(resultadoConcatenado);

                }
                    
                }else{
                    System.out.println("No existen incidencias para la consulta realizada. \n");
                }
            

        } catch (XMLDBException ex) {
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExcepcionGestorBD ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void obtenerEmpleadosConsultaIncidencias(){
        String query = "for $evento in distinct-values(doc('historial.xml')/historial/empleado $evento1 in //historial/evento[tipoevento = 'C']"
                + " return ($evento)";
                
        try {ResourceSet resultado = ejecutarQuery(query, collection);
            if(resultado != null){
                
                ResourceIterator iterator = resultado.getIterator();
                System.out.println("Listado de empleados que han consultado sus incidencias: \n");
                while(iterator.hasMoreResources()){
                        Resource res = iterator.nextResource();
                        System.out.println((String) res.getContent() + "\n");
                }
                    
            }else{
                System.out.println("No existen incidencias para la consulta realizada. \n");
            }
        } catch (XMLDBException ex) {
            Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExcepcionGestorBD ex) {
                Logger.getLogger(IncidenciasXND.class.getName()).log(Level.SEVERE, null, ex);
        }
    }              
}