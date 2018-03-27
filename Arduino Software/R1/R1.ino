 /*
###########################################################################
#Pin-Setup:                                                               #
#   Motoren:      PINs      #   Endschalter:        #   Lichtschranken:   #      
#                               (LimitSwitch)           (OpticalSensor)   #        
#                                                                         #      
#   X-Achse   M2  D4, D5~   #   X-Achse   LSX A7    #   X-Achse   OSX A2  #                       
#   Y-Achse   M3  D7, D6~   #   Y-Achse   LSY A6    #   Y-Achse   OSY A1  #                           
#   Z-Achse   M1  D2, D3~   #   Z-Achse   LSZ A3    #   Z-Achse   OSZ A0  #                       
#   Magnete   M4  D8, D9~   #                                             #      
#   Bohrer    M0  D10~                                                    #        
#   Motor enable  D12                                                     #      
###########################################################################
*/
#include <arduino.h>
#include "motor.h"

#define MEN 12
#define MX 1
#define MY 2
#define MZ 3
#define BO 5

#define MXD 2
#define MXS 3

#define MYD 4
#define MYS 5

#define MZD 7
#define MZS 6

#define MSD 8
#define MSS 9

#define LSX A2
#define LSZ A1
#define OSX A3
#define LSDZ 13
#define POTI A0
#define TIEF LOW 
#define HOCH HIGH

int intmax = 2000;
int positionx = intmax;
int ox,oy,oz,lx,ly,lz;
int a=0;
int args1,args2,args3,args4;
String inputString;         // a string to hold incoming data
boolean stringComplete = false;  // whether the string is complete
int mod=1;
boolean sensory = false;
boolean fahre = false;
int saveAngle = 0;

boolean booren = false;

boolean tagx = false;
boolean tagy = false;
boolean tagz = false;
boolean tagb2 = false; //tag für einleitung zweite phase bohrung
int drlx; //globale var für drill
int drly;
int drlz;
int drlsp;

boolean goXNL = false;
boolean goYNL = false;
boolean goZNL = false;
boolean goZMX = false;

boolean ax = true;
boolean ay = true;
boolean az = true;

int xspeed = 100;
int yspeed = 150;
int zspeed = 150;


void setup() {



//Pin-Initialisierung
pinMode(MEN, OUTPUT);   //MotorENable
pinMode(MXD, OUTPUT);   //MotorXDirection
pinMode(MXS, OUTPUT);   //MotorXSpeed
pinMode(MYD, OUTPUT);   //MotorYDirection
pinMode(MYS, OUTPUT);   //MotorYSpeed
pinMode(MZD, OUTPUT);   //MotorZDirection
pinMode(MZS, OUTPUT);   //MotorZSpeed
pinMode(MSD, OUTPUT);   //MotorSpindelDirection
pinMode(MSS, OUTPUT);   //MotorSpindelSpeed
pinMode(LSX, INPUT);    //LimitSwitchX
pinMode(LSZ, INPUT);    //LimitSwitchZ
pinMode(OSX, INPUT);    //OpticalSensorX
pinMode(11,INPUT);
pinMode(13,INPUT);

// initialize serial:
  Serial.begin(9600);
// reserve 200 bytes for the inputString:
  inputString.reserve(200);
  //motor(3,1,255);
  // V.JJ.MM.DD.hh
  Serial.println("R1");
}

void loop() {

  if(false){

    
  }else{
  if (stringComplete) {
    
    if(inputString == "SNR"){  sensory = args1;}
    else if(inputString == "DRL"){  drill(args1,args2,args3,args4);}
    else if(inputString == "XNL"){  Xnull();}
    else if(inputString == "YNL"){  Ynull();}
    else if(inputString == "ZNL"){  Znull();}
    else if(inputString == "ZMX"){  Zmax();}
    else if(inputString == "XPS"){  Xpos(args1);}
    else if(inputString == "MOT"){  motornormal(args1,args2,args3);}
    else if(inputString == "ROT"){  rot(args1);}
    else if(inputString == "NMR"){  readNMR();}
    else if(inputString == "XSP"){  xspeed = args1;}
    else if(inputString == "YSP"){  yspeed = args1;}
    else if(inputString == "ZSP"){  zspeed = args1;}
    else{Serial.println("Unbekanntes Kommando");}
    stringComplete = false;
    inputString = "";
  }
  int t;
  String out;

  
  if(sensory){
      t=readout(A0);
      out+=",";
      out+=t;
      t=readout(A1);
      out+=",";
      out+=t;
      t=readout(A2);
      out+=",";
      out+=t;
      t=readout(A3);
      out+=",";
      out+=t;
      t=readout(A6);
      out+=",";
      out+=t;
      t=readout(A7);
      out+=",";
      out+=t;
      t=digitalRead(11);
      delay(5);
      t=digitalRead(11);
      out+=",";
      out+=t;
      t=digitalRead(13);
      delay(5);
      t=digitalRead(13);
      out+=",";
      out+=t;
      delay(490);
      Serial.println(out);
  }

  positionerx();
  positionerz();
  mx();
  }
}

boolean positionerx(){

if(goXNL){
if(readout(LSX)>250){ 

   motornormal(1,TIEF,0);
   goXNL = false;
   tagx = true;
   positionx = 0;
   Serial.println("Xnull");

   return true;
}else{
  if(ax){
   tagx = false;
   ax = false;
   motornormal(1,LOW,100);
   
        }
     } 
  }
}

boolean positionerz(){

if(goZNL){
if(readout(LSZ) > 550){

   motornormal(MZ,LOW,0);
   goZNL = false;
   tagz = true;
   Serial.println("Znull");
   return true;
}else{
  if(az){
   tagz = false;
   az = false;
   motornormal(MZ,LOW,zspeed);
        }
     } 
  }
}

void mx(){

  if(goZMX){

if(digitalRead(LSDZ) == 1){

   motornormal(MZ,LOW,0);
   goZMX = false;
   tagz = true;
   Serial.println("Zmax");
   return true;
}else{
  if(az){
   tagz = false;
   az = false;
   motornormal(MZ,HIGH,zspeed);
        }
     } 
  }
  
}


void readNMR(){

  String thiss = "";
  if(analogRead(A6) < 500){
    thiss += "0,";
  }else{
    thiss += "1,";
  }
  if(analogRead(A7) < 500){
    thiss += "0,";
  }else{
    thiss += "1,";
  }
  thiss+=digitalRead(11);
  Serial.println(thiss);
  
}


void rot(int eingabe){
  
    if(readout(POTI) > eingabe){
      
      motornormal(2,LOW,255);
      while (readout(POTI) > eingabe) {
    }
    }else if(readout(POTI) < eingabe){
      
      motornormal(2,HIGH,255);
       while (readout(POTI) < eingabe) {
    }
    }
    Serial.println("Rot");
    motornormal(2,HIGH,0);
}

void serialEvent() {
  while (Serial.available()) {
       
    if(mod==1){char inChar = (char)Serial.read();
    if (inChar!=':'){if(inChar!=';'){
    inputString += inChar;}}
    if (inChar==':'){mod=2;}
    if (inChar==';'){stringComplete = true;
      mod=1;}
    }    
    if(mod==2){//Serial.print("mod=2");
    args1=Serial.parseInt();
    args2=Serial.parseInt();
    args3=Serial.parseInt();
    args4=Serial.parseInt();
    mod=1;}
    }
}

int readout(int pin){
            int ro;
            ro=analogRead(pin);
            delay(5);
            ro=analogRead(pin);
          //  Serial.print(" READOUT:");
          //  Serial.print(pin);
          //  Serial.print(" ");
          //  Serial.print(ro);
            //delay(20);
            return ro;//=analogRead(ro);
}

boolean Xnull(){
ax = true;
goXNL = true;
}

void Ynull(){
  ay = true;
  goYNL = true;}

void Znull(){
  az = true;
  goZNL = true;}

void Zmax(){
  az = true;
  goZMX = true;}


void Xpos(int x){
    if(positionx == intmax){
      return;
  }
  if(positionx < x){
      motornormal(MX,HIGH,150);
  }else{
      motornormal(MX,LOW,150);
  }
      int talt = 0;
      int tneu = 0;
      int count = 0;
      int soll = abs(2*(x - positionx));
      while(count < soll){
        delay(50);
      tneu=readout(OSX);
      if (tneu-talt>200){count++;}
      if (talt-tneu>200){count++;}
      
      talt = tneu;
      }
      positionx = x;
      motornormal(MX,HIGH,0);
      Serial.println("Xpos");
      digitalWrite(MEN,LOW);delay(10);
}


void bohren(boolean boleaneo){
 
 if(boleaneo){
  
  motornormal(5,1,255); 
   
 }else{
  
  motornormal(5,1,0); 
   
 }
  
}
  

void drill(int xpoos,int ypoos,int tiefe,int sped){
Xnull();
Ynull();
Znull();
booren = true;
drlx = xpoos;
drly = ypoos;
drlz = tiefe;
drlsp = sped;

  
}


