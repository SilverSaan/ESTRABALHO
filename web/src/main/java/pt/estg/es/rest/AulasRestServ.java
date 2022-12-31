package pt.estg.es.rest;

import pt.estg.es.model.Aulas;
import pt.estg.es.model.unidadeCurricular;
import pt.estgp.es.services.AulasServices;
import pt.estgp.es.services.ucServices;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Path("/aula")
@RequestScoped
public class AulasRestServ {

    @Inject
    private Logger log;

    @Inject
    private AulasServices service;


    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Aulas> loadAll(){
        return service.findAll();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Aulas findUCbyID(@PathParam("id") long id)
    {
        Aulas aula = service.find(id);
        if (service == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return aula;
    }

    @GET
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAula(Aulas aula){
        Response.ResponseBuilder builder;

        try {
            service.create(aula);

            builder = Response.ok();
        }catch (Exception e){
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("ERROR", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();

    }


}
