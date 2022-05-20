package pl.coderslab.blog.services;

import org.springframework.data.repository.CrudRepository;
import pl.coderslab.blog.models.Post;

public interface PostService extends CrudRepository<Post, Long> {

}
