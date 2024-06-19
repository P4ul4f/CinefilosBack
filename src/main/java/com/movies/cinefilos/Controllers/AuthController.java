package com.movies.cinefilos.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/method")
public class AuthController {


    @GetMapping("/get")
    public String helloGet(){
        return "hello - GET";
    }

    @PostMapping("/post")
    public String helloPost(){
        return "hello - POST";
    }

    @PutMapping("/put")
    public String helloPut(){
        return "hello - PUT";
    }

    @DeleteMapping("/delete")
    public String helloDelete(){
        return "hello - DELETE";
    }

    @PatchMapping("/patch")
    public String helloPatch(){
        return "hello - PATCH";
    }

    // Endpoint para registrar un nuevo usuario
    /*@PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> registerUserAccount(@ModelAttribute("user") UserRegisterDTO userRegisterDTO) {
        userService.saveUser(userRegisterDTO);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    // Endpoint para iniciar sesión (simulado, adaptar según tu lógica)
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login() {
        // Aquí puedes implementar la lógica de autenticación si es necesario
        return ResponseEntity.ok("Inicio de sesión exitoso");
    }*/
}
