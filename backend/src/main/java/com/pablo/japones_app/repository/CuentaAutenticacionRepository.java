package com.pablo.japones_app.repository;

import com.pablo.japones_app.entity.CuentaAutenticacion;
import com.pablo.japones_app.enums.ProveedorAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaAutenticacionRepository extends JpaRepository<CuentaAutenticacion, Long> {
    List<CuentaAutenticacion> findByUsuarioId(Long usuarioId);
    Optional<CuentaAutenticacion> findByProveedorAndProveedorId(ProveedorAuth proveedor, String proveedorId);
    Optional<CuentaAutenticacion> findByUsuarioIdAndProveedor(Long usuarioId, ProveedorAuth proveedor);

}
