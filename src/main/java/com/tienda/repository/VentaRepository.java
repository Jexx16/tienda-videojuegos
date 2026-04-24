package com.tienda.repository;

import com.tienda.model.Usuario;
import com.tienda.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    
    // Buscar ventas por usuario
    List<Venta> findByUsuario(Usuario usuario);
    
    // Buscar ventas por estado
    List<Venta> findByEstado(String estado);
    
    // Buscar ventas en un rango de fechas
    List<Venta> findByFechaVentaBetween(LocalDateTime inicio, LocalDateTime fin);
    
    // Obtener total de ventas (suma de todos los montos)
    @Query("SELECT SUM(v.total) FROM Venta v WHERE v.estado = 'COMPLETADA'")
    Double obtenerTotalVentas();
    
    // Obtener cantidad total de ventas
    @Query("SELECT COUNT(v) FROM Venta v WHERE v.estado = 'COMPLETADA'")
    Long obtenerCantidadVentas();
    
    // Obtener ventas recientes de un usuario
    List<Venta> findTop5ByUsuarioOrderByFechaVentaDesc(Usuario usuario);
}