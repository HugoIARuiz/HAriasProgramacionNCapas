package com.digis01.HAriasProgramacionNCapas.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Usuario {

    private int IdUsuario;
    @NotBlank(message = "Ingresa un nombre, ")
    @Size(min = 3, max = 50, message = "Entre 3 y 50 caracteres")
    private String Nombre;
    @NotBlank(message = "Ingresa un apellido, ")
    @Size(min = 2, max = 50, message = "Entre 2 y 50 caracteres")
    private String ApellidoPaterno;
    @Size(max = 50, message = "No más de 50 caracteres")
    private String ApellidoMaterno;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message= "Ingresa una fecha de nacimiento")
    @Past(message = "La fecha de nacimiento debe estar en el pasado")
    private Date FechaNacimiento;
    @NotBlank(message = "Ingresa un número de Teléfono, ")
    @Size(min = 10, max = 20, message = "Entre 10 y 20 caracteres")
    private String Telefono;
    @NotBlank(message = "Ingresa un Username, ")
    @Size(min = 3, max = 50, message = "Entre 3 y 50 caracteres")
    private String Username;
    @NotBlank(message = "Ingresa una dirección de Email")
    @Email(message = "Ingresa una dirección de correo valida")
    private String Email;
    @NotBlank(message = "Ingresa un Password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W])[A-Za-z\\d\\W]{8,}$", message = "La contraseña debe contener al menos una letra Mayúscula, una minúscula, un número y un carácter especial")
    @Size(min = 8, message = "La contraseña debe contener al menos 8 caracteres")
    private String Password;
    @NotBlank(message = "Ingresa tu sexo")
    @Size(min = 1,max= 2, message = "Entre 1 y 2 caracteres")
    @Pattern(regexp = "(FM|MS)", message = "Ingresa un Sexo válido MS: Masculino FM: Femenino")
    private String Sexo;
    @Size(max = 20, message = "Entre 10 y 20 caracteres")
    private String Celular;
    @Size(max = 18, message = "La CURP debe contener 18 caracteres")
    @Pattern(regexp = "^([A-Z]{4}\\d{6}[HM][A-Z]{2}[B-DF-HJ-NP-TV-Z]{3}[A-Z\\d]\\d)*$", message = "CURP Inválida")
    private String Curp;
    @Valid
    public Rol Rol;
    private String Imagen;
    private int Status;

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public String getCurp() {
        return Curp;
    }

    public void setCurp(String Curp) {
        this.Curp = Curp;
    }
    
    public int getStatus(){
        return Status;
    }
    
    public void setStatus(int Status){
        this.Status = Status;
    }

    public Rol getRol() {
        return Rol;
    }

    public void setRol(Rol Rol) {
        this.Rol = Rol;
    }

    

    public Usuario() {
    }

    public Usuario(int IdUsuario, String Nombre, String ApellidoPaterno, String ApellidoMaterno, Date FechaNacimiento, String Telefono, String Username, String Email, String Password, String Sexo, String Celular, String Curp, Rol Rol) {
        this.IdUsuario = IdUsuario;
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.FechaNacimiento = FechaNacimiento;
        this.Telefono = Telefono;
        this.Username = Username;
        this.Email = Email;
        this.Password = Password;
        this.Sexo = Sexo;
        this.Celular = Celular;
        this.Curp = Curp;
        this.Rol = Rol;
    }

}
