package it.unisa.diem.javaadv24.group01.mysimpleirtool.gui;

import it.unisa.diem.javaadv24.group01.mysimpleirtool.components.Directory;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * <p></p>
 *
 * @author Guerra Simone
 * @version 1.0
 */
public class HelloController {

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox vbox1;

    @FXML
    private VBox vbox2;

    @FXML
    private VBox vbox3;

    @FXML
    private VBox vbox4;

    @FXML
    private VBox vbox5;

    @FXML
    private Button directoryButton;

    @FXML
    private Button goToViewButt;

    @FXML
    private Button buttonSearch;

    @FXML
    private Button changeDirectoryButton;

    @FXML
    private Button changeDirectoryButton2;

    @FXML
    private TextField textFieldSearch;

    @FXML
    private TextField textFieldSearch2;

    @FXML
    private Text textQuery;

    @FXML
    private Text textQuery2;

    @FXML
    private ProgressBar progressBar;

    /**
     * <p>Il metodo initialize rende visibile il vbox principale</p>
     *
     * @author Guerra Simone
     * @version 1.0
     */


    @FXML
    public void initialize() {
        // Nascondiamo tutti i VBox dello StackPane
        for (int i = 0; i < stackPane.getChildren().size(); i++) {
            stackPane.getChildren().get(i).setVisible(false);
        }

        // Mostriamo il primo VBox
        vbox1.setVisible(true);
    }

    /**
     * <p>Il metodo handleSelectFolder() si occupa di gestire la selezione della directory</p>
     * <p>Mostra una finestra di dialogo per la selezione</p>
     * <p>successivamente utilizza un task per gestire la schermata di caricamento</p>
     *
     * @author Guerra Simone
     * @version 1.0
     */

    @FXML
    private void handleSelectFolder() {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");

        Stage primaryStage = (Stage) stackPane.getScene().getWindow();
        File choosenDirectory;
        choosenDirectory = directoryChooser.showDialog(primaryStage);

        String path = choosenDirectory.getAbsolutePath();
        System.out.println(path);
        // Mostriamo la schermata di caricamento
        showLoadingScreen();

        // Simuliamo un'operazione di caricamento in background
        Task<Void> loadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                int totalSteps = 100; // Numero totale di passi del caricamento
                for (int i = 0; i < totalSteps; i++) {
                    if (isCancelled()) {
                        break;
                    }
                    updateProgress(i + 1, totalSteps);
                    Thread.sleep(40); // Simula un'attesa di 40 ms per passo
                }
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                // Nascondiamo la schermata di caricamento e mostriamo di nuovo il primo VBox
                vbox2.setVisible(false);
                vbox3.setVisible(true);

            }

            @Override
            protected void failed() {
                super.failed();
                // Gestiamo il fallimento
                hideLoadingScreen();
            }
        };

        // Collega la progressProperty della ProgressBar al Task
        progressBar.progressProperty().bind(loadTask.progressProperty());

        // Esegui il task in un nuovo thread
        new Thread(loadTask).start();
    }

    /**
     * <p>I metodi showLoadingScreen() e hideLoadingScreen() <br>
     * si occupano della visualizzazione delle diverse schermate</p>
     *
     * @author Guerra Simone
     * @version 1.0
     */

    private void showLoadingScreen() {
        // Nascondiamo tutti i VBox nello StackPane
        for (int i = 0; i < stackPane.getChildren().size(); i++) {
            stackPane.getChildren().get(i).setVisible(false);
        }

        // Mostriamo la schermata di caricamento
        vbox2.setVisible(true);
    }

    private void hideLoadingScreen() {

        vbox2.setVisible(false);

        vbox1.setVisible(true);

    }

    /**
     * <p>Il metodo switchToList() viene utilizzato per passare alla schermata contenente la tableView<br>
     * Questo metodo viene richiamato sia dal bottone : {@see it.unisa.diem.javaadv24.group01.mysimpleirtool.gui.HelloController#goToViewButt};<br>
     * che dal tasto "Invio"</p>
     *
     * @author Guerra Simone
     * @version 1.0
     */

    @FXML
    private void switchToList(){
        //metodo per il primo bottone che porta alla lista dei file

        //TODO: gestire il caso in cui ci sono corrispondenze per quella parola
        String query = textFieldSearch.getText();
        textQuery.setText(query);
        textQuery2.setText(query);

        if (query.isEmpty()) {
            vbox3.setVisible(false); //nascondiamo la vista relativa alla pagina di ricerca
            vbox4.setVisible(true);  //mostriamo la vista contenente la tableView corrispondente alla parola cercata
        }else{
            vbox3.setVisible(false);
            vbox5.setVisible(true);
        }
    }

    /**
     * <p>Il metodo switchToList2(), viene richiamato quando si vuole cercare una nuova parola<br>
     * Quindi nel caso in cui siamo già nella schermata con la tableView e vogliamo cercare una parola diversa,<br>
     * tramite il bottone o il tasto "Invio" richiamiamo questo metodo</p>
     *
     * @author Guerra Simone
     * @version 1.0
     */


    @FXML
    private void switchToList2(){

        String query2 = textFieldSearch2.getText();
        textQuery2.setText(query2);

        if (query2.isEmpty()) {
            vbox3.setVisible(false);
            vbox4.setVisible(true);
        }else{
            vbox3.setVisible(false);
            vbox5.setVisible(true);
        }

    }

    /**
     * <p>Il metodo changeDirectory(), viene utilizzato per cambiare la directory di riferimento per i file.<br>
     * Metodo richiamato tramite i bottoni: changeDirectory() e changeDirectory2()</p>
     *
     * @author Guerra Simone
     * @version 1.0
     */


    @FXML
    private void changeDirectory() {
        //metodo associato ai bottoni per tornare alla scelta della directory
        for (int i = 0; i < stackPane.getChildren().size(); i++) {
            stackPane.getChildren().get(i).setVisible(false);
        }

        vbox1.setVisible(true);

    }

}