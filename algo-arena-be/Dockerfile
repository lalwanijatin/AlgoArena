FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copy the Maven wrapper files into the container
COPY .mvn/ .mvn/
COPY mvnw .
COPY pom.xml .

# Run a 'dependency-only' build to cache the dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code to the image
COPY src/ src/

RUN ./mvnw clean package -DskipTests

RUN cp target/algoarena-0.0.1.jar algoarena.jar
EXPOSE 8080

# Set an environment variable with a folder path
ENV PROBLEMS_DIRECTORY=/app/problems
COPY ../problems/ problems/

CMD ["java","-jar","algoarena.jar"]