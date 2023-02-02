package pt.estg.es.rest;

import pt.estg.es.model.Aulas;
import pt.estg.es.model.Presenca;
import pt.estg.es.model.PresencaID;
import pt.estg.es.model.Usuario;
import pt.estgp.es.services.AulasServices;

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/make-attendance")
    public Response createAttendance(PresencaID id){
        Response.ResponseBuilder builder;

        try {
            service.makeAttendance(id);
            builder = Response.ok();
        }catch (Exception e){
            Map<String, String> responseObject = new HashMap<>();
            responseObject.put("Erro", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObject);
        }
        return builder.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/delete-Attendance")
    public Response deleteAttendance(PresencaID presencaID){
        Response.ResponseBuilder builder;

        try {
            service.deleteAttendance(presencaID);
            builder = Response.ok();
        }catch (Exception e){
            Map<String, String> responseObject = new HashMap<>();
            responseObject.put("Erro", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObject);
        }
        return builder.build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}/presenca")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> alunosPresentes(@PathParam("id") long Aulaid){
       return service.getAlunosPresentes(Aulaid);

    }


}
