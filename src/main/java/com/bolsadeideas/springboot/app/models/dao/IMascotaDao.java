package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsadeideas.springboot.app.models.entity.Mascota;

public interface IMascotaDao extends PagingAndSortingRepository<Mascota, Long>{

}
