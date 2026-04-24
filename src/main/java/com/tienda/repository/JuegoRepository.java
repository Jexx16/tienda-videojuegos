package com.tienda.repository;

import com.tienda.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {
    
    // Buscar por título (contiene el texto, sin importar mayúsculas)
    List<Juego> findByTituloContainingIgnoreCase(String titulo);
    
    // Buscar por categoría
    List<Juego> findByCategoriaIgnoreCase(String categoria);
    
    // Buscar por plataforma
    List<Juego> findByPlataformaIgnoreCase(String plataforma);
    
    // Buscar disponibles (stock > 0)
    List<Juego> findByDisponibleTrue();
    
    // Buscar juegos ordenados por precio (menor a mayor)
    List<Juego> findAllByOrderByPrecioAsc();
    
    // Búsqueda general en título, categoría o plataforma
    @Query("SELECT j FROM Juego j WHERE LOWER(j.titulo) LIKE LOWER(CONCAT('%', :termino, '%')) OR LOWER(j.categoria) LIKE LOWER(CONCAT('%', :termino, '%')) OR LOWER(j.plataforma) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Juego> buscarPorTermino(@Param("termino") String termino);
}