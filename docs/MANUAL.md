# Requires
* An operating system supports Java (Linux recommended, but Windows is also fine)
* Java 8 or higher (AdoptOpenJDK recommended)

# Install (First Time)
1. Download the JAR file
2. Open the terminal on the folder that the file is downloaded on
3. Run the command `java -jar ImageExpBackend.jar`
4. Wait for the server to load
5. On the folder, open [`config.yml`](#configyml), and change some settings
6. Back to the terminal, type `reload` and press Enter
7. Enjoy

# Run
1. Open the terminal on the folder that the file is downloaded on
2. Run the command `java -jar ImageExpBackend.jar`
3. Enjoy

* If you want to stop the server, type `stop` on the terminal and press Enter

# Operate
On the terminal, Type `help` and press Enter to get the list of available commands you can use to interact with the server
![help](./picture/HelpCommand.PNG)

# config.yml
```yaml
# Settings on the server
server:
  # The port the server listens to
  port: 8080
  # The secret key for secured interactions on some crucial APIs
  secret-key: d7sTPQBxmSv8OmHdgjS5
  # The milliseconds before clearing the unverified users
  clear-unverified-period: 300000

# Settings on the database
database:
  # Is the database MySQL?
  use-mysql: false
  # Should the server create the tables on the database?
  first-load: false
  # Should a test account be added?
  # Test Email: test@gmail.com
  # Test Password: AbCd!232
  test-account: true
  # The host of the MySQL database
  host: localhost
  # The port of the MySQL database
  port: '3306'
  # The name of the database
  db-name: imageexp
  # The username to access the MySQL database
  username: root
  # The password to access the MySQL database
  password: ''

# Some optional settings
option:
  # Should the username be assigned when a user is registered
  auto-assign-name-to-new-user: false

# Settings on email
# 
# The email server settings is currently from Google Mail
# If you want to support other email servers, open the JAR file as an ZIP file (on WinRar) and edit the file called "email-host.properties"
email:
  # The username of the email account
  username: ''
  # The password of the email account
  password: ''
  # Settings on the verification email
  verification:
    # Should the verification email be sent when a user is registered
    send-on-register: false
    # The title of the verification email
    title: Verify Code For ImageExp
    # The content of the verification email
    #
    # {code} will be replaced with the actual verify code
    body:
      - 'Registered successfully. Please verify your account using this code: {code}'
```
