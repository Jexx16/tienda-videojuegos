package com.tienda.controller;

import com.tienda.model.Usuario;
import com.tienda.model.Venta;
import com.tienda.repository.UsuarioRepository;
import com.tienda.service.VentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "http://localhost:8080")
public class VentaController {

    private final VentaService ventaService;
    private final UsuarioRepository usuarioRepository;

    public VentaController(VentaService ventaService, UsuarioRepository usuarioRepository) {
        this.ventaService = ventaService;
        this.usuarioRepository = usuarioRepository;
    }

    // ========== PROCESAR COMPRA ==========
    @PostMapping("/procesar")
    public ResponseEntity<?> procesarCompra(@RequestBody Map<String, Long> payload) {
        try {
            Long usuarioId = payload.get("usuarioId");
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
            
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Usuario no encontrado");
            }
            
            Venta venta = ventaService.procesarCompra(usuarioOpt.get());
            return ResponseEntity.ok(venta);
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al procesar la compra");
        }
    }

    // ========== HISTORIAL DE COMPRAS DEL USUARIO ==========
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> historialCompras(@PathVariable Long usuarioId) {
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Usuario no encontrado");
            }
            
            List<Venta> historial = ventaService.obtenerHistorialCompras(usuarioOpt.get());
            return ResponseEntity.ok(historial);
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener historial");
        }
    }
}