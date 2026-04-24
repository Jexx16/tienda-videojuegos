package com.tienda.repository;

import com.tienda.model.ItemVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemVentaRepository extends JpaRepository<ItemVenta, Long> {
    
    // Buscar items por juego (para estadísticas)
    List<ItemVenta> findByJuegoTitulo(String juegoTitulo);
    
    // Obtener los juegos más vendidos
    @Query("SELECT i.juegoTitulo, SUM(i.cantidad) as total FROM ItemVenta i GROUP BY i.juegoTitulo ORDER BY total DESC")
    List<Object[]> obtenerJuegosMasVendidos();
}