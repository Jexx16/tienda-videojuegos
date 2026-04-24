package com.tienda.controller;

import com.tienda.model.Usuario;
import com.tienda.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:8080")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // ========== REGISTRO ==========
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        System.out.println("=== INTENTO DE REGISTRO ===");
        System.out.println("Usuario: " + usuario.getNombre());
        System.out.println("Email: " + usuario.getEmail());
        
        try {
            // Validar campos obligatorios
            if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
                return ResponseEntity.status(400).body("El nombre de usuario es obligatorio");
            }
            
            if (usuario.getPassword() == null || usuario.getPassword().length() < 6) {
                return ResponseEntity.status(400).body("La contraseña debe tener al menos 6 caracteres");
            }
            
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                return ResponseEntity.status(400).body("El email es obligatorio");
            }
            
            // Verificar si el usuario ya existe
            if (usuarioRepository.existsByNombre(usuario.getNombre())) {
                return ResponseEntity.status(400).body("El nombre de usuario ya existe");
            }
            
            // Crear nuevo usuario (por defecto rol = "USER")
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(usuario.getNombre().trim());
            nuevoUsuario.setPassword(usuario.getPassword());
            nuevoUsuario.setEmail(usuario.getEmail().trim());
            nuevoUsuario.setRol("USER");
            nuevoUsuario.setFechaRegistro(LocalDateTime.now());
            
            Usuario guardado = usuarioRepository.save(nuevoUsuario);
            System.out.println("Usuario registrado con ID: " + guardado.getId());
            
            return ResponseEntity.ok(guardado);
            
        } catch (Exception e) {
            System.out.println("Error en registro: " + e.getMessage());
            return ResponseEntity.status(500).body("Error interno al registrar usuario");
        }
    }

    // ========== LOGIN ==========
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        System.out.println("=== INTENTO DE LOGIN ===");
        String nombre = credenciales.get("nombre");
        String password = credenciales.get("password");
        
        System.out.println("Usuario: " + nombre);
        
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findByNombreAndPassword(nombre, password);
            
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                System.out.println("Login exitoso - Rol: " + usuario.getRol());
                
                // Crear respuesta sin enviar la contraseña
                Map<String, Object> respuesta = new HashMap<>();
                respuesta.put("id", usuario.getId());
                respuesta.put("nombre", usuario.getNombre());
                respuesta.put("email", usuario.getEmail());
                respuesta.put("rol", usuario.getRol());
                
                return ResponseEntity.ok(respuesta);
            } else {
                System.out.println("Login fallido: credenciales incorrectas");
                return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
            }
        } catch (Exception e) {
            System.out.println("Error en login: " + e.getMessage());
            return ResponseEntity.status(500).body("Error interno del servidor");
        }
    }
}