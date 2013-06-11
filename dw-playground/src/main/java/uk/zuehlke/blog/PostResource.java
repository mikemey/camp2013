package uk.zuehlke.blog;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.zuehlke.blog.domain.Post;

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class PostResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostResource.class);

	@Autowired
	private PostRepository postRepository;
	
	@GET
	public Iterable<Post> getAllPosts() {
		LOGGER.info("Query all posts.");
		return postRepository.findAll();
	}
}
