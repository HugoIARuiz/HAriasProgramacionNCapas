/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.HAriasProgramacionNCapas.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("saludo")
    public String HolaMundo() {
        return "HolaMundo";
    }

    @GetMapping("saludoPath/{nombre}")
    public String SaludoPath(@PathVariable String nombre) {
        return "HolaMundo";
    }

    @GetMapping("operaciones/{op}/numeroUno/{numeroUno}/numeroDos/{numeroDos}")
    public String Operaciones(@PathVariable String op, @PathVariable int numeroUno, @PathVariable int numeroDos) {
        if (op.toLowerCase().equals("suma")) {
            int resultado = numeroUno + numeroDos;
            System.out.println("El resultado de la suma es: " + resultado);

        } else if (op.toLowerCase().equals("resta")) {
            int resultado = numeroUno - numeroDos;
            System.out.println("El resultado de la resta es: " + resultado);

        } else if (op.toLowerCase().equals("multiplicacion")) {
            int resultado = numeroUno * numeroDos;
            System.out.println("El resultado de la multiplicación es: " + resultado);
        } else if (op.toLowerCase().equals("division")) {
            if (numeroDos > 0) {
                double resultado = numeroUno / numeroDos;
                System.out.println("El resultado de la división es: " + resultado);
            } else {
                System.out.println("No es posible dividir entre 0");
            }
        } else {
            System.out.println("Ingresa una operacion válida");
        }
        return "HolaMundo";
    }

}

