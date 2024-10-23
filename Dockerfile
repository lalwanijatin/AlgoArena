FROM openjdk:17-jdk-alpine
WORKDIR /app

# Install curl
RUN apk --no-cache add curl && curl --version

# Copy the Maven wrapper files into the container
COPY algo-arena-be/.mvn/ .mvn/
COPY algo-arena-be/mvnw .
COPY algo-arena-be/pom.xml .

# Run a 'dependency-only' build to cache the dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code to the image
COPY algo-arena-be/src/ src/

RUN ./mvnw clean package -DskipTests

RUN cp target/algoarena-0.0.1.jar algoarena.jar
EXPOSE 8080

# Set an environment variable with a folder path
ENV PROBLEMS_DIRECTORY=/app/problems
COPY problems/ problems/

CMD ["java","-jar","algoarena.jar"]