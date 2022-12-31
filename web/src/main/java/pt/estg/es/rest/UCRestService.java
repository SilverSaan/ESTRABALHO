package pt.estg.es.rest;

import pt.estg.es.model.Usuario;
import pt.estg.es.model.unidadeCurricular;
import pt.estgp.es.services.ucServices;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.Null;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Path("/unCurricular")
@RequestScoped
public class UCRestService {

    @Inject
    private Logger log;

    @Inject
    private ucServices service;


    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<unidadeCurricular> loadAlunos(){
        log.info("Lista de UCS");
        return service.findAll();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public unidadeCurricular findUCbyID(@PathParam("id") long id)
    {
        unidadeCurricular uc = service.find(id);
        if (service == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return uc;
    }

    @GET
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUC(unidadeCurricular uc){
        Response.ResponseBuilder builder;

        try {
            service.create(uc);
            builder = Response.ok();
        }catch (Exception e){
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("ERROR", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();

    }


}
