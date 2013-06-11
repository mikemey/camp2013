package uk.zuehlke.blog;

import org.springframework.data.repository.CrudRepository;

import uk.zuehlke.blog.domain.Post;

public interface PostRepository extends CrudRepository<Post, Long>{

}
