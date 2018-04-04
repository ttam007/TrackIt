REM Run TrackIt HITS
cd "C:\Users\Matt\Documents\Education\BS Computer Science\CMSC 495 Current Trends and Projects in Computer Science\Code\Dev\TrackIt\dist\"

REM Use this command line with default database options and see the login window.
REM java -jar TrackIt.jar

REM Use this command line with default database options and remember the user name only.
REM java -jar TrackIt.jar <user name>
java -jar TrackIt.jar root

REM Use this command line with default database options and skip the login window.
REM java -jar TrackIt.jar <user name> <password>

REM Use this command line with custom database options and see the login window.
REM java -jar TrackIt.jar <database location> <port> <database name>

REM Use this command line with custom database options and remember the user name only.
REM java -jar TrackIt.jar <database location> <port> <database name> <user name>

REM Use this command line with custom database options and skip the login window.
REM java -jar TrackIt.jar <database location> <port> <database name> <user name> <password>

REM Uncomment the next line to debug.
REM pause