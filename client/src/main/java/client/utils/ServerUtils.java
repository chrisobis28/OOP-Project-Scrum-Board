/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client.utils;

import commons.*;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 *  Utilities class for the server.
 */
public class ServerUtils {

  private String server = "http://localhost:8080/";
  //password is currently hard coded, not mandatory but we might want to change it in the future
  private final String adminPassword = "oopp51";

  private WebTarget serverTarget;

  /**
   * Constructor with no parameters for ServerUtils.
   */
  public ServerUtils() {
    this.serverTarget = ClientBuilder.newClient(new ClientConfig()).target(server);
    this.session = connect("ws://localhost:8080/websocket");
  }

  /**
   * Constructor with a parameter for ServerUtils.
   *
   * @param address IP address of the server
   */
  public ServerUtils(String address) {
    this.serverTarget = ClientBuilder.newClient(new ClientConfig()).target(server);
    this.session = connect("ws://localhost:8080/websocket");
  }

  //testing constructor
  public ServerUtils(String address, WebTarget target) {
    this.server = address;
    this.serverTarget = target;
  }

  /**
   * Getter for the admin password.
   * @return String representing the admin login password
   */
  public String getAdminPassword() {
    return adminPassword;
  }

  /**
   * Function to check validity of an IP address by checking if the output is the same
   *     as the output from the local server.
   *
   * @return true if the address is valid (the output of the server is the same as the Main
   *     controller), false otherwise
   */
  public Boolean checkServerValidity() {
    try {
     return serverTarget.path("")
              .request(APPLICATION_JSON)
              .accept(APPLICATION_JSON)
              .get(String.class).equals("Hello world!");
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Add a card list to the repository by sending a POST request with the card list
   *  to the server following the path "api/cardlist".
   *
   * @param cardlist The card list to be added to the repository.
   * @return Response entity for the Cardlist.
   */
  public Cardlist addCardList(Cardlist cardlist) {
    return serverTarget.path("api/cardlist") //
            .request(APPLICATION_JSON) //
            .accept(APPLICATION_JSON) //
            .post(Entity.entity(cardlist, APPLICATION_JSON), Cardlist.class);
  }

  /**
   * Add as card to the repo by sending a post request to the server to the path 'api/card'.
   *
   * @param card The card to be added to the repository
   * @return Response entity for the Card
   */
  public Card addCard(Card card){
    return serverTarget.path("api/cards")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .post(Entity.entity(card, APPLICATION_JSON), Card.class);
  }

  public Task addTask(Task task){
    return serverTarget.path("api/tasks")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .post(Entity.entity(task, APPLICATION_JSON), Task.class);
  }

  /**
   * A function to send a GET request to the server for the card lists
   *  for a given board id, arranged in a list.
   *
   * @return The list containing all the card lists for a board.
   */
  public List<Cardlist> getCardLists(long id) {
    String path = "api/cardlist/" + id;
    return ClientBuilder.newClient(new ClientConfig()) //
            .target(server).path(path) //
            .request(APPLICATION_JSON) //
            .accept(APPLICATION_JSON) //
            .get(new GenericType<List<Cardlist>>() {
            });
  }

  public Cardlist getCardlistById(long id) {
    String path = "api/cardlist/specific/" + id;
    return ClientBuilder.newClient(new ClientConfig())
            .target(server).path(path)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<Cardlist>() {
            });
  }

  public List<Card> getCards(long id) {
    String path = "api/cards/all/" + id;
    return ClientBuilder.newClient(new ClientConfig()) //
            .target(server).path(path) //
            .request(APPLICATION_JSON) //
            .accept(APPLICATION_JSON) //
            .get(new GenericType<List<Card>>() {
            });
  }

  /**
   * A function that sends a DELETE request to the server, targeting
   *  a specific card list by its id.
   *
   * @param id The id of the card list to be deleted.
   */
  public void deleteCardList(long id) {
    try {
      String path = "api/cardlist/" + id;
      ClientBuilder.newClient(new ClientConfig()) //
              .target(server).path(path) //
              .request(APPLICATION_JSON) //
              .accept(APPLICATION_JSON) //
              .delete();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void deleteTask(long id) {
    try {
      String path = "api/tasks/" + id;
      ClientBuilder.newClient(new ClientConfig()) //
              .target(server).path(path) //
              .request(APPLICATION_JSON) //
              .accept(APPLICATION_JSON) //
              .delete();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Add a Board to the repo by sending a POST request to the path 'api/board'.
   *
   * @param board The Board to be added to the repo
   * @return Response Entity for the Board
   */
  public Board addBoard(Board board) {
    return serverTarget.path("api/boards")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .post(Entity.entity(board, APPLICATION_JSON), Board.class);
  }

  /**
   * Get a list of all the boards inside the repository by sending a GET request.
   *
   * @return A list of all the boards in the repository
   */
  public List<Board> getBoardList() {
    return ClientBuilder.newClient(new ClientConfig())
            .target(server).path("api/boards")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<List<Board>>() {
            });
  }

  /**
   * Get the board with the requested id by sending a GET request.
   *
   * @param id Id of the Board to be returned
   * @return A board with that respective id
   */
  public Board getBoardById(long id) {
    String path = "api/boards/" + id;
    return ClientBuilder.newClient(new ClientConfig())
            .target(server).path(path)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<Board>() {
            });
  }

//  /**
//   * Get the board with the requested name by sending a GET request.
//   *
//   * @param name Name of the board to be returned
//   * @return A board with the given name
//   */
//  public Board getBoardbyName(String name) {
//    String path = "api/boards/" + name;
//    return ClientBuilder.newClient(new ClientConfig())
//            .target(server).path(path)
//            .request(APPLICATION_JSON)
//            .accept(APPLICATION_JSON)
//            .get(new GenericType<Board>() {
//            });
//  }

  /**
   * Delete a board with a specific id by sending a DELETE request
   *
   * @param id Id of the board to be deleted
   */
  public void deleteBoard(long id) {
    try {
      String path = "api/boards/" + id;
      ClientBuilder.newClient(new ClientConfig())
              .target(server).path(path)
              .request(APPLICATION_JSON)
              .accept(APPLICATION_JSON)
              .delete();
      } catch (Exception e) {
      e.printStackTrace();
      } 
  }

  /**
   * Editing a specific CardList by posting the newer version
   *
   * @param edit CardList with the same id to be replaced
   */
  public void editCardList(Cardlist edit) {
    try {
      String path = "api/cardlist/edit";
      ClientBuilder.newClient(new ClientConfig()) //
              .target(server).path(path) //
              .request(APPLICATION_JSON) //
              .accept(APPLICATION_JSON) //
              .post(Entity.entity(edit, APPLICATION_JSON), Cardlist.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Editing a specific Board by posting the newer version
   *
   * @param edit Board with the same id to be replaced
   */
  public void editBoard(Board edit) {
    try {
      String path = "api/boards/edit";
      ClientBuilder.newClient(new ClientConfig()) //
              .target(server).path(path) //
              .request(APPLICATION_JSON) //
              .accept(APPLICATION_JSON) //
              .post(Entity.entity(edit, APPLICATION_JSON), Board.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void editCard(Card edit) {
    try {
      String path = "api/cards/edit";
      ClientBuilder.newClient(new ClientConfig()) //
              .target(server).path(path) //
              .request(APPLICATION_JSON) //
              .accept(APPLICATION_JSON) //
              .post(Entity.entity(edit, APPLICATION_JSON), Card.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Delete a card with a specific id by sending a DELETE request
   *
   * @param id Id of the card to be deleted
   */
  public void deleteCard(long id) {
    try {
      String path = "api/cards/" + id;
      ClientBuilder.newClient(new ClientConfig()) //
              .target(server).path(path) //
              .request(APPLICATION_JSON) //
              .accept(APPLICATION_JSON) //
              .delete();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Get the card with the requested id by sending a GET request.
   *
   * @param id Id of the Card to be returned
   * @return A Card with that respective id
   */
  public Card getCardById(long id) {
    return ClientBuilder.newClient(new ClientConfig())
            .target(server).path("api/cards/" + id)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<Card>() {
            });
  }

  //A separate thread so that the data synchronization can run in parallel.
  private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();

  /**
   * Method for long-polling for updates to synchronize data between clients.
   * @param consumer
   */
  public void registerForUpdates(Consumer<Board> consumer) {

    EXEC.submit(() -> {
      while(!Thread.interrupted()) {
            var res = ClientBuilder.newClient(new ClientConfig()) //
                    .target(server).path("api/boards/update") //
                    .request(APPLICATION_JSON) //
                    .accept(APPLICATION_JSON) //
                    .get(Response.class);

            if(res.getStatus() == 204) {
              continue;
            }

            var board = res.readEntity(Board.class);
            consumer.accept(board);
      }
    });
  }

  /**
   * Method to stop the parallel thread.
   */
  public void stop() {
    EXEC.shutdownNow();
  }

  private StompSession session;

  private StompSession connect(String destination){
    var client = new StandardWebSocketClient();
    var stomp = new WebSocketStompClient(client);
    stomp.setMessageConverter(new MappingJackson2MessageConverter());
    try{
      return stomp.connect(destination, new StompSessionHandlerAdapter() {}).get();
      }
    catch (InterruptedException e){
      Thread.currentThread().interrupt();
    }
    catch (ExecutionException e){
      throw new RuntimeException();
    }
    throw new IllegalStateException();
  }

  public void registerForCards(String destination, Consumer<Card> cardConsumer){
    session.subscribe(destination, new StompFrameHandler() {
      @Override
      public Type getPayloadType(StompHeaders headers) {
        return Card.class;
      }

      @Override
      public void handleFrame(StompHeaders headers, Object payload) {
        cardConsumer.accept((Card) payload);
      }
    });
  }


}