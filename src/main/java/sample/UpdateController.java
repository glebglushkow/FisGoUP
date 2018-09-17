package sample;


import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton changeConfigButton;

    @FXML
    private JFXButton copyFisGoDirButton;

    @FXML
    private JFXButton updateButton;

    @FXML
    private StackPane messagePane;

    @FXML
    private JFXDialog messageDialog;

    @FXML
    private JFXTextField ipTextField;

    private CashBoxUpdater cashBoxUpdater;

    @FXML
    void initialize() {
        cashBoxUpdater = new CashBoxUpdater();
    }

    @FXML
    private JFXDialogLayout messageDialogLayout;

    @FXML
    private StackPane mainStackPane;

    private Task copyWorker;
    private boolean isSuccessfulOperation;

    @FXML
    void updateCashBox() {
//        String ip = ipTextField.getText();
//        try {
//            cashBoxUpdater.updateCashBox(ip);
//        } catch (IllegalArgumentException e){
//            updateButton.setDisable(true);
//        }

        JFXSpinner root = new JFXSpinner();
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Information"));
        content.setBody(root);
        JFXDialog dialog = new JFXDialog(mainStackPane, content, JFXDialog.DialogTransition.CENTER);
        dialog.setOverlayClose(false);
        JFXButton button = new JFXButton("Okay");

        button.setDisable(true);
        button.setOnAction(event -> dialog.close());

        content.setActions(button);

        dialog.show();

        root.setProgress(-1);
        copyWorker = createWorker();

        root.progressProperty().unbind();
        root.progressProperty().bind(copyWorker.progressProperty());

        copyWorker.messageProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);

            }
        });
        Thread updateCashbox = new Thread(copyWorker);
        updateCashbox.start();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {

            try {
                updateCashbox.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            button.setDisable(false);
            if (isSuccessfulOperation) {
                content.setHeading(new Text("Information"));
                System.out.println("123123123123123");
                button.setText("pfddfdf");
                System.out.println();
            }
        });

    }

    public Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < 1003; i++) {
                    System.out.println(i);
                }
                updateProgress(1, 1);
                isSuccessfulOperation = true;
                return true;
            }
        };
    }

    private void test(JFXSpinner jfxSpinner) {
        for (int i = 0; i < 1000000; i++) {
            System.out.println(i);
        }
        jfxSpinner.setProgress(1);
    }

}
