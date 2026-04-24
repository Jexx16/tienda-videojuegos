package com.tienda.service;

import com.tienda.model.*;
import com.tienda.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class VentaService {

    private final CarritoItemRepository carritoRepository;
    private final JuegoRepository juegoRepository;
    private final VentaRepository ventaRepository;
    private final ItemVentaRepository itemVentaRepository;

    public VentaService(CarritoItemRepository carritoRepository,
                        JuegoRepository juegoRepository,
                        VentaRepository ventaRepository,
                        ItemVentaRepository itemVentaRepository) {
        this.carritoRepository = carritoRepository;
        this.juegoRepository = juegoRepository;
        this.ventaRepository = ventaRepository;
        this.itemVentaRepository = itemVentaRepository;
    }

    // ========== PROCESAR COMPRA ==========
    @Transactional
    public Venta procesarCompra(Usuario usuario) {
        System.out.println("=== PROCESANDO COMPRA ===");
        
        // Obtener items del carrito
        List<CarritoItem> carritoItems = carritoRepository.findByUsuario(usuario);
        
        if (carritoItems.isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }
        
        double total = 0;
        List<ItemVenta> itemsVenta = new ArrayList<>();
        
        for (CarritoItem item : carritoItems) {
            Juego juego = item.getJuego();
            int cantidad = item.getCantidad();
            
            // Verificar stock
            if (juego.getStock() < cantidad) {
                throw new RuntimeException("Stock insuficiente para: " + juego.getTitulo());
            }
            
            // Actualizar stock
            juego.setStock(juego.getStock() - cantidad);
            juegoRepository.save(juego);
            
            // Crear item de venta
            ItemVenta itemVenta = new ItemVenta(
                juego.getTitulo(),
                cantidad,
                item.getPrecioUnitario()
            );
            itemsVenta.add(itemVenta);
            
            total += item.getSubtotal();
        }
        
        // Guardar items de venta
        List<ItemVenta> itemsGuardados = itemVentaRepository.saveAll(itemsVenta);
        
        // Crear la venta
        Venta venta = new Venta(usuario, itemsGuardados, total);
        Venta ventaGuardada = ventaRepository.save(venta);
        
        // Vaciar carrito
        carritoRepository.deleteByUsuario(usuario);
        
        System.out.println("Compra procesada. ID Venta: " + ventaGuardada.getId() + " - Total: $" + total);
        
        return ventaGuardada;
    }
    
    // ========== OBTENER HISTORIAL DE COMPRAS DE UN USUARIO ==========
    public List<Venta> obtenerHistorialCompras(Usuario usuario) {
        return ventaRepository.findByUsuario(usuario);
    }
    
    // ========== OBTENER ESTADÍSTICAS PARA ADMIN ==========
    public Map<String, Object> obtenerEstadisticas() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalVentas", ventaRepository.obtenerTotalVentas() != null ? ventaRepository.obtenerTotalVentas() : 0.0);
        stats.put("cantidadVentas", ventaRepository.obtenerCantidadVentas() != null ? ventaRepository.obtenerCantidadVentas() : 0);
        stats.put("juegosMasVendidos", itemVentaRepository.obtenerJuegosMasVendidos());
        return stats;
    }
}