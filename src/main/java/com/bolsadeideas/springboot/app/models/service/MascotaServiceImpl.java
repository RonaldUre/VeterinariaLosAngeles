package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bolsadeideas.springboot.app.models.dao.IHistoriaDao;
import com.bolsadeideas.springboot.app.models.dao.IMascotaDao;
import com.bolsadeideas.springboot.app.models.dao.IProductoDao;
import com.bolsadeideas.springboot.app.models.entity.Historia;
import com.bolsadeideas.springboot.app.models.entity.Mascota;
import com.bolsadeideas.springboot.app.models.entity.Producto;

@Service
public class MascotaServiceImpl implements IMascotaService {

	@Autowired
	private IMascotaDao mascotaDao;
	
	@Autowired 
	private IHistoriaDao historiaDao;
	
	@Autowired
	private IProductoDao productoDao;
	

	@Override
	@Transactional(readOnly = true)
	public List<Mascota> findAll() {
		return (List<Mascota>) mascotaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Mascota> findAll(Pageable pageable) {
		return mascotaDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Mascota mascota) {
		mascotaDao.save(mascota);

	}

	@Override
	@Transactional(readOnly = true)
	public Mascota findOne(Long id) {
		return mascotaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		mascotaDao.deleteById(id);

	}

	@Override
	public Historia saveHistoria(Historia historia) {
		// TODO Auto-generated method stub
		return historiaDao.save(historia);
	}

	@Override
	public void deleteHistoria(Long id) {
		historiaDao.deleteById(id);
		
	}

	@Override
	public List<Producto> findAllProductos() {
		// TODO Auto-generated method stub
		return (List<Producto>) productoDao.findAll();
	}

}
