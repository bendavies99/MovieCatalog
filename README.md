<p align="center">
   <img 
     src="https://socialify.git.ci/bendavies99/MovieCatalog/image?description=1&font=Source%20Code%20Pro&language=1&owner=0&pattern=Floating%20Cogs&theme=Dark" 
     alt="Movie Catalog" 
     width="640" 
     height="320" />
</p>

# Getting Started

## Prerequisites
 - Maven
 - Java 8
 - PostgreSQL Server

## Installing
 - Clone the repo
 - run `./mvnw install`
 - Change in src/main/resources/application-dev.properties
   ```
   #PostgreSQL Connection settings (Change me!)
   spring.datasource.url=jdbc:postgresql://localhost:5432/md
   spring.datasource.username=mdu
   spring.datasource.password=pass
   ```
   
## Running for Dev
Run in the cli:
 `MAVEN_OPTS="-Dspring-boot.run.jvmArguments=\"-Dspring.profiles.active=dev\"" ./mvnw spring-boot:run`

Or Set the the VM Options for Maven to: `-Dspring-boot.run.jvmArguments="-Dspring.profiles.active=dev"` in your favorite IDE

## Testing
Run in the CLI: `./mvnw test`

# Building for production
Run in the CLI: `./mvnw clean install spring-boot:repackage`

# Running for Production
 - Goto your target directory
 - Run in your cli `java -jar movie-catalog-0.0.1.jar --spring.datasource.url=jdbc:postgresql://<host>:<port>/<db> --spring.datasource.username=<username> --spring.datasource.password=<password>`
 - **Make Sure to replace the < item > with your details**
