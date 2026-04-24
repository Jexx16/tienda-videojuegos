package com.tienda.controller;

import com.tienda.model.CarritoItem;
import com.tienda.model.Juego;
import com.tienda.model.Usuario;
import com.tienda.repository.CarritoItemRepository;
import com.tienda.repository.JuegoRepository;
import com.tienda.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "http://localhost:8080")
public class CarritoController {

    private final CarritoItemRepository carritoRepository;
    private final JuegoRepository juegoRepository;
    private final UsuarioRepository usuarioRepository;

    public CarritoController(CarritoItemRepository carritoRepository,
                             JuegoRepository juegoRepository,
                             UsuarioRepository usuarioRepository) {
        this.carritoRepository = carritoRepository;
        this.juegoRepository = juegoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // ========== AGREGAR AL CARRITO ==========
    @PostMapping("/agregar")
    public ResponseEntity<?> agregarAlCarrito(@RequestBody Map<String, Object> payload) {
        try {
            Long usuarioId = Long.valueOf(payload.get("usuarioId").toString());
            Long juegoId = Long.valueOf(payload.get("juegoId").toString());
            Integer cantidad = Integer.valueOf(payload.get("cantidad").toString());

            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
            Optional<Juego> juegoOpt = juegoRepository.findById(juegoId);

            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Usuario no encontrado");
            }
            if (juegoOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Juego no encontrado");
            }

            Usuario usuario = usuarioOpt.get();
            Juego juego = juegoOpt.get();

            // Verificar stock
            if (juego.getStock() < cantidad) {
                return ResponseEntity.status(400).body("No hay suficiente stock disponible");
            }

            // Verificar si el juego ya está en el carrito
            Optional<CarritoItem> existente = carritoRepository.findByUsuarioAndJuego(usuario, juego);

            if (existente.isPresent()) {
                // Actualizar cantidad
                CarritoItem item = existente.get();
                item.setCantidad(item.getCantidad() + cantidad);
                carritoRepository.save(item);
            } else {
                // Crear nuevo item
                CarritoItem nuevoItem = new CarritoItem(usuario, juego, cantidad, juego.getPrecio());
                carritoRepository.save(nuevoItem);
            }

            return ResponseEntity.ok("Juego agregado al carrito");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al agregar al carrito: " + e.getMessage());
        }
    }

    // ========== OBTENER CARRITO DEL USUARIO ==========
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerCarrito(@PathVariable Long usuarioId) {
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Usuario no encontrado");
            }

            List<CarritoItem> items = carritoRepository.findByUsuario(usuarioOpt.get());
            
            double total = items.stream()
                    .mapToDouble(CarritoItem::getSubtotal)
                    .sum();

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("items", items);
            respuesta.put("total", total);
            respuesta.put("cantidadItems", items.size());

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener carrito");
        }
    }

    // ========== ACTUALIZAR CANTIDAD ==========
    @PutMapping("/actualizar/{itemId}")
    public ResponseEntity<?> actualizarCantidad(@PathVariable Long itemId, @RequestBody Map<String, Integer> payload) {
        try {
            Integer nuevaCantidad = payload.get("cantidad");
            Optional<CarritoItem> itemOpt = carritoRepository.findById(itemId);

            if (itemOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Item no encontrado");
            }

            CarritoItem item = itemOpt.get();
            
            if (nuevaCantidad <= 0) {
                carritoRepository.delete(item);
                return ResponseEntity.ok("Item eliminado del carrito");
            }

            // Verificar stock
            if (item.getJuego().getStock() < nuevaCantidad) {
                return ResponseEntity.status(400).body("No hay suficiente stock disponible");
            }

            item.setCantidad(nuevaCantidad);
            carritoRepository.save(item);

            return ResponseEntity.ok("Carrito actualizado");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar");
        }
    }

    // ========== ELIMINAR ITEM DEL CARRITO ==========
    @DeleteMapping("/eliminar/{itemId}")
    public ResponseEntity<?> eliminarItem(@PathVariable Long itemId) {
        try {
            carritoRepository.deleteById(itemId);
            return ResponseEntity.ok("Item eliminado del carrito");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar");
        }
    }

    // ========== VACIAR CARRITO ==========
    @DeleteMapping("/vaciar/{usuarioId}")
    public ResponseEntity<?> vaciarCarrito(@PathVariable Long usuarioId) {
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Usuario no encontrado");
            }
            carritoRepository.deleteByUsuario(usuarioOpt.get());
            return ResponseEntity.ok("Carrito vaciado");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al vaciar carrito");
        }
    }
}