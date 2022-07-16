package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.app.models.entity.Historia;
import com.bolsadeideas.springboot.app.models.entity.Mascota;
import com.bolsadeideas.springboot.app.models.entity.Producto;

public interface IMascotaService {

	public List<Mascota> findAll();
	
	public Page<Mascota> findAll(Pageable pageable);
	
	public void save(Mascota mascota);
	
	public Mascota findOne(Long id);
	
	public void delete (Long id);
	
	
	public Historia saveHistoria(Historia historia);
	
	public void deleteHistoria(Long id);
	
	public List<Producto> findAllProductos();
	
//	public Historia fetchHistoriaByIdWithProductoWithMascota(Long id);
}
