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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.image.Image;

public class MainCtrl {

    private Stage primaryStage;

    private WelcomeScreenCtrl overviewCtrl;
    private Scene overview;

    private BoardViewCtrl boardViewCtrl;
    private Scene boardView;

    private AddCardlistCtrl addListCtrl;
    private Scene addList;
    private Image icon;

    /**
     * Initialize the stage with the scenes for the application, along with their respective controllers.
     *
     * @param primaryStage the main stage of the application
     * @param overview the first scene, which is the welcome screen here
     * @param board the board view scene
     */
    public void initialize(Stage primaryStage, Pair<WelcomeScreenCtrl, Parent> overview,
                           Pair<BoardViewCtrl, Parent> board, Pair<AddCardlistCtrl, Parent> addList) {
        this.primaryStage = primaryStage;

        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.boardViewCtrl = board.getKey();
        this.boardView = new Scene(board.getValue());

        this.addListCtrl = addList.getKey();
        this.addList = new Scene(addList.getValue());

        icon = new Image("icon.png");

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
        primaryStage.getIcons().add(icon);

        overview.setOnKeyPressed(e -> overviewCtrl.keyPressed(e));
    }

    /**
     * Show the Board View scene.
     */
    public void showBoard() {
        primaryStage.setTitle("Your Board");
        primaryStage.setScene(boardView);

        //for key presses:
        boardView.setOnKeyPressed(evt -> boardViewCtrl.keyPressed(evt));
    }

    /**
     * Show the new stage where you can add a new list to the board.
     */
    public void showAddList() {
        Stage stage = new Stage();
        stage.setTitle("New List");
        stage.getIcons().add(icon);
        stage.setScene(addList);
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        addList.setOnKeyPressed(e -> addListCtrl.keyPressed(e));
        stage.showAndWait();

        boardViewCtrl.refreshBoard();
    }
}