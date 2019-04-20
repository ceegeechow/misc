import java.util.Collections;
import java.util.Arrays;
import java.lang.Math;

PImage human;
PImage street;
String[] anger, joy, fear, tweet1, tweet2, tweet3;
int[] lengths = new int[3];
int min_len = 0;
int i = 0;
float intensity1, intensity2, intensity3;
float horizon = 300;

ArrayList<Human> humans = new ArrayList<Human>();
Human h1, h2, h3;

void setup(){
  size(1600,800);
  street = loadImage("street3.jpg");
  human = loadImage("human.png");
  
  //load twitter data
  anger = loadStrings("anger.txt");
  joy = loadStrings("joy.txt");
  fear = loadStrings("fear.txt");
  Collections.shuffle(Arrays.asList(anger));
  Collections.shuffle(Arrays.asList(joy));
  Collections.shuffle(Arrays.asList(fear));
  
  lengths[0] = anger.length;
  lengths[1] = joy.length;
  lengths[2] = fear.length;
  min_len = min(lengths);
}

void draw() {
  background(street);
  tweet1 = split(anger[i], '\t');
  tweet2 = split(joy[i], '\t');
  tweet3 = split(fear[i], '\t');
  intensity1 = float(tweet1[3])*5;
  intensity2 = float(tweet2[3])*5;
  intensity3 = float(tweet3[3])*5;
  h1 = new Human(int(random(750,700+800)), int(random(horizon,500)), 0, intensity1);
  h2 = new Human(int(random(750,700+800)), int(random(horizon,500)), 5, intensity2);
  h3 = new Human(int(random(750,700+800)), int(random(horizon,500)), 10, intensity3);
  humans.add(h1);
  humans.add(h2);
  humans.add(h3);
  
  //limit number of people in scene
  while (humans.size() > 40) {
    humans.remove(0);
  }
  for (int j = 0; j < humans.size(); j++) {
    Human h = humans.get(j);
    if (h.delay <= 0) {
      humans.remove(h);
      j--;
      //continue;
    }
    else {
      h.walk();
      h.display();
    }
  }
  i++;
  if (i >= min_len) {
    noLoop(); //kill when we run out of tweets
  }
  
  //saveFrame("/Volumes/CACHOW/output2/frame_####.png");
}
class Human{
  float cor_x;
  float cor_y;
  float speed; //depends on emotion
  int dir = 0; //moving to the left or right, randomly chosen
  float delay; //amount of time on screen, proportional to intensity of tweet
  
  Human(float cor_x, float cor_y, float speed, float delay) {
    this.cor_x = cor_x;
    this.cor_y = cor_y;
    this.speed = speed;
    this.delay = delay;
    //this.dir = int(random(0,2));
  }
  void walk(){
    if (dir == 0) {
      cor_x += speed;
    }
    else {
      cor_x -= speed;
    }
    cor_y = cor_y + random(-1,1);
    if (cor_y <= horizon) {
      cor_y = horizon;
    }
    delay -= .1;
  }
  void display(){
    //as people get closer to horizon, they appear smaller
    image(human,cor_x,cor_y, human.width*cor_y/900,human.height*cor_y/900);
  }
}
