
package com.digis01.HAriasProgramacionNCapas.ML;

public class ResultFile {
    
    private int fila;
    private String mensaje;
    private String descripcion;
    
    public int getFila(){
        return fila;
    }
    public void setFila(int fila){
        this.fila = fila;
    }
    
    public String getMensaje(){
        return mensaje;
    }
    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public ResultFile(int fila, String mensaje, String descripcion) {
        this.fila = fila;
        this.mensaje = mensaje;
        this.descripcion = descripcion;
    }
    public ResultFile(){
        
    }
    
    
}
