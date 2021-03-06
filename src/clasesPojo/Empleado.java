package clasesPojo;
// Generated 04-mar-2020 12:52:09 by Hibernate Tools 4.3.1

/**
 * Empleado generated by hbm2java
 */
public class Empleado  implements java.io.Serializable {


     private String nombreusuario;
     private String password;
     private String nombre;
     private String apellidos;
     private String direccion;
     private String telefono;
     

    public Empleado() {
    }

	
    public Empleado(String nombreusuario, String password, String nombre, String apellidos, String telefono) {
        this.nombreusuario = nombreusuario;
        this.password = password;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
    }
      
    public String getNombreusuario() {
        return this.nombreusuario;
    }
    
    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellidos(){
        return apellidos;
    }
    
    public void setApellidos(String apellidos){
        this.apellidos = apellidos;
    }
    
    public String getDireccion(){
        return direccion;
    }
    
    public void setDireccion(String direccion){
        this.direccion = direccion;
    }
    
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Empleado: Nombre de usuario= " + nombreusuario + " --- Password= " + password + " --- Nombre= " + nombre + " --- Apellidos= " + apellidos + " --- Direccion= " + direccion + " --- Telefono= " + telefono + "\n";
    }
}