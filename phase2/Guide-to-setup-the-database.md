# Guide to setup the database

This document contains instructions to setting up the database to run the code for Compound app.

### Setting up Postgresql database locally

We use a Postgres database for our application. Here are the instructions to download Postgres and set it up to work with Compound.

1. **Download Postgresql**
    - Go to [https://www.postgresql.org/](https://www.postgresql.org/) and click on the download button
    - Select your OS
    - Follow the OS specific instructions listed on the webpage
    - Open the Postgres app
    
2. **Create a new database server**
    - Open the Postgres app
    - Click the + button on the bottom left
    - You can edit the name if you want but make sure you're using version 14
    - Change the port to `54321` and path to end in var-14-compound
    - Click on Create Server
    - A new server with the chosen name will appear in the sidebar. Select on it and click on the Start button.

1. **Run the Setup code**
    
    Now that our database server is up and running on our local machine, we need to create users and tables used by our application. We have provided code for the same in the `compound_db.sql` file in the root directory.
    
    - In the Postgres app (with the server selected), double-click on `postgres` which will open a SQL shell in terminal
    - Copy the entire code in `compound_db.sql` and paste it into the shell and hit enter
    - All the tables should be created

1. **Run the web app**
    - Open IntelliJ and run the CompoundApplication file
    - It should now run without any errors
    - The API is available on `/api` path
    
2. **(Optional) Download pgAdmin 4**
