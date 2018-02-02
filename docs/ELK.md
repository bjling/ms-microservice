https://www.elastic.co/downloads

https://www.elastic.co/downloads/logstash
~~~
Run bin/logstash -f logstash.conf

Run bin/elasticsearch (or bin\elasticsearch.bat on Windows)

Open config/kibana.yml in an editor
Set elasticsearch.url to point at your Elasticsearch instance

Run bin/kibana (or bin\kibana.bat on Windows)

Point your browser at http://localhost:5601
~~~