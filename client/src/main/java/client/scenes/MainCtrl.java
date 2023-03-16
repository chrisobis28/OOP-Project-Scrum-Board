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
package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.image.Image;

public class MainCtrl {

    private Stage primaryStage;

    private WelcomeScreenCtrl overviewCtrl;
    private Scene overview;

    private BoardViewCtrl boardViewCtrl;
    private Scene boardView;

<<<<<<< HEAD
<<<<<<< HEAD
    public void initialize(Stage primaryStage, Pair<QuoteOverviewCtrl, Parent> overview,
                           Pair<AddQuoteCtrl, Parent> add) {
=======
    //public void initialize(Stage primaryStage, Pair<WelcomeScreenCtrl, Parent> overview, Pair<BoardViewCtrl, Parent> board)
    public void initialize(Stage primaryStage, Pair<WelcomeScreenCtrl, Parent> overview) {
>>>>>>> f5a72f031ceba810e5d07bf455ce1692d002534d
=======
    /**
     * Initialize the stage with the scenes for the application, along with their respective controllers.
     *
     * @param primaryStage
     * @param overview
     * @param board
     */
    public void initialize(Stage primaryStage, Pair<WelcomeScreenCtrl, Parent> overview,
                           Pair<BoardViewCtrl, Parent> board) {
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
        this.primaryStage = primaryStage;

        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

<<<<<<< HEAD
        //this.boardViewCtrl = board.getKey();
        //this.boardView = new Scene(board.getValue());
=======
        this.boardViewCtrl = board.getKey();
        this.boardView = new Scene(board.getValue());
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9

        showOverview();
        primaryStage.show();

    }

    /**
     * Show the Welcome screen.
     */
    public void showOverview() {

        //When starting the app show the welcome screen
        primaryStage.setTitle("Talio");
        primaryStage.setScene(overview);

        //Icon for the App
        Image icon = new Image("icon.png");
        primaryStage.getIcons().add((icon));

        overview.setOnKeyPressed(e -> overviewCtrl.keyPressed(e));
    }

<<<<<<< HEAD
    public void showBoard() {
        primaryStage.setTitle("Your Board");
        primaryStage.setScene(boardView);
=======
    /**
     * Show the Board View scene.
     */
    public void showBoard() {
        primaryStage.setTitle("Your Board");
        primaryStage.setScene(boardView);

>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
        //for key presses:
        boardView.setOnKeyPressed(e -> boardViewCtrl.keyPressed(e));
    }

}