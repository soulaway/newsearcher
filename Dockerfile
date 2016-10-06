# Solr with "names" core configuration
FROM solr:latest
MAINTAINER Dmitry Soloviev "dgsoloviev@gmail.com"
ADD names.zip /opt/solr/server/solr/
RUN unzip -q /opt/solr/server/solr/names.zip -d /opt/solr/server/solr/ && \
    rm /opt/solr/server/solr/names.zip
EXPOSE 8983