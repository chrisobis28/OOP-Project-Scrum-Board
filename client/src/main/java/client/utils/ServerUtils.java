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

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.Quote;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import org.glassfish.jersey.client.ClientConfig;

/**
 *  Utilities class for the server.
 */
public class ServerUtils {

  private String server = "http://localhost:8080/";

  /**
   * Constructor with no parameters for ServerUtils.
   */

<<<<<<< HEAD
<<<<<<< HEAD
    public List<Quote> getQuotes() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/quotes") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Quote>>() {
                });
    }
=======
  public ServerUtils() {}
>>>>>>> f5a72f031ceba810e5d07bf455ce1692d002534d
=======
  public ServerUtils() {}
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9

  /**
   * Constructor with a parameter for ServerUtils.
   *
   * @param address IP address of the server
   */
  public ServerUtils(String address) {
    this.server = address;
  }

  public void getQuotesTheHardWay() throws IOException {
    var url = new URL("http://localhost:8080/api/quotes");
    var is = url.openConnection().getInputStream();
    var br = new BufferedReader(new InputStreamReader(is));
    String line;
    while ((line = br.readLine()) != null) {
      System.out.println(line);
    }
  }

  public List<Quote> getQuotes() {
    return ClientBuilder.newClient(new ClientConfig()) //
            .target(server).path("api/quotes") //
            .request(APPLICATION_JSON) //
            .accept(APPLICATION_JSON) //
            .get(new GenericType<List<Quote>>() {
            });
  }

  public Quote addQuote(Quote quote) {
    return ClientBuilder.newClient(new ClientConfig()) //
            .target(server).path("api/quotes") //
            .request(APPLICATION_JSON) //
            .accept(APPLICATION_JSON) //
            .post(Entity.entity(quote, APPLICATION_JSON), Quote.class);
  }

  /**
   * Function to check validity of an IP address by checking if the output is the same
   *     as the output from the local server.
   *
   * @return true if the address is valid (the output of the server is the same as the Main
   *     controller), false otherwise
   */
  public Boolean checkServerValidity() {
<<<<<<< HEAD
    return ClientBuilder.newClient(new ClientConfig())
            .target(server).path("")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(String.class).equals("Hello world!");
=======
    try {
     return ClientBuilder.newClient(new ClientConfig())
              .target(server).path("")
              .request(APPLICATION_JSON)
              .accept(APPLICATION_JSON)
              .get(String.class).equals("Hello world!");
    } catch (Exception e) {
      return false;
    }
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
  }
}