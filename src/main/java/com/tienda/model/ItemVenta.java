package com.tienda.model;

import jakarta.persistence.*;

@Entity
@Table(name = "items_venta")
public class ItemVenta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String juegoTitulo;
    
    private Integer cantidad;
    
    private Double precioUnitario;
    
    private Double subtotal;
    
    public ItemVenta() {}
    
    public ItemVenta(String juegoTitulo, Integer cantidad, Double precioUnitario) {
        this.juegoTitulo = juegoTitulo;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getJuegoTitulo() {
        return juegoTitulo;
    }
    
    public void setJuegoTitulo(String juegoTitulo) {
        this.juegoTitulo = juegoTitulo;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public Double getPrecioUnitario() {
        return precioUnitario;
    }
    
    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
    public Double getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}