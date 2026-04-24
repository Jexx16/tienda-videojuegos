package com.tienda.repository;

import com.tienda.model.CarritoItem;
import com.tienda.model.Juego;
import com.tienda.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
    
    // Buscar todos los items del carrito de un usuario
    List<CarritoItem> findByUsuario(Usuario usuario);
    
    // Buscar un item específico en el carrito de un usuario para un juego
    Optional<CarritoItem> findByUsuarioAndJuego(Usuario usuario, Juego juego);
    
    // Eliminar todos los items del carrito de un usuario
    void deleteByUsuario(Usuario usuario);
    
    // Contar cuántos items tiene un usuario en el carrito
    int countByUsuario(Usuario usuario);
}