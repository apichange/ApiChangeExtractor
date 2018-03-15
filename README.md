ApiChangeExactor
===

### Step one: build the database
We use the MySQL database. First, build or select a new schema. Second, use three following files under the folder `src\main\java\resources\final` to create tables:
  * apichange.sql
  * example.sql
  * inner_example.sql

### Step two: modify the configuration.xml 
Modify configuration.xml under the folder `src\main\java\resources`. Change the third element `environment` in the element `environments` to your own configuration.
 

### Step three: package by maven
Use the command `mvn package -Dmaven.test.skip=true` to package the project.

### Step four: run the program
Then we can run it by using `ApiChangeExtractor-0.7.0-jar-with-dependencies.jar` in the `target` folder:
```cmd
    java –jar ApiChangeExtractor-0.7.0-jar-with-dependencies.jar res
```
PS: `ApiChangeExtractor-0.7.0-jar-with-dependencies.jar` is the jar that we get by step three, and `res` is the file that contains all projects’ absolute paths we want to analysis, one line one project.
