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

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import br.ce.qxa.ufc.model.AutorizacaoTwitter;
import br.ce.qxa.ufc.model.Papel;
import br.ce.qxa.ufc.model.Pin;
import br.ce.qxa.ufc.model.Usuario;
import br.ce.qxa.ufc.repositorio.jpa.GenericRepositoryImpl;
import br.ce.qxa.ufc.service.GenericService;
import br.ce.qxa.ufc.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private GenericService<AutorizacaoTwitter> autorizacaoTwitterService;
	
	@Inject
	private GenericRepositoryImpl<Papel> papelService;
	
	private Twitter twitter;
	
	private RequestToken requestToken=null;

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
		Long id = new Long(1);
		Papel papel = papelService.find(Papel.class, id);

		List<Papel> papeis = new ArrayList<Papel>();
		papeis.add(papel);
		usuario.setPapeis(papeis);

		usuarioService.save(usuario);
		redirectAttributes.addFlashAttribute("info",
				"Usuario adicionado com sucesso.");
		return "redirect:/login";

	}

	@RequestMapping(value = "/autorizacaoTwitter")
	public String autorizacaoTwitter(@Valid Pin pin,ModelMap modelMap) throws Exception {
		Usuario usuarioAutorizado = usuarioService.getUsuarioByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		AccessToken accessToken = null;
		try{
	           if(pin.getPin().length() > 0){
	        	 accessToken = twitter.getOAuthAccessToken(requestToken, pin.getPin());
	           }else{
	             accessToken = twitter.getOAuthAccessToken();
	           }
	        } catch (TwitterException te) {
	          if(401 == te.getStatusCode()){
	            System.out.println("Não foi possível obter o token de acesso.");
	          }else{
	            te.printStackTrace();
	          }
	        }
	      
		AutorizacaoTwitter autorizacaoTwitter = new AutorizacaoTwitter();
		autorizacaoTwitter.setId(accessToken.getUserId());
		autorizacaoTwitter.setToken(accessToken.getToken());
		autorizacaoTwitter.setTokenSecret(accessToken.getTokenSecret());
		autorizacaoTwitter.setUsuario(usuarioAutorizado);
		autorizacaoTwitterService.save(autorizacaoTwitter);
		
		usuarioAutorizado.setAutorizacaoTwitter(autorizacaoTwitter);
		usuarioService.update(usuarioAutorizado);
		
		modelMap.addAttribute("usuario",usuarioAutorizado);
	return "redirect:/usuario/listar";
		
	}
	
	public void getIdsUsuariosTwitter(Long idUsuarioTwitter) {
		long cursor = -1;
        IDs ids = null;
        User user;
        do {
            
          	  //pode usar essa metodo passando como primeiro argumento os id´s e como segundo argumento paginação para retornar os amigos do id repassado.
                try {
					ids = twitter.getFriendsIDs(idUsuarioTwitter, cursor);
				} catch (TwitterException e) {
					e.printStackTrace();
				}
            for (long id : ids.getIDs()) {
          	  //showuser tem limite de duas vezes por dia
          	  //user = twitter.showUser(id);
          	  
          	  System.out.println("ID: "+ id);
                //System.out.println("Nome: "+user.getName());
                //System.out.println("Quantidades de pessoas: "+user.getFriendsCount());
                //System.out.println("Status: "+user.getStatus().getText());
            }
        } while ((cursor = ids.getNextCursor()) != 0);

	}
	
	@RequestMapping(value = "/verificaAcessoTwitter")
	public String verificaAcessoTwitter(ModelMap modelMap) throws Exception {
		Usuario usuarioAutorizado = usuarioService.getUsuarioByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		if (usuarioAutorizado.getAutorizacaoTwitter() != null) {
			twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer("bVqAzGbuR5jsOTDstph9XB1dM","vi9xVqIc1oMQAydQYIVgbo0GvO4XWwPjdhtJpjAUk6yv19vdDO");
			 AccessToken accessToken = new AccessToken(usuarioAutorizado.getAutorizacaoTwitter().getToken(), usuarioAutorizado.getAutorizacaoTwitter().getTokenSecret());  
		      twitter.setOAuthAccessToken(accessToken);
			getIdsUsuariosTwitter(usuarioAutorizado.getAutorizacaoTwitter().getId());
			return "redirect:/usuario/listar";
			
			
		}	
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer("bVqAzGbuR5jsOTDstph9XB1dM","vi9xVqIc1oMQAydQYIVgbo0GvO4XWwPjdhtJpjAUk6yv19vdDO");

		requestToken = twitter.getOAuthRequestToken();
		//AccessToken accessToken = null;
		String url = requestToken.getAuthorizationURL();
		System.out.println(url);
		System.out.println(url);
		System.out.println(url);
		System.out.println(url);
		modelMap.addAttribute("url",url);
		modelMap.addAttribute("pin", new Pin());
		return "usuario/autorizacaoTwitter";	
	}

}
