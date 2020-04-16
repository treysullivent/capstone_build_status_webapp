# capstone_build_status_webapp
A webapp powered by JS, Java, and MySQL. Built for Software Engineering II at University of Oklahoma, Spring 2020.

# How to Use
This isn't really something you 'use', but if you do want to install it and poke around, go ahead. This was only tested on AWS t2.mircro instances running Ubuntu 18.04, be aware if your setup differs from this.

Start by cloning this repo to your machine, and run the install script install.sh. You will need to manually run 'source ~/.bashrc' or restart your shell to complete the setup.

The install script will install Apache and setup the webpage, along with mySQL and a few other necessities. You will need web traffic allowed on your computer or instance befor this will work. 

To begin, we will want to start the node program that updates the json in webroot with the appropriate data. The interval at which it polls is set in the Node file. This will need to run the whole time, so we provided scripts to start and kill it silently in the background. This will silence any errors, sorry.

From here you will need to run the java program 'Driver' located in log_parser, with the file or folder of files that you would like parsed and added to the database.

