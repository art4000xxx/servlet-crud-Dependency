package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private static final String API_POSTS_PATH = "/api/posts";
  private static final String API_POSTS_ID_PATH_REGEX = "/api/posts/\\d+";
  private static final String GET_METHOD = "GET";
  private static final String POST_METHOD = "POST";
  private static final String DELETE_METHOD = "DELETE";

  private PostController controller;

  @Override
  public void init() {
    final PostRepository repository = new PostRepository();
    final PostService service = new PostService(repository);
    controller = new PostController(service);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    try {
      final String path = req.getRequestURI();
      final String method = req.getMethod();

      if (GET_METHOD.equals(method) && API_POSTS_PATH.equals(path)) {
        controller.all(resp);
        return;
      }
      if (GET_METHOD.equals(method) && path.matches(API_POSTS_ID_PATH_REGEX)) {
        final long id = parseId(path);
        controller.getById(id, resp);
        return;
      }
      if (POST_METHOD.equals(method) && API_POSTS_PATH.equals(path)) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (DELETE_METHOD.equals(method) && path.matches(API_POSTS_ID_PATH_REGEX)) {
        final long id = parseId(path);
        controller.removeById(id, resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  private long parseId(String path) {
    return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
  }
}
