package uk.mmi.movies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.mmi.movies.domain.Movie;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieResource.class);

	private List<Movie> movieList;
	private MovieFilterPredicate movieFilterPredicate = new MovieFilterPredicate();

	public MovieResource() {
		movieList = new ArrayList<Movie>();
		movieList.add(new Movie(0, "V for Vendetta"));
		movieList.add(new Movie(1, "Mean Machine"));
		movieList.add(new Movie(2, "I, Robot"));
		movieList.add(new Movie(3, "The Godfather"));
		movieList.add(new Movie(4, "Pulp Fiction"));
		movieList.add(new Movie(5, "Goodfellas"));
		movieList.add(new Movie(6, "The Matrix"));
	}

	@GET
	public Collection<Movie> getMovieList(@QueryParam("q") Optional<String> name) {
		LOGGER.info("Query movies: <{}>", name.orNull());
		if (name.isPresent() && !StringUtils.isEmpty(name.get())) {
			movieFilterPredicate.setFilter(name.get());
			return Collections2.filter(movieList, movieFilterPredicate);
		}
		return movieList;
	}

	class MovieFilterPredicate implements Predicate<Movie> {

		private String filter;

		@Override
		public boolean apply(Movie input) {
			return input.getTitle().contains(filter);
		}

		public void setFilter(String filter) {
			this.filter = filter;
		}
	}
}
