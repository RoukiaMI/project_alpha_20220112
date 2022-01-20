/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-11 buixuan.
 * ******************************************************/
package data;

import tools.HardCodedParameters;
import tools.Position;
import tools.Sound;

import specifications.DataService;
import specifications.PhantomService;

import data.ia.MoveLeftPhantom;

import java.util.ArrayList;

public class Data implements DataService{
  //private Heroes hercules;
  private Position heroesPosition;
  private int stepNumber, score,scoreHero,lifePoints;
  private ArrayList<PhantomService> phantoms;
  private double heroesWidth,heroesHeight,phantomWidth,phantomHeight;
  private Sound.SOUND sound;
  private boolean hitting,isGameOver;

  public Data(){}

  @Override
  public void init(){
    //hercules = new Heroes;
    heroesPosition = new Position(HardCodedParameters.heroesStartX,HardCodedParameters.heroesStartY);
    phantoms = new ArrayList<PhantomService>();
    stepNumber = 0;
    score = 0;
    scoreHero=0;
    heroesWidth = HardCodedParameters.heroesWidth;
    heroesHeight = HardCodedParameters.heroesHeight;
    phantomWidth = HardCodedParameters.phantomWidth;
    phantomHeight = HardCodedParameters.phantomHeight;
    sound = Sound.SOUND.None;
    isGameOver=false;
    lifePoints=100;

  }

  @Override
  public Position getHeroesPosition(){ return heroesPosition; }
  
  @Override
  public double getHeroesWidth(){ return heroesWidth; }
  
  @Override
  public double getHeroesHeight(){ return heroesHeight; }
  
  @Override
  public double getPhantomWidth(){ return phantomWidth; }
  
  @Override
  public double getPhantomHeight(){ return phantomHeight; }

  @Override
  public int getStepNumber(){ return stepNumber; }
  
  @Override
  public int getScore(){ return score; }

  @Override
  public int getScoreH(){ return scoreHero; }
  @Override
  public void setScoreH(int s){scoreHero=s; }
  @Override
  public ArrayList<PhantomService> getPhantoms(){ return phantoms; }
  
  @Override
  public Sound.SOUND getSoundEffect() { return sound; }

  @Override
  public void setHeroesPosition(Position p) { if(p.y<=100&& p.y>=0&&p.x>=0&&p.x<=100){heroesPosition=p; }}
  
  @Override
  public void setStepNumber(int n){

    stepNumber=n; }
  
  @Override
  public void addScore(int score){ this.score+=score; }

  public void setScoreToZero(){this.score=0;}

  @Override
  public void addPhantom(Position p) { phantoms.add(new MoveLeftPhantom(p)); }

  
  @Override
  public void setPhantoms(ArrayList<PhantomService> phantoms) { this.phantoms=phantoms; }
  
  @Override
  public void setSoundEffect(Sound.SOUND s) { sound=s; }

@Override
  public boolean getIsHint(){

  return hitting;
}
@Override
public void setIsHint(boolean htiPhantom){
    this.hitting=htiPhantom;
}
@Override
public boolean killPhantom(PhantomService p,ArrayList<PhantomService> phantom,boolean hitPhantom){
    boolean res=false;
    if(hitPhantom){
      for(int i =0; i<phantom.size();i++){
        if(phantom.get(i)==p){
          phantoms.remove(i);
          res=true;
          return res;
        }
      }


    }
  return res;
}
@Override
public int getLifePoints() {
    return lifePoints;
  }

  @Override
  public void setLifePoints(int lifePoints) {
    this.lifePoints = lifePoints;
  }

  @Override
  public void removeLifePoints(int lifePoints) {
    // TODO Auto-generated method stub
    this.lifePoints-= lifePoints;
  }

  @Override
  public boolean getIsGameOver() {
    return isGameOver;
  }

  @Override
  public void setIsGameOver(boolean isGameOver) {
    this.isGameOver = isGameOver;

  }

}
