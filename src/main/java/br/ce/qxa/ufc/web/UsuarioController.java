package br.ce.qxa.ufc.web;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ce.qxa.ufc.model.Papel;
import br.ce.qxa.ufc.model.Usuario;
import br.ce.qxa.ufc.repositorio.jpa.GenericRepositoryImpl;
import br.ce.qxa.ufc.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Inject
	private UsuarioService usuarioService;
	
	
	
	@Inject
	private GenericRepositoryImpl<Papel> papelService;
	
	

	@RequestMapping(value = "/adicionar")
	public String adicionar(ModelMap modelMap) {
		modelMap.addAttribute("usuario", new Usuario());
		return "usuario/adicionar";
	}

	@RequestMapping(value = "/listar")
	public String listar(ModelMap modelMap) {
		modelMap.addAttribute("usuario", usuarioService.getUsuarioByLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
		return "usuario/listar";
	}
	
	@RequestMapping(value = "/adicionar", method = RequestMethod.POST)
	public String adicionar(@Valid Usuario usuario, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "usuario/adicionar";
		}

		if (usuarioService.getUsuarioByLogin(usuario.getLogin()) != null) {
			result.rejectValue("login", "Repeat.usuario.login",
					"Ja existe um usuario com esse login");
			return "usuario/adicionar";
		}
		usuario.setHabilitado(true);
		Integer id = new Integer(1);
		Papel papel = papelService.find(Papel.class, id);

		List<Papel> papeis = new ArrayList<Papel>();
		papeis.add(papel);
		usuario.setPapeis(papeis);

		usuarioService.save(usuario);
		redirectAttributes.addFlashAttribute("info",
				"Usuario adicionado com sucesso.");
		return "redirect:/login";

	}

	
	
		
	

}
