package com.proyecto.hojadevida.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.hojadevida.model.HojaDeVida;

public interface HojaDeVidaRepository extends JpaRepository<HojaDeVida, Long> {
}