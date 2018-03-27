/*
*
*-----------------------------------------------------------------------------------------
*                              ! NICHT Ã„ANDERN !
*-----------------------------------------------------------------------------------------
*                      Programmiert von Christopher Freund
*
*/


void motornormal(int motornummer, int linksrechts, int drehzahl){
  digitalWrite(12, HIGH); //Motoren aktiviert 

  //Motor 3            
  if(motornummer == 3){

/*
      Serial.print("Motor: ");
      Serial.print(motornummer);
      Serial.print(", dreht mit ");
      Serial.print(drehzahl);
      Serial.print(" nach ");
      Serial.println(linksrechts);
*/
         if(linksrechts == 1){
    analogWrite(motornummer*2, 0);
    delay(500);
      digitalWrite(motornummer * 2 + 1, HIGH);
   
   }else{
    analogWrite(motornummer*2, 0);
    delay(500);
     digitalWrite(motornummer * 2 + 1, LOW);
   
   }
   
   analogWrite(motornummer*2, drehzahl);
     
  }
  //M5
  else if(motornummer == 5){

/*
      Serial.print("Motor: ");
      Serial.print(motornummer);
      Serial.print(", dreht mit ");
      Serial.println(drehzahl);
  */    
      analogWrite(motornummer*2, drehzahl);
     
  }else{
    /* Serial.print("Motor: ");
      Serial.print(motornummer);
      Serial.print(", dreht mit ");
      Serial.print(drehzahl);
      Serial.print(" nach ");
      Serial.println(linksrechts);
*/
   if(linksrechts == 1){
    analogWrite(motornummer*2 + 1, 0);
    delay(500);
      digitalWrite(motornummer * 2, HIGH);
   
   }else{
    analogWrite(motornummer*2 + 1,0);
    delay(500);
     digitalWrite(motornummer * 2, LOW);
   
   }
   
   analogWrite(motornummer*2 + 1,drehzahl);
  }
}

