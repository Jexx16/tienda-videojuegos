package com.tienda.controller;

import com.tienda.model.Juego;
import com.tienda.repository.JuegoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/juegos")
@CrossOrigin(origins = "http://localhost:8080")
public class JuegoController {

    private final JuegoRepository juegoRepository;

    public JuegoController(JuegoRepository juegoRepository) {
        this.juegoRepository = juegoRepository;
    }

    // ========== OBTENER TODOS LOS JUEGOS ==========
    @GetMapping("/todos")
    public List<Juego> obtenerTodos() {
        return juegoRepository.findAll();
    }

    // ========== OBTENER JUEGOS DISPONIBLES ==========
    @GetMapping("/disponibles")
    public List<Juego> obtenerDisponibles() {
        return juegoRepository.findByDisponibleTrue();
    }

    // ========== BUSCAR POR TÍTULO ==========
    @GetMapping("/buscar/{titulo}")
    public List<Juego> buscarPorTitulo(@PathVariable String titulo) {
        return juegoRepository.findByTituloContainingIgnoreCase(titulo);
    }

    // ========== BUSCAR POR CATEGORÍA ==========
    @GetMapping("/categoria/{categoria}")
    public List<Juego> buscarPorCategoria(@PathVariable String categoria) {
        return juegoRepository.findByCategoriaIgnoreCase(categoria);
    }

    // ========== BUSCAR POR PLATAFORMA ==========
    @GetMapping("/plataforma/{plataforma}")
    public List<Juego> buscarPorPlataforma(@PathVariable String plataforma) {
        return juegoRepository.findByPlataformaIgnoreCase(plataforma);
    }

    // ========== BUSCAR POR TÉRMINO GENERAL ==========
    @GetMapping("/buscar")
    public List<Juego> buscarPorTermino(@RequestParam String q) {
        return juegoRepository.buscarPorTermino(q);
    }

    // ========== OBTENER JUEGO POR ID ==========
    @GetMapping("/{id}")
    public ResponseEntity<Juego> obtenerJuego(@PathVariable Long id) {
        return juegoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}