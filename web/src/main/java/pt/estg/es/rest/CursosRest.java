package pt.estg.es.rest;

import pt.estg.es.model.Cursos;
import pt.estgp.es.services.CursosServices;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CursosRest {

    @Inject
    private Logger log;

    @Inject
    private CursosServices service;


    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cursos> loadAll(){
        return service.findAll();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Cursos findUCbyID(@PathParam("id") long id)
    {
        Cursos cs = service.find(id);
        if (service == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return cs;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createAula(Cursos curso){
        Response.ResponseBuilder builder;

        try {
            service.create(curso);

            builder = Response.ok();
        }catch (Exception e){
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("ERROR", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();

    }


}
