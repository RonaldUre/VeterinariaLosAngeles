package com.bolsadeideas.springboot.app.controllers;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Mascota;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.models.service.IMascotaService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;


@Controller
@RequestMapping("/mascota")
@SessionAttributes("mascota")
public class MascotaController {
	@Autowired
	private IMascotaService mascotaService;
	
	@Autowired IClienteService clienteService;
	
	//private final static Logger log = LoggerFactory.getLogger(MascotaController.class);
	
	@GetMapping("/listar")
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication, HttpServletRequest request, Locale locale) {
		
		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Mascota> mascotas = mascotaService.findAll(pageRequest);
		
		PageRender<Mascota> pageRender = new PageRender<Mascota>("/listar", mascotas);
		model.addAttribute("titulo", "Lista de Mascotas");// messageSource.getMessage("text.cliente.listar.titulo",
															// null, locale));
		model.addAttribute("mascotas", mascotas);
		model.addAttribute("page", pageRender);
		
		return "mascotas";
	}
	
	@GetMapping("/formMascota/{id}")
	public String crear(@PathVariable(value = "id") Long id,Model model) {
		Mascota mascota =  new Mascota();
		Cliente cliente = clienteService.findOne(id);
		mascota.setCliente(cliente);
		model.addAttribute("cliente", cliente);
		model.addAttribute("mascota", mascota);
		model.addAttribute("titulo", "Formulario de mascotas");
		return "formMascota";
	}
	
	@GetMapping("/formMascota/editar/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Mascota mascota = null;

		if (id > 0) {
			mascota = mascotaService.findOne(id);
			if (mascota == null) {
				flash.addFlashAttribute("error", "El ID de la mascota no existe en la BBDD!");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID de la mascota no puede ser cero!");
			return "redirect:/listar";
		}
		model.put("mascota", mascota);
		model.put("titulo", "Editar Mascota");
		return "formMascota";
	}
	
	@PostMapping("/formMascota")
	public String guardar(@Valid Mascota mascota,BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Mascota");
			return "formMascota";
		}
		String mensajeFlash = (mascota.getId() != null) ? "Mascota editado con éxito!" : "Mascota creado con éxito!";
		mascotaService.save(mascota);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}
		
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			//Mascota mascota = mascotaService.findOne(id);

			mascotaService.delete(id);
			flash.addFlashAttribute("success", "Mascota eliminado con éxito!");
		}
		return "redirect:/mascota/listar";
	} 
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Mascota mascota = mascotaService.findOne(id);// clienteService.findOne(id);
		if (mascota == null) {
			flash.addFlashAttribute("error", "la mascota no existe en la base de datos");
			return "redirect:/mascota/listar";
		}

		model.put("mascota", mascota);
		model.put("titulo", "Detalle de la mascota: " + mascota.getNombre());
		return "ver-mascota";
	}


	

}
