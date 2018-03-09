# Identity Management Demo

## System Usage

1. Run the application by executing the main method inside IdentityManager class in the dpd.accenture.identity.identitymgmt
2. Open any browser and navigate to http://locahost:8080/swagger-ui.html
   - User will be prompted with a username and password combination.
     - Type in 'admin' as the username and 'password' as the password since this is the preloaded user when the application is started
   - User will be forwarded to the swagger interface and will be presented with the different rest endpoints.
     - The **user/create** endpoint allows a logged in user to the system, using basic authentication, to create a user in the application regardless if the logged in user is a normal user or an admin user.
     - The **user/update** endpoint allows a logged in user to update his own details.
     - The **user/delete/{username}** endpoint allows the logged in user to delete a user record if he has admin role.
     - the **user/admin/permission** endpoint allows an admin user to change another user's permission into an administrator or not.
     - The **user/list** endpoint allows a logged in user to retrieve the list of users from the application

### Scheduled Task
- The scheduled task implemented just displays all the existing users in the console every five seconds.

#### Hours Logged
- 12 Hours of worked

#### Other Notes
I was not able to test if my docker file works for my implementation since the machine I'm working on is quite antique and is unable to run any docker image. Also, I was not able to focus much on the task at hand since I was busy packing up and organizing things to ship. I hope my circumstances are considered during code review.
