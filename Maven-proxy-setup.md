Run :  mvn -version
 If version other than mvn 3.6.
 
1. Edit the /home/train/.bashrc file to export
 the maven,java and proxy settings.

2. Run sudo chown /home/train/downloads/apache-maven 3.6.. -R
 
 3. Edit the /home/train/downloads/apache-maven 3.6/conf/settings.xml to include the http proxy address and port.
 4. In eclipse Window-Preferences-Maven-Global settings and User settings --> /home/train/downloads/apache-maven 3.6/conf/settings.xml and  update settings.

  5. For maven project site goal : update pOM.xml with new version of site components.

  6. For Maven WebApp project update Tomcat version to 8.0.5 or 8.5.X to run in Tomcat.

   7.Run mavn generate archtype instead of mvn create projects.