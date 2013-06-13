package uk.zuehlke.blog;

import org.springframework.data.repository.PagingAndSortingRepository;

import uk.zuehlke.blog.domain.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, String>{

	
}
