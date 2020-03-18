package eu.ase.ejb3.jpaclient;


import javax.ejb.embeddable.EJBContainer;
//import javax.naming.Context;
import javax.naming.*;
import java.util.List;
import java.util.Properties;

import eu.ase.ejb3.jpa.*;

/**
 * @version $Revision: 607077 $ $Date: 2007-12-27 06:55:23 -0800 (Thu, 27 Dec 2007) $
 */
public class SimpleJPAClient {

    public static void main(String[] args) throws Exception {
        Properties p = new Properties();
        p.put("movieDatabase", "new://Resource?type=DataSource");
        p.put("movieDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
        p.put("movieDatabase.JdbcUrl", "jdbc:hsqldb:mem:moviedb");
        p.put("java.naming.factory.initial",
		 "org.apache.openejb.client.RemoteInitialContextFactory");
	p.put("java.naming.provider.url", "http://localhost:8080/tomee/ejb");
            

        //final Context context = EJBContainer.createEJBContainer(p).getContext();

        InitialContext context = new InitialContext(p);

        NamingEnumeration<NameClassPair> listJ = context.list("");
	while (listJ.hasMore()) {
  		System.out.println(listJ.next().getName());
	}

        //Movies movies = (Movies) context.lookup("java:global/jpa-eclipselink/Movies");

	Movies movies = (Movies) context.lookup(args[0]);

        movies.addMovie(new Movie("Quentin Tarantino", "Reservoir Dogs", 1992));
        movies.addMovie(new Movie("Joel Coen", "Fargo", 1996));
        movies.addMovie(new Movie("Joel Coen", "The Big Lebowski", 1998));

        List<Movie> list = movies.getMovies();
        //assertEquals("List.size()", 3, list.size());

        for (Movie movie : list) {
            System.out.println("Movie = " + movie);
            movies.deleteMovie(movie);
        }

        //assertEquals("Movies.getMovies()", 0, movies.getMovies().size());
    }
}

