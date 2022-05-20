package pl.coderslab.blog.services;

import org.springframework.data.repository.CrudRepository;
import pl.coderslab.blog.models.Comment;

public interface CommentService extends CrudRepository<Comment, Long> {

}