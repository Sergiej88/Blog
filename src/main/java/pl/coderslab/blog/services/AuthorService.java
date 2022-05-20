package pl.coderslab.blog.services;

import org.springframework.data.repository.CrudRepository;
import pl.coderslab.blog.models.Author;
import pl.coderslab.blog.models.Post;

public interface AuthorService extends CrudRepository<Author, Long> {

}