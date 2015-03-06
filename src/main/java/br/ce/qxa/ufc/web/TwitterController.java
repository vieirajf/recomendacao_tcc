package br.ce.qxa.ufc.web;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import br.ce.qxa.ufc.model.AutorizacaoTwitter;
import br.ce.qxa.ufc.model.AutorizacaoTwitterRequest;
import br.ce.qxa.ufc.model.Pin;
import br.ce.qxa.ufc.model.TwitterUsuarioId;
import br.ce.qxa.ufc.model.Usuario;
import br.ce.qxa.ufc.service.GenericService;
import br.ce.qxa.ufc.service.TwitterUsuarioIdService;
import br.ce.qxa.ufc.service.UsuarioService;

@Controller
@RequestMapping("/twitter")
public class TwitterController {

	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private GenericService<AutorizacaoTwitter> autorizacaoTwitterService;
	
	@Inject
	private TwitterUsuarioIdService twitterUsuarioIdService;
	
	@Inject
	private GenericService<AutorizacaoTwitterRequest> autorizacaoTwitterRequestService;
				
	/**
	 * Grava a autorização para o programa acessar a conta do twitter gerando um token, tokenSecret.
	 * 
	 */
	@RequestMapping(value = "/{id}/autorizacaoTwitter")
	public String autorizacaoTwitter(@PathVariable("id") Integer id,@Valid Pin pin,ModelMap modelMap)  {
	
		AutorizacaoTwitterRequest autorizacaoTwitterRequest = autorizacaoTwitterRequestService.find(AutorizacaoTwitterRequest.class, id);
		Usuario usuarioAutorizado = usuarioService.getUsuarioByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		Twitter twitter = autorizacaoTwitterRequest.getTwitter();
		RequestToken requestToken = autorizacaoTwitterRequest.getRequestToken();
		AccessToken accessToken = null;
		
		try{
		      if(pin.getPin().length() > 0){
	           	   System.out.println(pin.getPin());
	              	 accessToken = twitter.getOAuthAccessToken(requestToken, pin.getPin());
	           }else{
	             accessToken = twitter.getOAuthAccessToken();
	           }
	        } catch (TwitterException te) {
	          if(401 == te.getStatusCode()){
	            System.out.println("Não foi possível obter o token de acesso." +te.getMessage());
	          }else{
	            te.printStackTrace();
	          }
	        }
		
	      
		AutorizacaoTwitter autorizacaoTwitter = new AutorizacaoTwitter();
		autorizacaoTwitter.setId((int)accessToken.getUserId() );
		autorizacaoTwitter.setToken(accessToken.getToken());
		autorizacaoTwitter.setTokenSecret(accessToken.getTokenSecret());
		autorizacaoTwitter.setUsuario(usuarioAutorizado);
		autorizacaoTwitterService.save(autorizacaoTwitter);
		
		usuarioAutorizado.setAutorizacaoTwitter(autorizacaoTwitter);
		usuarioService.update(usuarioAutorizado);
		
		
		autorizacaoTwitterRequestService.delete(autorizacaoTwitterRequest);
		modelMap.addAttribute("usuario",usuarioAutorizado);
	return "redirect:/usuario/listar";
		
	}
	
	public List<TwitterUsuarioId> getIdsUsuariosTwitter(Long idUsuarioTwitter,Twitter twitter) throws TwitterException {
		long cursor = -1;
		List<TwitterUsuarioId> listaDeId = new ArrayList<TwitterUsuarioId>();
        IDs ids = null;
        User user = null;
        do {
            //pode usar essa metodo passando como primeiro argumento os id´s e 
        	//como segundo argumento paginação para retornar os amigos do id repassado.
            ids = twitter.getFriendsIDs(idUsuarioTwitter, cursor);
			for (long id : ids.getIDs()) {
			  TwitterUsuarioId idNovo = twitterUsuarioIdService.getTwitterUsuarioIdByIdTwitter(id);
			  if (idNovo==null){
				  idNovo= new TwitterUsuarioId();
				  idNovo.setIdTwitter(id);
				  twitterUsuarioIdService.save(idNovo);
			  }
			  
          	  listaDeId.add(idNovo);
            }
        } while ((cursor = ids.getNextCursor()) != 0);
        return listaDeId;
	}
	
	public void cadastroIdTwitterAmigos1E2Grau(Usuario usuario,Twitter twitter){
		Long idUsuario = (long)usuario.getAutorizacaoTwitter().getId();
		try {
			List<TwitterUsuarioId> idAmigos = new ArrayList<TwitterUsuarioId>(); 
					idAmigos = getIdsUsuariosTwitter(idUsuario,twitter);
			usuario = usuarioService.CadastraIdAmigos(idAmigos, usuario.getId());
			usuarioService.save(usuario);
			for (TwitterUsuarioId id :idAmigos ) {
				List<TwitterUsuarioId> idAmigos2 = new ArrayList<TwitterUsuarioId>();
				idAmigos2 = getIdsUsuariosTwitter(id.getIdTwitter(),twitter);
				usuario = usuarioService.CadastraIdsParaRecomadacao(idAmigos2, usuario);
			}
			usuarioService.save(usuario);
			//getTweets(twitter);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * Verifica se o programa já tem acesso a conta do usuario, caso negativo redireciona para pagina, que gera uma autorização. 
	 * @throws TwitterException 
	 * @throws IllegalStateException 
	 */
	
	@RequestMapping(value = "/verificaAcessoTwitter")
	public String verificaAcessoTwitter(ModelMap modelMap) throws IllegalStateException, TwitterException {
		Usuario usuarioAutorizado = usuarioService.getUsuarioByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
		Twitter twitter = null;
		RequestToken requestToken=null;
		if (usuarioAutorizado.getAutorizacaoTwitter() != null) {
			twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer("bVqAzGbuR5jsOTDstph9XB1dM","vi9xVqIc1oMQAydQYIVgbo0GvO4XWwPjdhtJpjAUk6yv19vdDO");
			 AccessToken accessToken = new AccessToken(usuarioAutorizado.getAutorizacaoTwitter().getToken(), usuarioAutorizado.getAutorizacaoTwitter().getTokenSecret());  
		      twitter.setOAuthAccessToken(accessToken);
		      cadastroIdTwitterAmigos1E2Grau(usuarioAutorizado ,twitter);
			
			return "redirect:/usuario/listar";
		}	
		
		try {
			twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer("bVqAzGbuR5jsOTDstph9XB1dM","vi9xVqIc1oMQAydQYIVgbo0GvO4XWwPjdhtJpjAUk6yv19vdDO");
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//AccessToken accessToken = null;
		String url = requestToken.getAuthorizationURL();
		//System.out.println(twitter.getId());
		AutorizacaoTwitterRequest autorizacaoTwitterRequest = new AutorizacaoTwitterRequest();
		autorizacaoTwitterRequest.setRequestToken(requestToken);
		autorizacaoTwitterRequest.setTwitter(twitter);
		autorizacaoTwitterRequestService.save(autorizacaoTwitterRequest);
		modelMap.addAttribute("url",url);
		modelMap.addAttribute("id",autorizacaoTwitterRequest.getId());
		modelMap.addAttribute("pin", new Pin());
		return "usuario/autorizacaoTwitter";	
	}
	
	public List<Status> getTweets(Twitter twitter) throws TwitterException{
		Integer i = 1;
		Integer cont = 1;
		System.out.println("Showing home timeline.");
		List<Status> statuses;
		List<Status> listaDeTweets = new ArrayList<Status>();
		statuses = twitter.getUserTimeline(729614881,new Paging(i, 200));
		while(!statuses.isEmpty()){
				 // The factory instance is re-useable and thread safe.
			    //729614881
				   	for (Status status : statuses) {
				   		listaDeTweets.add(status);
				        System.out.println(cont +": "+status.getUser().getName() + ":" +  status.getText());
				        cont++;
				       
				    }
			    		        	
			    i++;
			     statuses = twitter.getUserTimeline(729614881,new Paging(i, 200));
		}
		return listaDeTweets;
	}

}
