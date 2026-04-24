package com.tienda.controller;

import com.tienda.model.Juego;
import com.tienda.model.Usuario;
import com.tienda.repository.JuegoRepository;
import com.tienda.repository.UsuarioRepository;
import com.tienda.service.VentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:8080")
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final JuegoRepository juegoRepository;
    private final VentaService ventaService;

    public AdminController(UsuarioRepository usuarioRepository,
                           JuegoRepository juegoRepository,
                           VentaService ventaService) {
        this.usuarioRepository = usuarioRepository;
        this.juegoRepository = juegoRepository;
        this.ventaService = ventaService;
    }

    // ========== ESTADÍSTICAS ==========
    @GetMapping("/stats")
    public ResponseEntity<?> obtenerEstadisticas() {
        try {
            Map<String, Object> stats = ventaService.obtenerEstadisticas();
            stats.put("totalUsuarios", usuarioRepository.count());
            stats.put("totalJuegos", juegoRepository.count());
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener estadísticas");
        }
    }

    // ========== GESTIÓN DE JUEGOS ==========
    @GetMapping("/juegos")
    public List<Juego> listarJuegos() {
        return juegoRepository.findAll();
    }

    @GetMapping("/juegos/{id}")
    public ResponseEntity<Juego> obtenerJuego(@PathVariable Long id) {
        return juegoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/juegos")
    public ResponseEntity<?> crearJuego(@RequestBody Juego juego) {
        try {
            if (juego.getTitulo() == null || juego.getTitulo().trim().isEmpty()) {
                return ResponseEntity.status(400).body("El título es obligatorio");
            }
            
            if (juego.getPrecio() == null || juego.getPrecio() <= 0) {
                return ResponseEntity.status(400).body("El precio debe ser mayor a 0");
            }
            
            if (juego.getStock() == null || juego.getStock() < 0) {
                juego.setStock(0);
            }
            
            juego.setDisponible(juego.getStock() > 0);
            Juego nuevo = juegoRepository.save(juego);
            return ResponseEntity.ok(nuevo);
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear juego: " + e.getMessage());
        }
    }

    @PutMapping("/juegos/{id}")
    public ResponseEntity<?> actualizarJuego(@PathVariable Long id, @RequestBody Juego juego) {
        try {
            Juego existente = juegoRepository.findById(id).orElse(null);
            if (existente == null) {
                return ResponseEntity.notFound().build();
            }
            
            existente.setTitulo(juego.getTitulo());
            existente.setCategoria(juego.getCategoria());
            existente.setPlataforma(juego.getPlataforma());
            existente.setPrecio(juego.getPrecio());
            existente.setDescripcion(juego.getDescripcion());
            existente.setStock(juego.getStock());
            existente.setDisponible(juego.getStock() > 0);
            
            juegoRepository.save(existente);
            return ResponseEntity.ok(existente);
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar: " + e.getMessage());
        }
    }

    @DeleteMapping("/juegos/{id}")
    public ResponseEntity<?> eliminarJuego(@PathVariable Long id) {
        try {
            juegoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar: " + e.getMessage());
        }
    }

    // ========== GESTIÓN DE USUARIOS ==========
    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar usuario");
        }
    }
}