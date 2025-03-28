package utils;

public class VertxConstants {

  public static final int ZERO = 0;
  public static final int ONE = 1;
  public static final int DEFAULT_PORT = 8080;
  public static final int SUCCESSFUL = 200;
  public static final int CLIENT_ERROR = 400;
  public static final int SERVER_ERROR = 500;
  public static final int RATE_ERROR = 429;
  public static final int RATE_LIMIT = 10;
  public static final int FIVE = 5;
  public static final int USERNAME_MISSING = 402;
  public static final int AUTHENTICATION_ERROR = 403;
  public static final int JSON_ERROR = 400;

  public static final long RATE_LIMIT_WINDOW_MS = 10000L;  // 10 seconds

  public static final String CONFIG_JSON = "config.json";
  public static final String TASK_NAME = "taskName";
  public static final String COMPLETED = "completed";
  public static final String GET_ALL_TASKS = "getAllTasks.vertx.addr";
  public static final String GET_SINGLE_TASK = "getSingleTask.vertx.addr";
  public static final String CREATE_TASK = "createTask.vertx.addr";
  public static final String SORT_TASKS = "sortTaks.vertx.addr";
  public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
  public static final String INVALID_JSON_PAYLOAD = "Invalid JSON Payload";
  public static final String CONTENT_TYPE = "content-type";
  public static final String APPLICATION_JSON = "application/json";
  public static final String TASK_NOT_FOUND = "Task not found";
  public static final String HTTP = "http";
  public static final String PORT = "port";
  public static final String CREATE_FRUITS = "createFruits.vertx.addr";
  public static final String FRUIT_NAME = "name";
  public static final String IS_SWEET = "isSweet";
  public static final String GET_ALL_FRUITS = "getAllFruits.vertx.addr";
  public static final String X_AUTHENTICATE = "X-AUTHENTICATE";
  public static final String X_AUTHENTICATE_KEY = "#QWERTY1781237812";
  public static final String UNAUTHORIZED_REQUEST = "Request is Unauthorized";
  public static final String USER_NAME = "X-USERNAME";
  public static final String MISSING_USERNAME = "Username is missing";
  public static final String TOO_MANY_REQUESTS = "Too many Request, Please wait for cooldown";
  public static final String ID = "id";
  public static final String PRIORITY = "priority";
  public static final String INVALID_JSON = "Invalid JSON format";

}
