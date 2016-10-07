# Solr with "articles" core configuration
FROM solr:latest
MAINTAINER Dmitry Soloviev "dgsoloviev@gmail.com"
ADD articles.zip /opt/solr/server/solr/
RUN unzip -q /opt/solr/server/solr/articles.zip -d /opt/solr/server/solr/ && \
    rm /opt/solr/server/solr/articles.zip
EXPOSE 8983