# newsearcher

Test Sring web-app, searches articles using Apache Cassandra and Apache Solr.

#how to setup and run

- launching Apache Cassandra DB cluster (single docker VM)

> docker pull cassandra:2.2.7

> docker run --name casdb -p 127.0.0.1:9042:9042 -p 127.0.0.1:9160:9160 -d cassandra:2.2.7

> docker run --name casdb2 -d -e CASSANDRA_SEEDS="$(docker inspect --format='{{ .NetworkSettings.IPAddress }}' casdb)" cassandra:2.2.7

> docker run --name casdb3 -d -e CASSANDRA_SEEDS="$(docker inspect --format='{{ .NetworkSettings.IPAddress }}' casdb)" cassandra:2.2.7

- building Solr image with configured **articles** core, execute from "newsearcher" workspace

> docker build -t "solrnews" .

- launching **solrnews** with linkage to Cassandra DB

> docker run -p 8983:8983 --name solrsearch --link casdb:cassandra -d solrnews

* run Names application

> mvn clean install jetty:run -Dcassandra_url=127.0.0.1:9042/casdb -Dsolr_host=127.0.0.1 -Dsolr_port=8983

#useful bash

* using docker to pull/run cassandra image, Spring works only with v 2.x

>> docker pull cassandra:2.2.7

>> docker run --name casdb -p 127.0.0.1:9042:9042 -p 127.0.0.1:9160:9160 -d cassandra:2.2.7

* check cassandra connection ready

>> telnet 127.0.0.1 9160

* stop cassandra by name

>> docker ps -a -q --filter="name=casdb"

* using docker to pull/run solr image

>> docker pull solr

>> docker run -p 8983:8983 --name solrsearch --link casdb:cassandra solr

* print solr file structure

>> docker exec -i -t --privileged solrsearch ls -R

* modify docker solr add config

>> cd /{workspace}/cassolr/

>> docker build -t "solrnames" .

>> docker run -p 8983:8983 --name solrsearch --link casdb:cassandra solrnames

* check *solr* connection ready

>> telnet 127.0.0.1 8983

* or acess *solr* web ui

>> here <http://localhost:8983/solr/>

* force stop all running docker images (make shure you dont have **\** inside command)

>> docker rm -f \`docker ps --no-trunc -aq\`

* run Names application

>> mvn clean install jetty:run

#references:
Cassandra docker repository <https://hub.docker.com/_/cassandra/>

Solr docker repository <https://hub.docker.com/_/solr/>

Solr cloud wiki <https://cwiki.apache.org/confluence/display/solr/Getting+Started+with+SolrCloud>

Solr cluster <https://github.com/docker-solr/docker-solr/blob/master/docs/docker-networking.md>

Source project repository <https://github.com/dchqinc/dchq-docker-java-solr-mongo-cassandra-example>
