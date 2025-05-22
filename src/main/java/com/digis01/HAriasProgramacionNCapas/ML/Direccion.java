package com.digis01.HAriasProgramacionNCapas.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Direccion {

    private int IdDireccion;
    @NotBlank(message = "Ingresa una calle")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Ingresa un nombre de Calle válido")
    @Size(min = 3, max = 50, message = "Entre 3 y 50 caracteres")
    private String Calle;
    @Pattern(regexp = "^[0-9A-Z-\\/\\s]*$", message = "Solo puede contener números, letras mayúsculas y guiones")
    private String NumeroInterior;
    @NotBlank(message = "Ingresa un número exterior, si no cuenta escriba S/N")
    @Pattern(regexp = "[0-9A-Z-\\/\\s]*", message = "Solo puede contener números, letras mayúsculas y guiones")
    private String NumeroExterior;
    @Valid
    public Colonia Colonia;

    public int getIdDireccion() {
        return IdDireccion;
    }

    public void setIdDireccion(int IdDireccion) {
        this.IdDireccion = IdDireccion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroInterior(String NumeroInterior) {
        this.NumeroInterior = NumeroInterior;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }

    public Colonia getColonia() {
        return Colonia;
    }

    public void setColonia(Colonia Colonia) {
        this.Colonia = Colonia;
    }
    


}
