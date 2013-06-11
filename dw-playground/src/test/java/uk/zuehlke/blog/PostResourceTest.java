package uk.zuehlke.blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uk.zuehlke.blog.domain.Post;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mongo-context.xml")
public class PostResourceTest {

	@Autowired
	private PostResource postResource;

	@Test
	public void testGetAllPosts() {
		Iterable<Post> allPosts = postResource.getAllPosts();
		for (Post post : allPosts) {
			System.out.println(post);
		}
	}

}
