package ru.netology.controller;

import com.google.gson.Gson;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
  public static final String APPLICATION_JSON = "application/json";
  private final PostService service;

  public PostController(PostService service) {
    this.service = service;
  }

  public void all(HttpServletResponse response) throws IOException {
    writeJsonResponse(service.all(), response);
  }

  public void getById(long id, HttpServletResponse response) throws IOException {
    try {
      writeJsonResponse(service.getById(id), response);
    } catch (NotFoundException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  public void save(Reader body, HttpServletResponse response) throws IOException {
    final Gson gson = new Gson();
    final Post post = gson.fromJson(body, Post.class);
    writeJsonResponse(service.save(post), response);
  }

  public void removeById(long id, HttpServletResponse response) throws IOException {
    try {
      service.removeById(id);
      response.setStatus(HttpServletResponse.SC_OK);
    } catch (NotFoundException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  private void writeJsonResponse(Object data, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final Gson gson = new Gson();
    response.getWriter().print(gson.toJson(data));
  }
}