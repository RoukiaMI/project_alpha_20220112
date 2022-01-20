/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package userInterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.ImagePattern;
import tools.HardCodedParameters;



import specifications.ViewerService;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.PhantomService;
import engine.Engine;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public class Viewer implements ViewerService, RequireReadService{
  private Engine enginr;
  private static final int spriteSlowDownRate=HardCodedParameters.spriteSlowDownRate;
  private static final double defaultMainWidth=HardCodedParameters.defaultWidth,
                              defaultMainHeight=HardCodedParameters.defaultHeight,
                              limitionWidthG=HardCodedParameters.limitationWidth,
                              limitionHeightG=HardCodedParameters.limitationHeight;

  private ReadService data;
  private ImageView heroesAvatar;
  private Image heroesSpriteSheet;
  private ArrayList<Rectangle2D> heroesAvatarViewports;
  private ArrayList<Integer> heroesAvatarXModifiers;
  private ArrayList<Integer> heroesAvatarYModifiers;
  private int heroesAvatarViewportIndex;
  private double xShrink,yShrink,shrink,xModifier,yModifier,heroesScale;

  public Viewer(){
    enginr=new Engine();
  }
  
  @Override
  public void bindReadService(ReadService service){
    data=service;
  }

  @Override
  public void init(){
    xShrink=1;
    yShrink=1;
    xModifier=0;
    yModifier=0;





    //root.getChildren().addAll(button1, button2);

    //primaryStage.setTitle("Java Button (o7planning.org)");

    //Scene scene = new Scene(root, 350, 150);
    //primaryStage.setScene(scene);
    //primaryStage.show();




    //Yucky hard-conding
    heroesSpriteSheet = new Image("file:src/images/modern soldier large_1.png");
    heroesAvatar = new ImageView(heroesSpriteSheet);
    heroesAvatarViewports = new ArrayList<Rectangle2D>();
    heroesAvatarXModifiers = new ArrayList<Integer>();
    heroesAvatarYModifiers = new ArrayList<Integer>();

    heroesAvatarViewportIndex=0;
    
    //TODO: replace the following with XML loader
    //heroesAvatarViewports.add(new Rectangle2D(301,386,95,192));
    heroesAvatarViewports.add(new Rectangle2D(570,194,115,190));
    heroesAvatarViewports.add(new Rectangle2D(398,386,133,192));
    heroesAvatarViewports.add(new Rectangle2D(155,194,147,190));
    heroesAvatarViewports.add(new Rectangle2D(785,386,127,194));
    heroesAvatarViewports.add(new Rectangle2D(127,582,135,198));
    heroesAvatarViewports.add(new Rectangle2D(264,582,111,200));
    heroesAvatarViewports.add(new Rectangle2D(2,582,123,198));
    heroesAvatarViewports.add(new Rectangle2D(533,386,115,192));
    //heroesAvatarViewports.add(new Rectangle2D(204,386,95,192));

    //heroesAvatarXModifiers.add(10);heroesAvatarYModifiers.add(-7);
    heroesAvatarXModifiers.add(6);heroesAvatarYModifiers.add(-6);
    heroesAvatarXModifiers.add(2);heroesAvatarYModifiers.add(-8);
    heroesAvatarXModifiers.add(1);heroesAvatarYModifiers.add(-10);
    heroesAvatarXModifiers.add(1);heroesAvatarYModifiers.add(-13);
    heroesAvatarXModifiers.add(5);heroesAvatarYModifiers.add(-15);
    heroesAvatarXModifiers.add(5);heroesAvatarYModifiers.add(-13);
    heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(-9);
    heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(-6);
    //heroesAvatarXModifiers.add(10);heroesAvatarYModifiers.add(-7);
    
  }

  @Override
  public Parent getPanel(){
    shrink=Math.min(xShrink,yShrink);
    xModifier=.01*shrink*defaultMainHeight;
    yModifier=.01*shrink*defaultMainHeight;


  // ICI !!!!!!!!!!!!!!!!!!


    //Yucky hard-conding //
    Rectangle map = new Rectangle(-2*xModifier+shrink*defaultMainWidth,
                                  -.2*shrink*defaultMainHeight+shrink*defaultMainHeight);
    map.setFill(Color.WHITE);
    map.setStroke(Color.DIMGRAY);
    map.setStrokeWidth(.01*shrink*defaultMainHeight);
    map.setArcWidth(.04*shrink*defaultMainHeight);
    map.setArcHeight(.04*shrink*defaultMainHeight);
    map.setTranslateX(xModifier);
    map.setTranslateY(yModifier);
    
    Text greets = new Text(-0.1*shrink*defaultMainHeight+.5*shrink*defaultMainWidth,
                           -0.1*shrink*defaultMainWidth+shrink*defaultMainHeight,
                           "Round 1");
    greets.setFont(new Font(.05*shrink*defaultMainHeight));
    
    Text score = new Text(-0.1*shrink*defaultMainHeight+.5*shrink*defaultMainWidth,
                           -0.05*shrink*defaultMainWidth+shrink*defaultMainHeight,
                           "Score: "+data.getScore());
    score.setFont(new Font(.05*shrink*defaultMainHeight));

    //

    Text life = new Text(-0.5*shrink*defaultMainHeight+.5*shrink*defaultMainWidth,
            -0.10*shrink*defaultMainWidth+shrink*defaultMainHeight,
            "Life points: "+data.getLifePoints());
    life.setFont(new Font(.05*shrink*defaultMainHeight*0.5));
    
    int index=heroesAvatarViewportIndex/spriteSlowDownRate;
    heroesScale=data.getHeroesHeight()*shrink/heroesAvatarViewports.get(index).getHeight();
    heroesAvatar.setViewport(heroesAvatarViewports.get(index));
    heroesAvatar.setFitHeight(data.getHeroesHeight()*shrink);
    heroesAvatar.setPreserveRatio(true);
    heroesAvatar.setTranslateX(shrink*data.getHeroesPosition().x+
                               shrink*xModifier+
                               -heroesScale*0.5*heroesAvatarViewports.get(index).getWidth()+
                               shrink*heroesScale*heroesAvatarXModifiers.get(index)
                              );
    heroesAvatar.setTranslateY(shrink*data.getHeroesPosition().y+
                               shrink*yModifier+
                               -heroesScale*0.5*heroesAvatarViewports.get(index).getHeight()+
                               shrink*heroesScale*heroesAvatarYModifiers.get(index)
                              );
    heroesAvatarViewportIndex=(heroesAvatarViewportIndex+1)%(heroesAvatarViewports.size()*spriteSlowDownRate);
/*    FlowPane root = new FlowPane();
    root.setPadding(new Insets(10));
    root.setHgap(10);
    root.setVgap(10);
    *//*Button button1 = new Button("Button with Text");
    Label label=new Label("TATATATA");
    button1.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        label.setText("TOITOTIOIT");
      }
    });*//*
    Button button = new Button("Show Time");
    final Label label = new Label("\"TOITOTI\"");

    //button.setText("TOITOTI");
    *//*button.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        enginr.start();
      }
    });*//*

    *//*button.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        label.setText("MOIMOI");
      }

    });*//*

    InputStream input = getClass().getResourceAsStream("file:src/images/modern soldier large.png");

    Image image = new Image("file:src/images/modern soldier large.png");
    ImageView imageView = new ImageView(image);

    //Button button2 = new Button("Button with Text & Image");
    //button2.setGraphic(imageView);

    root.getChildren().addAll(button,label);//, button2);
    //root.setMaxWidth(50);
    root.setMaxSize(5,5);
    Group panel = new Group();
    panel.getChildren().addAll(map,greets,score,heroesAvatar,root);*/

    Group panel = new Group();
    panel.getChildren().addAll(map,greets,score,life,heroesAvatar);

    ArrayList<PhantomService> phantoms = data.getPhantoms();
    PhantomService p;

    for (int i=0; i<phantoms.size();i++){
      p=phantoms.get(i);
      double radius=.5*Math.min(shrink*data.getPhantomWidth(),shrink*data.getPhantomHeight());
      Circle phantomAvatar = new Circle(radius,Color.rgb(255,156,156));
      phantomAvatar.setStroke(Color.SEAGREEN);
      Image im = new Image("file:src/images/cyclope.png", false);
      phantomAvatar.setFill(new ImagePattern(im));
      phantomAvatar.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));

      // phantomAvatar.setEffect(new Lighting());
      phantomAvatar.setTranslateX(shrink*p.getPosition().x+shrink*xModifier-radius);
      phantomAvatar.setTranslateY(shrink*p.getPosition().y+shrink*yModifier-radius);
      panel.getChildren().add(phantomAvatar);
    }

    Circle circle = new Circle(20, Color.BLUE);
    circle.relocate(100, 100);
    Image im2 = new Image("file:src/images/Plants/jalapeno.gif", false);
    circle.setFill(new ImagePattern(im2));

    final Rectangle r = new Rectangle(20, 20, Color.DARKMAGENTA);
    r.setLayoutX(400);
    r.setLayoutY(300);

    panel.getChildren().addAll(circle);
    panel.getChildren().addAll(r);

    return panel;
  }

  @Override
  public void setMainWindowWidth(double width){
    xShrink=width/defaultMainWidth;
  }
  
  @Override
  public void setMainWindowHeight(double height){
    yShrink=height/defaultMainHeight;
  }
}
