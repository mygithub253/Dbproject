# Android app has java
# Below path takes you to java files
## "Dbproject/Dtrack v_almostFinal/app/src/main/java/com/example/finalapp/"

## BIO.JAVA :
- used by listreport.java to get patient vitals
- this java class comprises of getters and setters to fetch vitals from firebase

## GMAILSENDER.JAVA :
- sendmail() gets subject body sender mailid and finally sends email to doctor when A1c report is abnormal
- passwordauthentication() gets user email and password from homepage.java

## MAIN ACTIVITY.JAVA :
- takes care of user login and authentication
- sends sensor data to homepage.java

## ANALYTICS.JAVA : 
- displays 4 buttons for selecting either glucose,pressure weight or patient history bar-charts

## GLUCOSE.JAVA : 
- diaplays glucose bar chart using data from firebase

## HOMEPAGE.JAVA : 
- this page converts sensor data to glucose value using polynomial equation
----intglucose =  (0.13675)*Math.pow(intglucose,2) + (-148.15865)*Math.pow(intglucose,1)+ (40228.0908);
- it displays glucose value,asks for patient to enter blood pressure and weight value.
- it pushes these data to firebase

## JSSEPROVIDER.JAVA : 
- acts as a client for sending mail

## LISTREPORT.JAVA :
- creates a list view for all patients vitals along with date stamp

## MASS.JAVA : 
- displays weight chart from data on firebase

## PRESSURE.JAVA : 
- displays blood pressure chart from data on firebase

## REGISTER_USER : 
- obtains data like name,email id,height ,phone no., doctor name and email from the user and creates an account .
- adds a new user to the firebase and allocates space for him to enter and update his data.

## REPORT.JAVA : 
- after 2 months a report is generated with prediction,A1c , average blood pressure, average weight as buttons which got to suggestions.java

## SUGGESTIONS.JAVA :
- depending on the range of various parameters like a1c,bmi, blood pressure, suggestions are given when clicked on buttons of report.java.
