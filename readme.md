# News Searcher

Test web-app with full text search by Articles using Apache Cassandra and Apache Solr.

AngularJS as a front-end MVW. Spring Web MVC and Apache Velocity at back-end.

Spring data cassandra/solr (templates) a as persistence providers.

using Maven and Docker 
#3 steps to setup and run

* To install the default instance of Apache Cassandra we may pull docker image and launch it exposing name *casdb* and ports

> docker pull cassandra:2.2.7

> docker run --name casdb -p 127.0.0.1:9042:9042 -p 127.0.0.1:9160:9160 -d cassandra:2.2.7

* To install the default instance of Apache Solr we may pull base docker image, build an image with *articles* core inside, and then launch it linking to Cassandra and exposing name *solrsearch* and port (step 2 have to be executed from the Dockerfile location)

> docker pull solr

> docker build -t "solrnews" .

> docker run -p 8983:8983 --name solrsearch --link casdb:cassandra -d solrnews

* run News Searcher application

> mvn clean install jetty:run

#useful bash

** cassandra **

* check cassandra connection ready

>> telnet 127.0.0.1 9160

* launch cassandra cluster (single docker vm)

>> docker run --name casdb1 -d -p 127.0.0.1:9042:9042 -p 127.0.0.1:9160:9160 cassandra:2.2.7

>> docker run --name casdb2 -d -e CASSANDRA_SEEDS="$(docker inspect --format='{{ .NetworkSettings.IPAddress }}' casdb)" cassandra:2.2.7

>> docker run --name casdb3 -d -e CASSANDRA_SEEDS="$(docker inspect --format='{{ .NetworkSettings.IPAddress }}' casdb)" cassandra:2.2.7

* stop cassandra by name

>> docker ps -a -q --filter="name=casdb"

** solr **
* launching an instance with creating a core first (command below might take some time (~20 sec.), check <http://localhost:8983/solr/> do have *articles* core available before proceeding.)

>> docker run -p 8983:8983 --name solrsearch --link casdb:cassandra -d solr solr-create -c articles

* building Solr image with configured **articles** core (execute from the Dockerfile location)

>> docker build -t "solrnews" .

* print solr file structure

>> docker exec -i -t --privileged solrsearch ls -R

* unzip solr core on to docker container (execute from the articles.zip location)

>> unzip -q articles.zip && docker cp articles solrsearch:/opt/solr/server/solr/ && rm -r articles

* copy archive and unzip it on docker container (execute from the articles.zip location)

>> docker cp articles.zip solrsearch:/opt/solr/server/solr/

>> docker exec -i solrsearch unzip -q /opt/solr/server/solr/articles.zip -d /opt/solr/server/solr/

* check *solr* web UI

>> at <http://localhost:8983/solr/>

* force stop all running docker images (make shure you dont have **\** inside command)

>> docker rm -f \`docker ps --no-trunc -aq\`

#references:
Cassandra docker repository <https://hub.docker.com/_/cassandra/>

Solr docker repository <https://hub.docker.com/_/solr/>

Solr cloud wiki <https://cwiki.apache.org/confluence/display/solr/Getting+Started+with+SolrCloud>

Solr cluster <https://github.com/docker-solr/docker-solr/blob/master/docs/docker-networking.md>

Source project repository <https://github.com/dchqinc/dchq-docker-java-solr-mongo-cassandra-example>
