
### Give environment variable names from command line in springboot maven application
- mvn spring-boot:run -Dspring-boot.run.arguments=--ARG_NAME1=ARG_VALUE1,--ARG_NAME1=ARG_VALUE1,





### AWS Deployment Some helpful steps 

Go to https://www.elastic.co/ and steps are given there
How to run logstack
1) https://www.elastic.co/downloads/logstash
2) Download and unzip Logstash
3)  Prepare a logstash.conf config file
    Created file simple-logstash.conf
    //////////////////
    input {
    file {
    type => "users-ws-log"
    path => "C:/Users/amsidhlokhande/PhotoApp/logs/users-ws.log"
    }

file {
type => "albums-ws-log"
path => "C:/Users/amsidhlokhande/PhotoApp/logs/albums-ws.log"
}

file {
type => "discoveryservice-ws-log"
path => "C:/Users/amsidhlokhande/PhotoApp/logs/discoveryservice-ws.log"
}

file {
type => "cloud-config-ws-log"
path => "C:/Users/amsidhlokhande/PhotoApp/logs/cloud-config-ws.log"
}

file {
type => "zuul-ws-log"
path => "C:/Users/amsidhlokhande/PhotoApp/logs/zuul-ws.log"
}

file {
type => "account-ws-log"
path => "C:/Users/amsidhlokhande/PhotoApp/logs/account-ws.log"
}

}

output {
if [type] == "users-ws-log" {
elasticsearch {
hosts => ["localhost:9200"]
index => "users-ws-%{+YYYY.MM.dd}"
}
}else if [type] == "albums-ws-log" {
elasticsearch {
hosts => ["localhost:9200"]
index => "albums-ws-%{+YYYY.MM.dd}"
}
}else if [type] == "discoveryservice-ws-log" {
elasticsearch {
hosts => ["localhost:9200"]
index => "discoveryservice-ws-%{+YYYY.MM.dd}"
}
}else if [type] == "cloud-config-ws-log" {
elasticsearch {
hosts => ["localhost:9200"]
index => "cloud-config-ws-%{+YYYY.MM.dd}"
}
}else if [type] == "zuul-ws-log" {
elasticsearch {
hosts => ["localhost:9200"]
index => "zuul-ws-%{+YYYY.MM.dd}"
}
}else if [type] == "account-ws-log" {
elasticsearch {
hosts => ["localhost:9200"]
index => "account-ws-%{+YYYY.MM.dd}"
}
}

stdout { codec => rubydebug }
}

////////////////////

4) Run bin/logstash -f logstash.conf


How to run ElasticSearch
1) https://www.elastic.co/downloads/elasticsearch
2) Download and unzip Elasticsearch
3) Run bin/elasticsearch (or bin\elasticsearch.bat on Windows)
4) Run curl http://localhost:9200/ or Invoke-RestMethod http://localhost:9200 with PowerShell

How to run Kibana
1) https://www.elastic.co/downloads/kibana
2) Download and unzip Kibana
3) Open config/kibana.yml in an editor
   Set elasticsearch.hosts to point at your Elasticsearch instance
4) Run bin/kibana (or bin\kibana.bat on Windows)
5) Point your browser at http://localhost:5601


docker logs -f --tail 10 8adcf7699817

Docker Commands Used in this Video Course

Below is a list of Docker commands used in this video course.
Docker Commands Cheat Sheet

Here is a list of general Docker commands used in this video course:

http://appsdeveloperblog.com/docker-commands-cheat-sheet/


EC2 Linux Instance Command
sudo yum update

A) Install Docker on AWS EC2 Linux Instance
sudo yum update
sudo yum install docker
sudo service docker start
sudo usermod -a -G docker ec2-user
Relogin to your EC2 Linux Instance




B) Run RabbitMQ Docker Container
docker login --username amsidhmicroservice

docker run -d --name rabbit-app -p 15672:15672 -p 5672:5672 -p 15671:15671 -p 5671:5671 -p 4369:4369 rabbitmq:3-management
Make all port accessible within your VPC.

The rabbit Portal is available on http://ec2-13-232-167-119.ap-south-1.compute.amazonaws.com:15672/
So kindly make 15672 port is accessible from your machine



C) To run RabbitMQ and change Default user name and password:

docker run -d --name rabbit-name-management -p 15672:15672 -p 5672:5672 -p 5671:5671 -e RABBITMQ_DEFAULT_USER=user –e RABBITMQ_DEFAULT_PASS=password rabbitmq:3-management





D) Run Config ApplicationPropertyConfigurationServer Docker Container

Create docker image of config Servere

mvn package
docker build --tag=configserver --force-rm=true .
Tag the new image to docker repository using ImageId
docker tag 3bae75dea9a7 amsidhlokhande/configserver
docker push amsidhlokhande/configserver
docker run -d -p 8012:8012 -e "spring.rabbitmq.host=" -e "spring.rabbitmq.port=5672" -e "spring.rabbitmq.username=guest" -e "spring.rabbitmq.password=guest"

spring.rabbitmq.hostname= localhost
spring.rabbitmq.port= 5672
spring.rabbitmq.username= admin
spring.rabbitmq.password= admin

To get IP Address of running container
docker ps
CONTAINER ID        IMAGE                   COMMAND                  CREATED             STATUS              PORTS                                                                                                       NAMES
dd42a0169e03        rabbitmq:3-management   "docker-entrypoint.s…"   13 hours ago        Up 6 minutes        0.0.0.0:4369->4369/tcp, 0.0.0.0:5671-5672->5671-5672/tcp, 0.0.0.0:15671-15672->15671-15672/tcp, 25672/tcp   rabbit-app

docker inspect dd42a0169e03
This will display big json. Choose "IPAddress": from Networks section present in bottom.
"IPAddress": "172.17.0.2"

docker run -d -p 8012:8012 -e "spring.rabbitmq.host=172.31.9.106" -v /home/ec2-user/photoapp-logs:/api-logs amsidhlokhande/configserver

Started CloudConfigWSApplication in 16.069 seconds (JVM running for 17.251)
http://ec2-13-126-122-16.ap-south-1.compute.amazonaws.com:8012/PhotoAppAPIConfigServer/default
http://ec2-13-126-122-16.ap-south-1.compute.amazonaws.com:8012/PhotoAppAPIConfigServer/default


E) Run Eureka Docker Container
docker build --tag=eurekaserver --force-rm=true .
docker tag ffbf05fcc4ac amsidhlokhande/eurekaserver
docker push amsidhlokhande/eurekaserver
docker run -d -p 8010:8010 -e "spring.cloud.config.uri=http://172.31.4.122:8012" -v /home/ec2-user/photoapp-logs:/api-logs amsidhlokhande/eurekaserver
Eureka Portal Url is http://publicIP:8010/





F) Run Zuul API Gateway Docker Container
docker build --tag=gatewayserver --force-rm=true .
docker tag 779439166f56 amsidhlokhande/gatewayserver
docker push amsidhlokhande/gatewayserver

#docker run -d -e "spring.cloud.config.uri=http://172.31.38.45:8012" -e "spring.rabbitmq.host=172.31.38.45" -p 8011:8011 amsidhlokhande/gatewayserver
docker run -d -e "spring.cloud.config.uri=http://172.31.4.122:8012" -v /home/ec2-user/photoapp-logs:/api-logs -p 8011:8011 amsidhlokhande/gatewayserver

ZUUL API Gateway URI  http://PublicIP:8011/users-ws/actuator/info
http://ec2-13-232-77-228.ap-south-1.compute.amazonaws.com:8011/actuator/




G) Run Elasticsearch Docker Container
docker network create --driver bridge api_network
docker network ls
docker run -d -v esdata1:/usr/share/elasticsearch/data --network api_network --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.7.1


#docker run -d --name elasticsearch --network api_network -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.3.0





H) Run Kibana Docker Container

docker run -d --network api_network -p 5601:5601 kibana:7.7.1
docker run -d -e "ELASTICSEARCH_HOSTS=http://172.31.14.197:9200" -p 5601:5601 kibana:7.7.1


Portal URI http://PublicIP:5601/




I) Run Albums Microservice Docker Container
#############AlbumsService dockerfile#################
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/PhotoAppApiAlbums-1.0-SNAPSHOT.jar AlbumsService.jar
ENTRYPOINT ["java", "-jar", "AlbumsService.jar"]
#################End AlbumsService #dockerfile########################

docker build --tag=albumsservice --force-rm=true .
docker tag 7af6fa79ddc8 amsidhlokhande/albumsservice
docker push amsidhlokhande/albumsservice

#docker run -d --network host -e "spring.cloud.config.uri=http://172.31.38.45:8012" -v /home/ec2-user/photoapp-logs/:$HOME/PhotoApp/logs/ amsidhlokhande/albumsservice
docker run -d --network host -e "spring.cloud.config.uri=http://172.31.4.122:8012" -v /home/ec2-user/photoapp-logs:/api-logs amsidhlokhande/albumsservice
--Here --network host is port dynamic port enabling in docker container

J) Run Logstash for Albums Microservice Docker Container

Create three files dockerfile, logstash.conf and logstash.yml
#####################dockerfile##############
FROM logstash:7.7.1
RUN rm -f /usr/share/logstash/pipeline/logstash.conf
COPY logstash.conf /usr/share/logstash/pipeline/logstash.conf
COPY logstash.yml config/logstash.yml


Environment Variable to be set for logstash.conf
LOGFILE_TYPE  for example users-ws-log
LOGFILE_PATH  for example /api-logs/users-ws.log
ELASTIC_SEARCH_HOST_AND_PORT for example 172.31.7.205:9200
ELASTIC_SEARCH_INDEX for example users-ws
######################logstash.conf####################
input {
file {
type => "${SERVICE_NAME}-log"
path => "/api-logs/albums-ws.log"
}

    }

output {

elasticsearch {
hosts => ["172.31.7.205:9200"]
index => "albums-ws-%{+YYYY.MM.dd}"
}

stdout { codec => rubydebug }
}

Environment Variables to be set for logstahs.yml file
ELASTIC_SEARCH_HOST_URI for example http://172.31.7.205:9200
####################logstash.yml##########################

http.host: "0.0.0.0"
xpack.monitoring.elasticsearch.hosts: ["http://172.31.7.205:9200"]
######################################
Keep all files in one local folder and build the images

docker build --tag=logstash --force-rm=true .
docker tag 4a0676c36c6b amsidhlokhande/common-logstash
docker push amsidhlokhande/common-logstash

#docker run -d --name albumsservice-logstash -v /home/ec2-user/photoapp-logs:/api-logs amsidhlokhande/albumsservice-logstash

docker run -d --name albumsservice-logstash -e "LOGFILE_TYPE=albums-ws-log" -e "LOGFILE_PATH=/api-logs/albums-ws.log"  -e "ELASTIC_SEARCH_HOST_AND_PORT=172.31.14.197:9200" -e "ELASTIC_SEARCH_INDEX=albums-ws" -e "ELASTIC_SEARCH_HOST_URI=http://172.31.14.197:9200"  -v /home/ec2-user/photoapp-logs:/api-logs amsidhlokhande/common-logstash

You can also try with ELASTICSEARCH_HOSTS='["http://10.45.3.2:9220","http://10.45.3.1:9230"]'




K) Run MySQL Docker Container

#docker run –d -p 3306:3306 --name mysql-docker-container -e MYSQL_ROOT_PASSWORD=sergey -e MYSQL_DATABASE=photo_app -e MYSQL_USER=sergey -e MYSQL_PASSWORD=sergey mysql:latest
docker run -d --name mysql-container -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=photoappdb -e MYSQL_USER=admin -e MYSQL_PASSWORD=admin -v /var/lib/mysql:/var/lib/mysql mysql:latest

docker ps --filter name=mysql-container

To execute sql command on above mysql databsase
docker exec -it d6ff7f5e3046 bash
mysql -u admin -p





L) Run Users Microservice Docker Container

docker build --tag=usersmicroservice --force-rm=true .
docker tag 140a616b5d5f amsidhlokhande/usersmicroservice
docker push amsidhlokhande/usersmicroservice

docker run -d --name usersmicroservice -e "spring.cloud.config.uri=http://172.31.4.122:8012" --network host -v /home/ec2-user/photoapp-logs:/api-logs amsidhlokhande/usersmicroservice

For logstash


docker run -d --name usersservice-logstash -e "LOGFILE_TYPE=users-ws-log" -e "LOGFILE_PATH=/api-logs/users-ws.log"  -e "ELASTIC_SEARCH_HOST_AND_PORT=172.31.14.197:9200" -e "ELASTIC_SEARCH_INDEX=users-ws" -e "ELASTIC_SEARCH_HOST_URI=http://172.31.14.197:9200"  -v /home/ec2-user/photoapp-logs:/api-logs amsidhlokhande/common-logstash






M) Run Logstash for Users Microservice
Follow the instruction related to albumsservice-logstash given above
docker run -d --name usersmicroservice-logstash -v /home/ec2-user/photoapp-logs:/api-logs amsidhlokhande/usersmicroservice-logstash




===============================


***************************************************Cron job to push the log files content to S3 bucket ***************************************



Access Key ID: AKIAJSV5PCQ3YPYZWUAA
Secret Access Key: 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

	One more  using viru-anjali 
	Access Key ID: AKIA54RVKS6WUYDBQAWI
	Secret Access Key: vH4h4/OIPTR2gK0RpQrSw7HpZuYc6x0KiDllz5TS
	
	
	https://amsidh-photoapp.s3.ap-south-1.amazonaws.com/photoapp-logs/sample.txt
	Here region is  ap-south-1

Do following command in linux ec2 instance.
aws configure

Create a file named logfile-sync-with-s3.sh
echo "Starting sync at $(date)"
/usr/bin/aws s3 sync /home/ec2-user/photoapp-logs/. s3://amsidh-photoapp/photoapp-logs/albums-ws/
echo "Ending sync at $(date)"


crontab -e
*/3 * * * * sh ./logfile-sync-with-s3.sh>>/tmp/logfile-sync-with-s3.log 2>&1

**********************************************************************************************


===================================================
ELASTIC_SEARCH_HOST_AND_PORT and ELASTIC_SEARCH_HOST_URI

docker run -d --name photoapp-logstash -e "ELASTIC_SEARCH_HOST_AND_PORT=172.31.14.197:9200" amsidhlokhande/photoapp-logstash
docker run -d --name photoapp-logstash -e "ELASTIC_SEARCH_HOST_AND_PORT=172.31.14.197:9200" photoapp-logstash

docker run -d --name photoapp-logstash -e "ELASTIC_SEARCH_HOST_AND_PORT=172.31.14.197:9200"  -e "ELASTIC_SEARCH_INDEX=users-ws" -e "ELASTIC_SEARCH_HOST_URI=http://172.31.14.197:9200"  locallogstash



sudo yum -y remove logstash







docker run --name=gogs -p 10022:22 -p 10080:3000 -v /Users/vchari/var/gogs:/data gogs/gogs




A better alternative to --link is to launch both the applications into a custom docker network.
Here are the commands you can use:
docker network ls
docker network create web-application-mysql-network
docker inspect web-application-mysql-network

docker run -d -e MYSQL_ROOT_PASSWORD=root -e MYSQL_USER=admin -e MYSQL_PASSWORD=admin -e MYSQL_DATABASE=tododb --name mysql-container -p 3306:3306 --network web-application-mysql-network  mysql:latest
docker exec -it d6ff7f5e3046 bash
mysql -u admin -p

docker run -d --name taskschedulermysql -p 8080:8080 --network web-application-mysql-network -e MYSQL_HOST=mysql-container adithilokhande/taskschedulermysql:0.0.1-SNAPSHOT

docker inspect web-application-mysql-network

curl -X GET http://localhost:8080/h2-console -H 'authorization: Basic dXNlcjo3MjkzZjgxOC1jNDI3LTQ5MDItYjg4OC05NDNkNWM2ZGM3MGY=' -H 'cache-control: no-cache' -H 'postman-token: 2336e00c-329e-f900-93b5-e6356b5d78fc'





Newly Added/Updated step
mvn package -Dencrypt.key-store.location=file:///D:/spring-boot-microservices-and-spring-cloud/api-encryption-key.jks



spring.rabbitmq.host=172.17.0.2
zipkin host=172.17.0.5


docker run -d -p 8888:8888 -e "spring.rabbitmq.host=172.31.14.94" -e "spring.zipkin.base-url=http://172.31.14.94:9411" amsidhmicroservice/configserver:1.0.0


docker run -d -p 8010:8010 -e "spring.config.host.port=172.31.53.103:8888"  amsidhmicroservice/discovery-ws:1.0.0

docker run -d -p 8080:8080 -e "spring.config.host.port=172.31.53.103:8888"  amsidhmicroservice/gateway-ws:1.0.0


docker run -d -e "spring.config.host.port=100.25.29.37:8888"  amsidhmicroservice/users-ws:1.0.0
docker run -d -e "spring.config.host.port=100.25.29.37:8888"  amsidhmicroservice/albums-ws:1.0.0



RabbitMQ and Zipkin Private IP 172.31.14.94
Config Server Private IP 172.31.53.103
Discovery private ip 172.31.15.171
Gateway private IP 172.31.7.228



Setup ElasticSearch And Kibana
1) Create network
   docker network create --driver bridge elastic-kibana-network
2) Use above network name in below docker run command
   docker run -d --name elasticsearch --network elastic-kibana-network -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.13.4
3) Now run below docker command for kibana
   docker run -d --name kibana --network elastic-kibana-network -p 5601:5601 kibana:7.13.4

Now expose elasticsearch port 9200 to external world or My IP and 9300 within vpc
Similarly for Kibana expose 5601 port to external world or My IP


docker run -d -e "spring.config.host.port=172.31.53.103:8888" -e "logging.file.name=/api-logs/albums-ws.log" -v /home/ec2-user/api-logs:/api-logs --network host amsidhmicroservice/albums-ws:1.0.0
docker run -d -e "spring.config.host.port=172.31.53.103:8888" -e "logging.file.name=/api-logs/account-ws.log" -v /home/ec2-user/api-logs:/api-logs --network host amsidhmicroservice/account-ws:1.0.0


docker run -d --name albums-logstash -v /home/ec2-user/api-logs:/api-logs amsidhmicroservice/albums-logstash:1.0.0











