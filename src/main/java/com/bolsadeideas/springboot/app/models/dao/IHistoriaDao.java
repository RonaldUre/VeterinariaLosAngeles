package com.bolsadeideas.springboot.app.models.dao;

//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.bolsadeideas.springboot.app.models.entity.Historia;

public interface IHistoriaDao extends CrudRepository<Historia, Long>{
	/*
	@Query("select h from Historia h join fetch h.mascota m join fetch h.producto p where f.id=?1")
	public Historia fetchHistoriaByIdWithProductoWithMascota(Long id);
*/
}
