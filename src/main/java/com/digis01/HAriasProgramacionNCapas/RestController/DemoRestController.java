package com.digis01.HAriasProgramacionNCapas.RestController;

import com.digis01.HAriasProgramacionNCapas.ML.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demoapi")
public class DemoRestController {

    @GetMapping("saludo/{nombre}")
    public String Saludo(@PathVariable String nombre) {
        return "Hola Mundo " + nombre;
    }

    @GetMapping("saludo2")
    public ResponseEntity Saludo2() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Hugo Isaac");
        usuario.setApellidoPaterno("Arias");
        usuario.setApellidoMaterno("Ruiz");
        usuario.setUsername("shecallsmeHoney");
        usuario.setEmail("hugoar01@outlook.com");
        usuario.setCelular("7226795549");
        usuario.setCurp("AIRH010627HMCRZGA5");

        return ResponseEntity.accepted().body(usuario);
    }

    @GetMapping("suma/numero1/{num1}/numero2/{num2}")
    public ResponseEntity Suma(@PathVariable int num1, @PathVariable int num2) {
        int resultado = num1 + num2;

        return ResponseEntity.ok("Resultado de sumar " + num1 + " + " + num2 + " = " + resultado);

    }

    @GetMapping("resta/num1/{num1}/num2/{num2}")
    public ResponseEntity Resta(@PathVariable int num1, @PathVariable int num2) {
        int resultado = num1 - num2;

        return ResponseEntity.accepted().body("Resultado de restar " + num1 + " + " + num2 + " = " + resultado);
    }

    @GetMapping("multiplicacion/num1/{num1}/num2/{num2}")
    public ResponseEntity Multiplicacion(@PathVariable int num1, @PathVariable int num2) {
        int resultado = num1 * num2;

        return ResponseEntity.accepted().body("Resultado de multiplicar " + num1 + " + " + num2 + " = " + resultado);
    }

    @GetMapping("division/num1/{num1}/num2/{num2}")
    public ResponseEntity Division(@PathVariable int num1, @PathVariable int num2) {
        if (num2 == 0) {
            return ResponseEntity.badRequest().body("No es posible dividir entre 0");
        }
        double resultado = num1 / num2;
        return ResponseEntity.accepted().body("Resultado de dividir " + num1 + " + " + num2 + " = " + resultado);
    }

}
