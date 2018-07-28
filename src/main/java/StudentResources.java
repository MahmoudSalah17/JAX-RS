import entity.Student;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import java.lang.reflect.Executable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("students")
public class StudentResources {

    private static final Logger LOGGER = Logger.getLogger(StudentResources.class.getName());

    private Repo repo = new Repo();

    @Context
    HttpServletRequest request;


    @GET
    public Response getAllStudents()
    {
        try {
            return Response.ok().entity(repo.getAllStudents()).build();
        }
        catch (Exception e ){
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }
    }


    @POST
    public Response addStudent(Student student)
    {
        try {
            Student addedStudent = repo.addStudent(student);
            URI uri = new URI(request.getRequestURL() + addedStudent.getId());
            return Response.created(uri).
                    build();
        }
        catch (Exception e ){
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }
    }

    @PUT
    public Response updateStudent(Student student)
    {
        try {
            repo.updateStudent(student);
            return Response.ok().
                    build();
        }
        catch (Exception e){
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }
    }


    @DELETE
    @Path("{id}")
    public Response deleteStudent(@PathParam("id") String id)
    {
        try {
            repo.deleteStudent(id);

            return Response.ok().
                    build();
        } catch (Exception e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
            return Response.serverError().
                    entity(e.getClass() + ": " + e.getMessage()).
                    build();
        }

    }
}
