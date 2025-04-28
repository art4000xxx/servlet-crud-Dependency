package ru.netology.repository;

import ru.netology.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
  private final List<Post> posts = new CopyOnWriteArrayList<>();
  private final AtomicLong idCounter = new AtomicLong(0);

  public List<Post> all() {
    return Collections.unmodifiableList(posts);
  }

  public Optional<Post> getById(long id) {
    return posts.stream().filter(p -> p.getId() == id).findFirst();
  }

  public Post save(Post post) {
    Post savedPost = new Post(post.getId(), post.getContent());
    if (savedPost.getId() == 0) {
      // Создание нового поста
      long newId = idCounter.incrementAndGet();
      savedPost.setId(newId);
      posts.add(savedPost);
    } else {
      // Обновление существующего поста
      Optional<Post> existingPost = getById(savedPost.getId());
      if (existingPost.isPresent()) {
        posts.remove(existingPost.get());
        posts.add(savedPost);
      } else {
        // Если поста нет, создаем новый с указанным ID
        posts.add(savedPost);
      }
    }
    return savedPost;
  }

  public void removeById(long id) {
    posts.removeIf(p -> p.getId() == id);
  }
}