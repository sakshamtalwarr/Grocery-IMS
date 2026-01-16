# Use Tomcat 9 (same as your local setup)
FROM tomcat:9.0-jdk17

# Remove default Tomcat apps to keep it clean
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR file into Tomcat's webapps folder
# We rename it to ROOT.war so your app loads at the main URL (/)
# If you prefer the old link (/InventoryManagementSystem), change ROOT.war to InventoryManagementSystem.war
COPY InventoryManagementSystem.war /usr/local/tomcat/webapps/ROOT.war

# Expose the standard Tomcat port
EXPOSE 8080

# Start the server
CMD ["catalina.sh", "run"]