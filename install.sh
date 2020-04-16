#!/bin/bash
sudo apt update
sudo apt install -y default-jre default-jdk apache2 curl mysql-server
sudo ufw allow in "Apache Full"
#sudo mysql_secure_installation

sudo mysql -u root
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';
exit

curl -sL https://deb.nodesource.com/setup_12.x | sudo -E bash -
sudo apt-get install -y nodejs
sudo apt-get install libmysql-java
# add to ./bashrc also
echo 'export CLASSPATH=$CLASSPATH:/usr/share/java/mysql-connector-java.jar' >> ~/.bashrc
source ~/.bashrc
