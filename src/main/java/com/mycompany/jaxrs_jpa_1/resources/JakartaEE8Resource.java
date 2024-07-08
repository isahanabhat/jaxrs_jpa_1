package com.mycompany.jaxrs_jpa_1.resources;

import java.net.URI;
// import java.util.Arrays;
// import java.util.List;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
// import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
// import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
// import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author 
 * @param <T> 
 */
@Path("rest")
public abstract class JakartaEE8Resource<T> {
    @Context
	private UriInfo uriInfo;
	
	protected abstract String getSingle(String uid, String pwd) throws NamingException;
	
    @GET
	@Path("ping")
    public Response ping(){
        return Response
                .ok("ping")
                .build();
    }
	
	@GET
	@Path("query")
    public Response query(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu_jndi_mysql_pool");
		System.out.println("################## HI 1 ################### ");
		System.out.println("emf.isOpen: " + emf.isOpen()) ;
		
		EntityManager em = emf.createEntityManager();
		System.out.println("em.isOpen: " + em.isOpen()) ;
		System.out.println("################## HI 2 ################### ");
		
		String res = "HA HA";
		
		return Response.ok("query result: " + res).build();
    }

    @GET
    @Path("login")
    public Response display_query() throws NamingException {
            URI baseUri = uriInfo.getRequestUri();
            System.out.println(baseUri.getPath());
            System.out.println(baseUri.toString());


            System.out.println("uri here");
            //login?username=sahana&password=12345
            // http://localhost:8080/jaxrs_1/resources/rest/login?name=sahana&age=20
            String fullPath = baseUri.toString();
            String[] fullPath_split = fullPath.split("[/]");
            System.out.println(fullPath_split[fullPath_split.length-1]);
            String request = fullPath_split[fullPath_split.length-1];

            //  login?name=sahana&age=20
            String[] request_split = request.split("[?]", 2);
            String details = request_split[1];

            // name=sahana&age=20
            String[] paramaters_split = details.split("[&]",2);
            String username = paramaters_split[0].split("[=]")[1];
            String password = paramaters_split[1].split("[=]")[1];

            String records = getSingle(username, password);

            return Response.ok(records).build();
    }


}
