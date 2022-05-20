package pl.coderslab.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.blog.models.Author;
import pl.coderslab.blog.models.Comment;
import pl.coderslab.blog.models.Post;
import pl.coderslab.blog.services.AuthorService;
import pl.coderslab.blog.services.CommentService;
import pl.coderslab.blog.services.PostService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostService postService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/blog")
    public String blogMain(Model model){
        Iterable<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text,
                              @RequestParam Long author, Model model){
        if(title.isBlank() || anons.isBlank() || full_text.isBlank()){
            return "redirect:/blog";
        }
        Post post = new Post(title, anons, full_text);
        Optional<Author> authorEntity = authorService.findById(author);
        if(authorEntity == null){
            throw new EntityNotFoundException("author");
        }

        post.setAuthor(authorEntity.get());
        postService.save(post);

        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value="id") long id, Model model){
        if(!postService.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> result = postService.findById(id);
        Post post = result.get();
        model.addAttribute("post", post);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value="id") long id, Model model){
        if(!postService.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> result = postService.findById(id);
        Post post = result.get();
        model.addAttribute("post", post);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text, Model model){
        Post post = postService.findById(id).orElseThrow();
        post.setTitle(title);
        post.setTitle(anons);
        post.setTitle(full_text);
        postService.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/addcomment")
    public String blogPostAdd(@RequestParam Long postId,
                               @RequestParam String content,
                               Model model){
        Comment comment = new Comment(content);
        Optional<Post> post = postService.findById(postId);
        comment.setPost(post.get());
        commentService.save(comment);

        return "redirect:/blog";
    }


}
