package ru.netology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PostController {
  private final PostService service;

  @Autowired
  public PostController(PostService service) {
    this.service = service;
  }

  public Post save(Post post) {
    return service.save(post);
  }

  public Optional<Post> getById(long id) {
    return service.getById(id);
  }

  public void removeById(long id) {
    service.removeById(id);
  }

  public ConcurrentHashMap<Long, Post> getAll() {
    return service.getAll();
  }
}