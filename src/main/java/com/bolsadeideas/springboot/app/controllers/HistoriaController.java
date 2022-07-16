package com.bolsadeideas.springboot.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Historia;
import com.bolsadeideas.springboot.app.models.entity.Mascota;
import com.bolsadeideas.springboot.app.models.entity.Producto;
import com.bolsadeideas.springboot.app.models.service.IMascotaService;

@Controller
@RequestMapping("/historia")
@SessionAttributes("historia")
public class HistoriaController {
	
	@Autowired
	private IMascotaService mascotaService;
	
	
	@GetMapping("/form/{mascotaId}")
	public String crear(@PathVariable(value = "mascotaId") Long mascotaId, Model model, RedirectAttributes flash) {

		Mascota mascota = mascotaService.findOne(mascotaId);
		List<Producto> productos = mascotaService.findAllProductos();

		if (mascota == null) {
			flash.addFlashAttribute("error", "La mascota no existe en la base de datos");
			return "redirect:/mascota/listar";
		}

		Historia historia = new Historia();
		historia.setMascota(mascota);

		model.addAttribute("historia", historia);
		model.addAttribute("productos", productos);
		model.addAttribute("mascota",mascota);
		model.addAttribute("titulo", "Crear historia para la mascota" + mascota.getNombre());

		return "mascota/form";
	}
	
	@PostMapping("/form")
	public String guardar(@Valid Historia historia,BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Historia mascotas");
			return "mascota/form";
		}
		String mensajeFlash = (historia.getId() != null) ? "historia editado con éxito!" : "historia creado con éxito!";
		mascotaService.saveHistoria(historia);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/mascota/listar";
	}

}
