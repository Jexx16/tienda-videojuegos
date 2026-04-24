package com.tienda.repository;

import com.tienda.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Buscar usuario por nombre (para login)
    Optional<Usuario> findByNombre(String nombre);
    
    // Buscar usuario por nombre y contraseña (para login con validación)
    Optional<Usuario> findByNombreAndPassword(String nombre, String password);
    
    // Verificar si existe un usuario por nombre
    boolean existsByNombre(String nombre);
}