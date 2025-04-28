package ru.netology.repository;

import org.springframework.stereotype.Component;
import ru.netology.model.Post;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PostRepository {
  private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong(0);

  public Post save(Post post) {
    if (post.getId() == 0) {
      long newId = idCounter.incrementAndGet();
      post.setId(newId);
      posts.put(newId, post);
    } else {
      if (!posts.containsKey(post.getId())) {
        throw new IllegalArgumentException("Post with id " + post.getId() + " not found");
      }
      posts.put(post.getId(), post);
    }
    return post;
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public void removeById(long id) {
    posts.remove(id);
  }

  public ConcurrentHashMap<Long, Post> getAll() {
    return posts;
  }
}