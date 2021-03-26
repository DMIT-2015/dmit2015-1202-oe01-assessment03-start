package dmit2015.resource;

import dmit2015.entity.Covid19Cases;
import dmit2015.repository.Covid19CasesRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

@ApplicationScoped		// This is a CDI-managed bean that is created only once during the life cycle of the application
@Path("covid19cases")	        // All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)	// All methods this class accept only JSON format data
@Produces(MediaType.APPLICATION_JSON)	// All methods returns data that has been converted to JSON format
public class Covid19CasesResource {

    @Inject
    private Covid19CasesRepository _covid19casesRepository;

    @GET	// This method only accepts HTTP GET requests.
    public Response listCovid19Casess() {
        return Response.ok(_covid19casesRepository.findAll()).build();
    }

    @Path("{id}")
    @GET	// This method only accepts HTTP GET requests.
    public Response findCovid19CasesById(@PathParam("id") Long id) {
        Optional<Covid19Cases> optionalCovid19Cases = _covid19casesRepository.findOneById(id);

        if (optionalCovid19Cases.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(optionalCovid19Cases.get()).build();
    }

    @POST	// This method only accepts HTTP POST requests.
    public Response addCovid19Cases(@Valid Covid19Cases newCovid19Cases, @Context UriInfo uriInfo) {

        try {
            // Persist the new Covid19Cases into the database
            _covid19casesRepository.create(newCovid19Cases);
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // userInfo is injected via @Context parameter to this method
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(newCovid19Cases.getId() + "")
                .build();

        // Set the location path of the new entity with its identifier
        // Returns an HTTP status of "201 Created" if the Covid19Cases was successfully persisted
        return Response
                .created(location)
                .build();
    }

    @PUT 			// This method only accepts HTTP PUT requests.
    @Path("{id}")	// This method accepts a path parameter and gives it a name of id
    public Response updateCovid19Cases(@PathParam("id") Long id, @Valid Covid19Cases updatedCovid19Cases) {
        if (id == null || updatedCovid19Cases.getId() == null || !id.equals(updatedCovid19Cases.getId())) {
            throw new BadRequestException();
        }

        Optional<Covid19Cases> optionalCovid19Cases = _covid19casesRepository.findOneById(id);
        if (optionalCovid19Cases.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            _covid19casesRepository.update(updatedCovid19Cases);
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response.
                    serverError()
                    .entity(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "204 No Content" if the Covid19Cases was successfully persisted
        return Response.noContent().build();
    }

    @DELETE 			// This method only accepts HTTP DELETE requests.
    @Path("{id}")	// This method accepts a path parameter and gives it a name of id
    public Response delete(@PathParam("id") Long id) {
        if (id == null) {
            throw new BadRequestException();
        }

        Optional<Covid19Cases> optionalCovid19Cases = _covid19casesRepository.findOneById(id);
        if (optionalCovid19Cases.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            _covid19casesRepository.delete(id);	// Removes the Covid19Cases from being persisted
        } catch (Exception ex) {
            // Return a HTTP status of "500 Internal Server Error" containing the exception message
            return Response
                    .serverError()
                    .encoding(ex.getMessage())
                    .build();
        }

        // Returns an HTTP status "204 No Content" if the Covid19Cases was successfully deleted
        return Response.noContent().build();
    }

}