package com.tienda.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;
    
    private Double total;
    
    private String estado; // "COMPLETADA", "CANCELADA"
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "venta_id")
    private List<ItemVenta> items = new ArrayList<>();
    
    public Venta() {}
    
    public Venta(Usuario usuario, List<ItemVenta> items, Double total) {
        this.usuario = usuario;
        this.items = items;
        this.total = total;
        this.fechaVenta = LocalDateTime.now();
        this.estado = "COMPLETADA";
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }
    
    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
    
    public Double getTotal() {
        return total;
    }
    
    public void setTotal(Double total) {
        this.total = total;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public List<ItemVenta> getItems() {
        return items;
    }
    
    public void setItems(List<ItemVenta> items) {
        this.items = items;
    }
}