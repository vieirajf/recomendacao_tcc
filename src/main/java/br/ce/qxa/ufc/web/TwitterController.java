package br.ce.qxa.ufc.web;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import br.ce.qxa.ufc.model.AutorizacaoTwitter;
import br.ce.qxa.ufc.model.Pin;
import br.ce.qxa.ufc.model.TwitterUsuarioId;
import br.ce.qxa.ufc.model.Usuario;
import br.ce.qxa.ufc.service.GenericService;
import br.ce.qxa.ufc.service.UsuarioService;

@Controller
@RequestMapping("/twitter")
public class TwitterController {

	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private GenericService<AutorizacaoTwitter> autorizacaoTwitterService;
	
	@Inject
	private GenericService<TwitterUsuarioId> twitterUsuarioIsService;
		
	
	
	private RequestToken requestToken=null;

	
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
	
	
	public void getTwitte(Twitter twitter){
		Integer i = 1;
		Integer cont = 1;
		System.out.println("Showing home timeline.");
		List<Status> statuses;
		try {
			statuses = twitter.getUserTimeline(729614881,new Paging(i, 200));
			while(!statuses.isEmpty()){
				 // The factory instance is re-useable and thread safe.
			    //729614881
				   	for (Status status : statuses) {
				        System.out.println(cont +": "+status.getUser().getName() + ":" +  status.getText());
				        cont++;
				       
				    }
			    		        	
			    i++;
			     statuses = twitter.getUserTimeline(729614881,new Paging(i, 200));
			}
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	public void getIdsUsuariosTwitter(Long idUsuarioTwitter, Usuario usuario) {
		long cursor = -1;
        IDs ids = null;
//        User user;
        List <TwitterUsuarioId> idsUsuarioTwitter = new ArrayList<TwitterUsuarioId>();
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
            	TwitterUsuarioId novosIds = new TwitterUsuarioId();
            	novosIds.setIdTwitter(id);
            	twitterUsuarioIsService.save(novosIds);
          	  idsUsuarioTwitter.add(novosIds);
          	//  System.out.println("ID: "+ id);
                //System.out.println("Nome: "+user.getName());
                //System.out.println("Quantidades de pessoas: "+user.getFriendsCount());
                //System.out.println("Status: "+user.getStatus().getText());
            }
        } while ((cursor = ids.getNextCursor()) != 0);
        usuario.setAmigosId(idsUsuarioTwitter);
        usuarioService.update(usuario);

	}
	
	@RequestMapping(value = "/verificaAcessoTwitter")
	public String verificaAcessoTwitter(ModelMap modelMap) throws Exception {
		Usuario usuarioAutorizado = usuarioService.getUsuarioByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		if (usuarioAutorizado.getAutorizacaoTwitter() != null) {
			twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer("bVqAzGbuR5jsOTDstph9XB1dM","vi9xVqIc1oMQAydQYIVgbo0GvO4XWwPjdhtJpjAUk6yv19vdDO");
			 AccessToken accessToken = new AccessToken(usuarioAutorizado.getAutorizacaoTwitter().getToken(), usuarioAutorizado.getAutorizacaoTwitter().getTokenSecret());  
		      twitter.setOAuthAccessToken(accessToken);
			getIdsUsuariosTwitter(usuarioAutorizado.getAutorizacaoTwitter().getId(), usuarioAutorizado);
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
