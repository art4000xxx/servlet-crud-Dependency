package ru.netology.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.config.AppConfig;
import ru.netology.controller.PostController;
import ru.netology.model.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/posts/*")
public class MainServlet extends HttpServlet {
  private PostController controller;
  private ObjectMapper mapper;

  @Override
  public void init() throws ServletException {
    final var context = new AnnotationConfigApplicationContext(AppConfig.class);
    controller = context.getBean(PostController.class);
    mapper = new ObjectMapper();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String pathInfo = req.getPathInfo();
    if (pathInfo == null || pathInfo.equals("/")) {
      resp.getWriter().write(mapper.writeValueAsString(controller.getAll()));
    } else {
      long id = Long.parseLong(pathInfo.substring(1));
      controller.getById(id).ifPresentOrElse(
              post -> {
                try {
                  resp.getWriter().write(mapper.writeValueAsString(post));
                } catch (IOException e) {
                  throw new RuntimeException(e);
                }
              },
              () -> resp.setStatus(HttpServletResponse.SC_NOT_FOUND)
      );
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    Post post = mapper.readValue(req.getReader(), Post.class);
    Post savedPost = controller.save(post);
    resp.getWriter().write(mapper.writeValueAsString(savedPost));
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
    String pathInfo = req.getPathInfo();
    if (pathInfo != null && !pathInfo.equals("/")) {
      long id = Long.parseLong(pathInfo.substring(1));
      controller.removeById(id);
    } else {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }
}