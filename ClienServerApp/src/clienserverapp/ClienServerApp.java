/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienserverapp;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class ClienServerApp extends Application {

    BorderPane pane;
    TextArea txtArea;
    FlowPane fpane;
    TextField txt;
    Button btnSend;
    Button btnSave;
    Button btnOpen;
    Button btnExit;
    Socket mySocket;
    DataInputStream dis;
    PrintStream ps;

    @Override
    public void init() {
        fpane = new FlowPane();
        txt = new TextField("Enter your message");
        btnSend = new Button("Send");
        btnSend.setDefaultButton(true);
        btnOpen = new Button("Open");
        btnOpen.setDefaultButton(true);
        btnSave = new Button("Save");
        btnSave.setDefaultButton(true);
        btnExit = new Button("Exit");
        btnSave.setDefaultButton(true);
        txtArea = new TextArea();
        txtArea.setEditable(false);
        pane = new BorderPane();
        pane.setCenter(txtArea);
        fpane.getChildren().addAll(txt, btnSend, btnOpen, btnSave, btnExit);
        pane.setBottom(fpane);
        btnSend.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                ps.println(txt.getText());
                txt.setText("");
            }
        });
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("txt files", "*.txt");
                File savedFile = fc.showSaveDialog(null);
                try {
                    FileWriter fw = new FileWriter(savedFile);
                    fw.write(txtArea.getText());
                    fw.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        btnOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FileChooser fc = new FileChooser();
                    FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("txt files", "*.txt");
                    File opened_file = fc.showOpenDialog(null);
                    Scanner scan = new Scanner(opened_file);
                    String fileContent = "";
                    while (scan.hasNext()) {
                        txtArea.appendText(scan.nextLine() + '\n');
//                          fileContent = fileContent.concat(scan.nextLine()+"\n");
                    }
                    scan.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        try {
            mySocket = new Socket("127.0.0.1", 5005);
            dis = new DataInputStream(mySocket.getInputStream());
            ps = new PrintStream(mySocket.getOutputStream());
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        String replyMsg;
                        try {
                            replyMsg = dis.readLine();
                            txtArea.appendText("\n" + replyMsg);
                        } catch (IOException ex) {
//                            Logger.getLogger(ClienServerApp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }).start();
        } catch (IOException ex) {
//            Logger.getLogger(ClienServerApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void start(Stage primaryStage) {

        Scene scene = new Scene(pane, 300, 250);

        primaryStage.setTitle("ChatRoom");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
//       new ClienServerApp();
    }

    private static class txtarea {

        public txtarea() {
        }
    }

}
