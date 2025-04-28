package ru.netology.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PostService {
  private final PostRepository repository;

  @Autowired
  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public Post save(Post post) {
    return repository.save(post);
  }

  public Optional<Post> getById(long id) {
    return repository.getById(id);
  }

  public void removeById(long id) {
    repository.removeById(id);
  }

  public ConcurrentHashMap<Long, Post> getAll() {
    return repository.getAll();
  }
}