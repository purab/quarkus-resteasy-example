package in.purabtech;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("movies")
public class MovieResource {

    public static List<String> movies = new ArrayList<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMovie() {
        return Response.ok(movies).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/size")
    public Integer countMovies() {
        return movies.size();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response createMovie(String newMovie) {
        movies.add(newMovie);
        return Response.ok(movies).build();
    }

    @PUT
    @Path("{movieToUpdate}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response updateMovie(
             @PathParam("movieToUpdate") String movieToUpdate,
            @QueryParam("movie") String updateMovie) {
        movies = movies.stream().map(movie ->{
            if(movie.equals(movieToUpdate)) {
                return updateMovie;
            }
            return movie;
        }).collect(Collectors.toList());
        return Response.ok(movies).build();
    }

    @DELETE
    @Path("{movieToDelete}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response deleteMovie(@PathParam("movieToDelete") String movieToDelete) {
        boolean removed = movies.remove(movieToDelete);
        return removed ? Response.noContent().build() :
                Response.status(Response.Status.BAD_REQUEST).build();

    }
}
