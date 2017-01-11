# Android-Retrofit-REST-Demo (work in progress)

In this demo project I created an app to access data on a WampServer. The server db contains one table "users" currently. 
I wrote the server-side APIs in php. The app uses the Retrofit REST client to do GET, POST (and eventuqally PUT, DELETE) actions on the table, and post user-entries with pictures (optional). 

# Project Goal
This demo project is a foundation for a future app I plan to gradually build. The app will enable users to share their expensis.

## App Functionality Description
```
1. The app is entended for use in Android devices only. 
2. App users will register in the app using gmail only. This will ensure that each user is unique. Pnone numbers and other emails won't be used as users can have more than one.
3. A user can create an event to share expenses. 
  a. An event will state the issuer as the owner and will contain event name, description, and a list of participant members. 
  b. Events should have a date stamp and be searchable by date, name or members.
4. To add participants, the user will be able to select from a list containg his mobile contacts who should also be registered members in the app. 
5. After selecting memebers for the event, the user will then be able to set requests with the amount of money each member has lent or borrowed. The user should be able to add/delete/edit requests. 
6. Once the event is created, the other members will be notefied and the event will show up in their apps. 
7. Each user can either accept/reject a request notification or delete his participant in the event. The status of each request will be registered and visible on both the owner's and the member's events.  
  


```


The following will be implemented:

```
1. Verify their gmail using Facebook Account Kit.
2. Use Google Cloud Messaging (GCM) for push nitifications.
3. Develop the backend, that includes database tables and RESTful Web Service in PHP. 
4. Develop core logic for the app and GUI using material design guidlines.

```



