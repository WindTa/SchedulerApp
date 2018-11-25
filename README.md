# SchedulerApp
App to make schedules and collaborate with other users

# File Organization

- **src/main**
  - **java** (general purpose java programs)
    - Main.java: launches program
    - ConnectionManager.java: connects program to database
    - Validate.java: methods to validate user info fields
    - User.java: contains logged in user info
    - **controllers** (to aid fxml programs)
      - SignUpController.java
      - SignInController.java
      - HomeController.java
      - EditInfoController.java
      - MakeAppController.java
      - CancelAppController.java
      - EditAppController.java
      - EditColorController
  - **resources** (non java programs)
    - **view** (fxml programs to format scenes)
      - SignUp.fxml
      - SignIn.fxml
      - Home.fxml
      - EditInfo.fxml
      - MakeApp.fxml
      - CancelApp.fxml
      - EditApp.fxml
      - EditColor.fxml
    - **css** (custom themes)
      - lunas.css
    - **lib** (jar files)
      - mysql-connector-java-8.0.13.jar

# To do list

- Create appointments
- Import/export appointments
- Create calendar