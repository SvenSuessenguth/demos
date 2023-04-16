Prepare for Coding  
mvn clean liberty:create

Running Docker
docker network create -d bridge testing  
docker build -t "testing/testcontainers" .  
docker run -d -p 9080:9080 --name testcontainers --network=testing "testing/testcontainers":latest

http://localhost:9080/api/resources/localdatetime