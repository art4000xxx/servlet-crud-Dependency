Servlets CRUD Application
Простое веб-приложение на Java с использованием сервлетов для реализации CRUD-операций над сущностью Post. Разработано для задания Netology.
Функциональность

API:
GET /api/posts — получить все посты.
POST /api/posts — создать/обновить пост (JSON: {"id":0,"content":"Text"}).
GET /api/posts/{id} — получить пост по ID.
DELETE /api/posts/{id} — удалить пост.


Обработка ошибок: 404 (не найден), 400 (некорректный ID).
Thread-safe репозиторий для хранения постов.

Технологии

Java 8, Servlet API 4.0.1, Gson 2.10.1.
Maven для сборки.
WebApp Runner 9.0.89.0 для запуска.

Установка и запуск

Сборка:
mvn clean package

Создаёт target/servlets-1.0-SNAPSHOT.war и target/dependency/webapp-runner.jar.

Запуск:
java -jar target/dependency/webapp-runner.jar target/servlets-1.0-SNAPSHOT.war

Приложение доступно на http://localhost:8080.

Тестирование:
# Получить все посты
curl -X GET http://localhost:8080/api/posts

# Создать пост
curl -X POST http://localhost:8080/api/posts -H "Content-Type: application/json" -d '{"id":0,"content":"Hello, world!"}'

# Получить пост по ID
curl -X GET http://localhost:8080/api/posts/1

# Обновить пост
curl -X POST http://localhost:8080/api/posts -H "Content-Type: application/json" -d '{"id":1,"content":"Updated post"}'

# Удалить пост
curl -X DELETE http://localhost:8080/api/posts/1



Статус

Приложение собрано и протестировано.
CRUD-операции работают.
Задание со звёздочкой (WebApp Runner) выполнено.


Проблемы и решения

WebApp Runner: Исправлена зависимость (com.heroku:webapp-runner:9.0.89.0).


