Carl (ApiChangeExtractor)
===

### Step 1: setup the database
MySQL database is currently used in our implementation. Use the following three files under the folder `src\main\java\resources\final` to create tables.
  * apichange.sql
  * example.sql
  * inner_example.sql

### Step 2: configure the configuration.xml
Configure the `configuration.xml` under the folder `src\main\java\resources` by setting the third environment element in the environments element to your own configuration.

### Step 3: compile the project
Use the command `mvn package -Dmaven.test.skip=true` to compile the project.

### Step 4: run Carl
Run Carl by using `java –jar apichange.jar repo`, where apichange.jar is generated in Step 3 and repo contains the absolute paths of all the projects we want to analysis.
