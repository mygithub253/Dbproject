//
// Copyright 2015 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

// FirebaseDemo_ESP8266 is a sample that demo the different functions
// of the FirebaseArduino API.

#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

// Set these to run example.
#define FIREBASE_HOST ""
#define FIREBASE_AUTH ""
#define WIFI_SSID ""
#define WIFI_PASSWORD ""
int photoDiode=2; 
void setup() {
  Serial.begin(9600);
   pinMode(photoDiode,OUTPUT);      //gets analog value from sensor"
   digitalWrite(photoDiode,HIGH);   //sends 5 v to phtodiode"
  // connect to wifi.
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {      //begin firebase connection
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}

int n = 0;

void loop() {
  // set value
  int val=analogRead(A0); // read A0 value
  Serial.println(val); 
  Firebase.pushInt("bgvalue", val);
  // handle error
  if (Firebase.failed()) {                 // push to firebase
      Serial.print("setting /number failed:");
      Serial.println(Firebase.error());  
      return;
  }
  delay(2000);    ///adding 2sec delay


}
