### Repeat these commands for every EC2 instance for install Docker
===================Install Docker on AWS EC2 Linux Instance================
sudo yum update
sudo yum install docker
sudo service docker start
sudo usermod -a -G docker ec2-user
Relogin to your EC2 Linux Instance
Check Docker installation by
docker version
OR
docker info
### Microservices Setup on AWS 
## 1) Install Rabbit MQ and Zipkin on single EC2 micro instance
   docker run -d --name rabbit-app -p 15672:15672 -p 5672:5672 -p 15671:15671 -p 5671:5671 -p 4369:4369 rabbitmq:3-management
   docker run -d -p 9411:9411 openzipkin/zipkin

    RabbitMQ Private IP 172.31.5.189
    ZipKin Private IP 172.31.5.189
    RabbitMQ URL http://18.218.252.211:15672/#/users
    ZipKin URL http://18.218.252.211:9411/zipkin/


## 2) Install Config Server on above RabbitMQ EC2 Instacne
   docker run -d -p 8888:8888 -e "spring.rabbitmq.host=172.31.5.189" -e "spring.zipkin.base-url=http://172.31.5.189:9411" amsidhmicroservice/configserver:1.0.0
   Config Server URL http://18.218.252.211:8888/users-ws/git
   username test
   password test
   Config Server Private IP 172.31.5.189
   Update the git repository application.properties and update the rabbitmq and zipkin private ip properties
## 3) Install Eureka Discovery
   docker run -d -p 8010:8010 -e "spring.config.host.port=172.31.5.189:8888"  amsidhmicroservice/discovery-ws:1.0.0
   Discovery URL http://18.222.167.115:8010/    
   username test
   password test

   Discovery private IP 172.31.14.210
   Update the git repository application.properties discovery/eureka host ip

## 4) Install Gateway on EC2
   docker run -d -p 8080:8080 -e "spring.config.host.port=172.31.5.189:8888"  amsidhmicroservice/gateway-ws:1.0.0
   Gateway url http://18.218.1.41:8080/actuator

   Gateway private IP 172.31.5.187

## 5) Install Elastic Search And Kibana on EC2 ===SMALL=== instance
    -  Create network
       docker network create --driver bridge elastic-kibana-network
       Check all networks
       docker network ls
    
    - Use above network name in below docker run command
       docker run -d --name elasticsearch --network elastic-kibana-network -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.13.4
       URL http://18.117.245.36:9200/
    - Now run below docker command for kibana
       docker run -d --name kibana --network elastic-kibana-network -p 5601:5601 kibana:7.13.4
       URL http://18.117.245.36:5601/
    
    Now expose elasticsearch port 9200 to external world or My IP and 9300 within vpc
    Similarly for Kibana expose 5601 port to external world or My IP

### 6) Install albums-ws and Logstash in new EC2 =====SMALL==== instance
   docker run -d -e "spring.config.host.port=172.31.5.189:8888" -e "logging.file.name=/api-logs/albums-ws.log" -v /home/ec2-user/api-logs:/api-logs --network host amsidhmicroservice/albums-ws:1.0.0

   Now create any folder in youur local machine and create these file with below containt-> Dockerfile, logstash.conf and logstash.yml
   -----------------------Dockerfile-----------------------
   FROM logstash:7.13.4
   RUN rm /usr/share/logstash/pipeline/logstash.conf
   COPY logstash.conf /usr/share/logstash/pipeline/logstash.conf
   COPY logstash.yml config/logstash.yml
   -------------------------logstash.conf for albums-ws. Use elasticsearch public ip --------------------------
---   
  input {

        file {
           type => "albums-ws-log"
           path => "/api-logs/albums-ws.log"
           start_position => "beginning"
        }

   }

   output {
   if [type] == "albums-ws-log" {
   elasticsearch {
   hosts => ["18.117.245.36:9200"]
   index => "albums-ws-%{+YYYY.MM.dd}"
   }
   }

   stdout { codec => rubydebug }
   }
---
  ------------------------logstash.yml       Use elasticsearch public ip---------------------------
---
http:
host: "0.0.0.0"
xpack:
monitoring:
elasticsearch:
hosts:
- "http://18.117.245.36:9200"
---
-------------------------------------------

    Now build the image from the folder using following command
    docker build --tag=albums-logstash --force-rm=true .
    docker tag 90ec8fe34da0 amsidhmicroservice/albums-logstash:1.0.0
    docker push amsidhmicroservice/albums-logstash:1.0.0

    Now you install albums-logstash:1.0.0 in your EC2 instance using following docker command  
    docker run -d --name albums-logstash -v /home/ec2-user/api-logs:/api-logs amsidhmicroservice/albums-logstash:1.0.0
    
    Install albums-ws:1.0.0 with below command
    docker run -d -e "spring.config.host.port=172.31.5.189:8888" -e "logging.file.name=/api-logs/account-ws.log" -v /home/ec2-user/api-logs:/api-logs --network host amsidhmicroservice/account-ws:1.0.0

    Check the elasticsearch url and see whether your albums-ws.log file appear and then confire the index pattern in kibana for albums-ws service



### ==================Sometime if apis are not working then kindly check following thigs========================
 - Check whether service is registered on Eureka Discovery
 - Do the http://18.218.252.211:8888/actuator/busrefresh on config server
 - Check the logs of each microservice and see the keys are refreshed with above busrefresh api
 - If host access related error then kindly open the respcective microservice port to open to all. And then try.
   Here I was getting host not available/not found related error in albums-logstash log for elasticsearch service. So I opened elasticsearch 9200 port to all and
   tried, and it's working.
   



    
