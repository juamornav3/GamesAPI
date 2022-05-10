package aiss.api.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import aiss.model.EnumRev;
import aiss.model.Game;
import aiss.model.repository.GameRepository;
import aiss.model.repository.MapGameRepository;



@Path("/games")
public class GameResource {

	public static GameResource _instance=null;
	GameRepository repository;
	
	private GameResource(){
		repository=MapGameRepository.getInstance();
	}
	
	public static GameResource getInstance()
	{
		if(_instance==null)
			_instance=new GameResource();
		return _instance; 
	}
	
	@GET
	@Produces("application/json")
	public Collection<Game> getAll(@QueryParam("q") String q, @QueryParam("order") String order, @QueryParam("order2") String order2,
			@QueryParam("limit") Integer limit, @QueryParam("offset") Long offset){
		
		List<Game> list = new ArrayList<Game>();
		for(Game game : repository.getAllGames()) {
			if(q == null || game.getName().equals(q) || game.getConsole().contains(q) || game.getReview().toString().equals(q) || game.getScore().toString().equals(q)) {
				list.add(game);
			}
		}
		if(order != null && order2 ==null) {
            if(order.equals("name")) {
                Collections.sort(list, new CompareName());
            }else if(order.equals("console")) {
                Collections.sort(list, new CompareConsole());
            }else if(order.equals("review")) {
                Collections.sort(list, new CompareReview());
            }else if(order.equals("score")) {
                Collections.sort(list, new CompareScore());
            }else if(order.equals("-score")) {
                Collections.sort(list, new CompareScoreReversed());
            }else {
                throw new BadRequestException("The order parameter must be 'name' or 'console' or 'review' or 'score' or '-score'.");
            }
        } else if(order == null && order2 !=null) {
            if(order2.equals("name")) {
                Collections.sort(list, new CompareName());
            }else if(order2.equals("console")) {
                Collections.sort(list, new CompareConsole());
            }else if(order2.equals("review")) {
                Collections.sort(list, new CompareReview());
            }else if(order2.equals("score")) {
                Collections.sort(list, new CompareScore());
            }else if(order2.equals("-score")) {
                Collections.sort(list, new CompareScoreReversed());
            }else {
                throw new BadRequestException("The order parameter must be 'name' or 'console' or 'review' or 'score' or '-score'.");
            } 
        	} else if(order != null && order2 !=null) {
            if(order.equals("console") && order2.equals("score")) {
                Collections.sort(list, new CompareConsoleScore());
            } else if (order.equals("console") && order2.equals("-score")) {
                Collections.sort(list, new CompareConsoleScoreReversed());
            } else if (order.equals("console") && order2.equals("name")) {
                Collections.sort(list, new CompareConsoleName());
            } else if (order.equals("review") && order2.equals("name")) {
                Collections.sort(list, new CompareReviewName());
            } else if (order.equals("score") && order2.equals("name")) {
                Collections.sort(list, new CompareScoreName());
            } else if (order.equals("-score") && order2.equals("name")) {
                Collections.sort(list, new CompareScoreReversedName());
            } else if (order.equals("score") && order2.equals("console")) {
                Collections.sort(list, new CompareScoreConsole());
            } else if (order.equals("-score") && order2.equals("console")) {
                Collections.sort(list, new CompareScoreReversedConsole());
            } else {
                throw new BadRequestException("The order parameter must be 'console, score or -score' or 'console, name' or 'review, name' or 'score or -score, name' or 'score or -score, console'.");
            }
        }
		if (offset != null) { 
           list = list.stream().skip(offset).collect(Collectors.toList());
		}
        if (limit != null) {
          list = list.stream().limit(limit).collect(Collectors.toList());
        }
		return list;
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Game get(@PathParam("id") String gameId)
	{
		Game juego = repository.getGame(gameId);
		if (juego == null) {
			throw new NotFoundException("The game with id="+ gameId +" was not found");			
		}
		return juego;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addGame(@Context UriInfo uriInfo, Game game) {
		if ( game.getName()== null || "".equals(game.getName())) {
			throw new BadRequestException("The game's name must not be null");
		}
		Set<String> x = new HashSet<String>();
		x.add("");
        if ( game.getConsole()== null || x.equals(game.getConsole())) {
            throw new BadRequestException("The game's console must not be null");
        }
		if (game.getReview()==null) {
			game.setReview(EnumRev.NULO);
		}
		if ( game.getScore()== null || game.getScore()<1 || game.getScore()>10) {
			throw new BadRequestException("The game's score must be between 1 and 10");
		}
		repository.addGame(game);
		
		// Builds the response. Returns the game that has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(game.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(game);			
		return resp.build();
		
	}
	
	
	@PUT
	@Consumes("application/json")
	public Response updateGame(Game game) {
		Game oldGame = repository.getGame(game.getId());
		if (oldGame == null) {
			throw new NotFoundException("The game with id="+ game.getId() +" was not found");			
		}
		// Update name
		if (game.getName()!=null) {
			oldGame.setName(game.getName());
		}
		// Update description
		if (game.getConsole()!=null) {
			oldGame.setConsole(game.getConsole());
		}
		
		if (game.getScore()!=null) {
			oldGame.setScore(game.getScore());
			oldGame.setReview(MapGameRepository.getEnumRevScore(game));
		}
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeGame(@PathParam("id") String gameId) {
		
		Game toberemoved=repository.getGame(gameId);
		if (toberemoved == null)
			throw new NotFoundException("The game with id="+ gameId +" was not found");
		else
			repository.deleteGame(gameId);
		
		return Response.noContent().build();
	}
	
}
