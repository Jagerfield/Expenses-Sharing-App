#Expenses Sharing App (work in progress)

##Project Goal
The app will enable users to create events and share their expenses. It will provide participants 
with notifications of any changes, and enables them to communicate on the events level. 

##App Specifications - Functionality Description (Under development)
```
1. The app is intended for use in Android devices only. 
2. App users will register in the app using Gmail only. This will ensure that each user is unique. 
   Phone numbers and other emails won't be used as users can have more than one.
3. A user can create an event to share expenses. 
  a. An event will state the issuer as the owner and will contain event name, description, and a 
     list of participant members. 
  b. Events should have a date stamp and be searchable by date, name or members.
4. To add participants, the user will be able to select from a list containing his mobile contacts 
   who should also be registered members in the app. 
5. After selecting members for the event, the user will then be able to set transactions with the amount 
   of money each member has lent or borrowed. 
6. The owner of the event is the only one able to delete the event. 
7. The event will log changes made on each request. 
8. Once the event is created, the other members will be notified and the event will show up in their apps. 
7. Each user can add/delete/edit their own transactions or leave the the event. 
   status of each request will be registered and visible on both the owner's and the member's events.  
8. All Actions made on the event and its transactions should be logged in text in the event and 
   participants notified.
9. Text messages between the participants in an event is also possible to add to the event. 
```

##The following will be implemented:
```
1. Verify their Gmail using Facebook Account Kit.
2. Use Google Cloud Messaging (GCM) for push notifications.
3. Develop the backend, that includes database tables and RESTful Web Service in PHP. 
4. Develop core logic for the app and GUI using material design guidelines.
```

##Project current status
```
1. created an app to access users data on a WampServer. The server's db contains a demo table "users". 
 a. Wrote the server-side APIs in php. The app uses the Retrofit REST client to do GET, POST 
    (and eventually PUT, DELETE) 
 b. The app can currently post user-entries with pictures (optional). 
2. Completed the conceptual design for the server's db, and created tables.
3. Created relevant data models in the app.
3. Implemented  OrmLite for providing a db in the app. The purpose to store events information so they 
can be viewed offline.
4. Implemented Jackson for json parsing. 
5. Implemented permissions checking.
6. Implemented fetching mobile contacts.
```
## DB table design 

<img src="https://github.com/Jagerfield/Expenses-Sharing-App/blob/master/msc/db_design.PNG" width="400"/> &#160;




