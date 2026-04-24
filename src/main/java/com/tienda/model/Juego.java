package com.tienda.model;

import jakarta.persistence.*;

@Entity
@Table(name = "juegos")
public class Juego {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String titulo;
    
    private String categoria;
    
    private String plataforma;
    
    private Double precio;
    
    @Column(length = 1000)
    private String descripcion;
    
    private String imagenUrl;  // Nombre del archivo de imagen
    
    private Integer stock;      // Cantidad disponible para la venta
    
    private Boolean disponible; // true si stock > 0
    
    public Juego() {}
    
    public Juego(String titulo, String categoria, String plataforma, Double precio, 
                 String descripcion, String imagenUrl, Integer stock) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.plataforma = plataforma;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.stock = stock;
        this.disponible = stock > 0;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getPlataforma() {
        return plataforma;
    }
    
    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
    
    public Double getPrecio() {
        return precio;
    }
    
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getImagenUrl() {
        return imagenUrl;
    }
    
    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
    
    public Integer getStock() {
        return stock;
    }
    
    public void setStock(Integer stock) {
        this.stock = stock;
        this.disponible = stock > 0;
    }
    
    public Boolean getDisponible() {
        return disponible;
    }
    
    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
}