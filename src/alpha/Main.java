/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: alpha/Main.java 2015-03-11 buixuan.
 * ******************************************************/
package alpha;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import tools.HardCodedParameters;
import javafx.scene.image.Image;
import tools.User;
import tools.Sound;

import specifications.DataService;
import specifications.EngineService;
import specifications.ViewerService;
import specifications.AlgorithmService;

import data.Data;
import engine.Engine;
import userInterface.Viewer;
//import algorithm.RandomWalker;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.*;
import java.io.InputStream;
import java.util.Date;

public class Main extends Application{
  //---HARD-CODED-PARAMETERS---//
  private static String fileName = HardCodedParameters.defaultParamFileName;

  //---VARIABLES---//
  private static DataService data;
  private static EngineService engine;
  private static ViewerService viewer;
  private static AnimationTimer timer;

  //---EXECUTABLE---//
  public static void main(String[] args) {
    //readArguments(args);

    data = new Data();
    engine = new Engine();
    viewer = new Viewer();

    ((Engine)engine).bindDataService(data);
    ((Viewer)viewer).bindReadService(data);

    data.init();
    engine.init();
    viewer.init();
    
    launch(args);
  }

  @Override public void start(Stage stage) {
    final Scene scene = new Scene(((Viewer)viewer).getPanel());
    Image icone=new Image("file:src/images/modern soldier large.png");			//icone du jeu
    stage.getIcons().add(icone);									//application de l'icone
    stage.setTitle("MonJeuxFavoris");
    stage.setResizable(false);

    if(data.getIsGameOver() == true) {

      engine.stop();
      Circle circleGameOver = new Circle(800, Color.BLUE);
      circleGameOver.relocate(0, 0);
      Image go = new Image("file:src/images/Plants/go.jpg", false);
      circleGameOver.setFill(new ImagePattern(go));

      FlowPane pane = new FlowPane();
      pane.getChildren().addAll(circleGameOver);

      scene.setRoot(pane);
      stage.setScene(scene);

    }

   // scene.set
    //Root(((Viewer)viewer).getPanel().addAll(button1,button2));
/*
    Button button = new Button("Show Time");
    javafx.scene.control.Label label = new Label("");

    button.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        label.setText(new Date().toString());
      }
    });
    FlowPane root = new FlowPane();
    root.setPadding(new Insets(10));
    root.setHgap(10);
    root.setVgap(10);
    root.getChildren().addAll(button,label);//, button2);
    //root.setMaxWidth(50);
    root.setMaxSize(5,5);*/

    scene.setFill(Color.CORNFLOWERBLUE);
    scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
      @Override
        public void handle(KeyEvent event) {
          if (event.getCode()==KeyCode.LEFT) engine.setHeroesCommand(User.COMMAND.LEFT);
          if (event.getCode()==KeyCode.RIGHT) engine.setHeroesCommand(User.COMMAND.RIGHT);
          if (event.getCode()==KeyCode.UP) engine.setHeroesCommand(User.COMMAND.UP);
          if (event.getCode()==KeyCode.DOWN) engine.setHeroesCommand(User.COMMAND.DOWN);
          event.consume();
        }
    });
    scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
      @Override
        public void handle(KeyEvent event) {
          if (event.getCode()==KeyCode.LEFT) engine.releaseHeroesCommand(User.COMMAND.LEFT);
          if (event.getCode()==KeyCode.RIGHT) engine.releaseHeroesCommand(User.COMMAND.RIGHT);
          if (event.getCode()==KeyCode.UP) engine.releaseHeroesCommand(User.COMMAND.UP);
          if (event.getCode()==KeyCode.DOWN) engine.releaseHeroesCommand(User.COMMAND.DOWN);
          event.consume();
        }
    });
    scene.widthProperty().addListener(new ChangeListener<Number>() {
        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
          viewer.setMainWindowWidth(newSceneWidth.doubleValue());
        }
    });
    scene.heightProperty().addListener(new ChangeListener<Number>() {
        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
          viewer.setMainWindowHeight(newSceneHeight.doubleValue());
        }
    });
    scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Commencer Un Round 2");
        alert.setHeaderText("Results: Round 2");
        alert.setContentText("Etes vous sur de vouloir commencer un Round 2 ");

        alert.showAndWait();

        data.init();
        engine.init();
        engine.start();
        //Label label=new Label("New Round ");

      }
    });
    stage.setScene(scene);
    stage.setWidth(HardCodedParameters.defaultWidth);
    stage.setHeight(HardCodedParameters.defaultHeight);
    stage.setOnShown(new EventHandler<WindowEvent>() {
      @Override public void handle(WindowEvent event) {
        engine.start();
      }
    });
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override public void handle(WindowEvent event) {
        engine.stop();
      }
    });

    stage.show();
    //Group panel = new Group();
    //Group vue=((Viewer)viewer).getPanel();
    //;
            //.getChildren().addAll(new FlowPane(button));
    //((Viewer)viewer).getPanel().getChildrenUnmodifiable().add(root);
    timer = new AnimationTimer() {
      @Override public void handle(long l) {
        scene.setRoot(((Viewer)viewer).getPanel());
        //Group panel = new Group();
        //panel.getChildren().addAll(((Viewer)viewer).getPanel().getChildren().addAll(root));
        //scene.setRoot(new Group(((Viewer)viewer).getPanel().getChildrenUnmodifiable()).getChildren().addAll(root));
        switch (data.getSoundEffect()){
          case PhantomDestroyed:
            new MediaPlayer(new Media(getHostServices().getDocumentBase()+"src/sound/waterdrip.mp3")).play();
            break;
          case HeroesGotHit:
            new MediaPlayer(new Media(getHostServices().getDocumentBase()+"src/sound/waterdrip.mp3")).play();
            break;
          default:
            break;
        }
      }
    };
    timer.start();
  }

  //---ARGUMENTS---//
  private static void readArguments(String[] args){
    if (args.length>0 && args[0].charAt(0)!='-'){
      System.err.println("Syntax error: use option -h for help.");
      return;
    }
    for (int i=0;i<args.length;i++){
      if (args[i].charAt(0)=='-'){
	if (args[i+1].charAt(0)=='-'){
	  System.err.println("Option "+args[i]+" expects an argument but received none.");
	  return;
	}
	switch (args[i]){
	  case "-inFile":
	    fileName=args[i+1];
	    break;
	  case "-h":
	    System.out.println("Options:");
	    System.out.println(" -inFile FILENAME: (UNUSED AT THE MOMENT) set file name for input parameters. Default name is"+HardCodedParameters.defaultParamFileName+".");
	    break;
	  default:
	    System.err.println("Unknown option "+args[i]+".");
	    return;
	}
	i++;
      }
    }
  }
}
