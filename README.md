# OpenSearch Examples

```bash
docker-compose up -d
```

## Getting Started

Go to: <http://localhost:9200/> to access the ReST API:

```json
{
  "name" : "opensearch-node1",
  "cluster_name" : "opensearch-cluster",
  "cluster_uuid" : "VioGqUxBTnyeF0L5sHKTkA",
  "version" : {
    "distribution" : "opensearch",
    "number" : "2.14.0",
    "build_type" : "tar",
    "build_hash" : "aaa555453f4713d652b52436874e11ba258d8f03",
    "build_date" : "2024-05-09T18:51:00.973564994Z",
    "build_snapshot" : false,
    "lucene_version" : "9.10.0",
    "minimum_wire_compatibility_version" : "7.10.0",
    "minimum_index_compatibility_version" : "7.0.0"
  },
  "tagline" : "The OpenSearch Project: https://opensearch.org/"
}
```

## List Indexes (Indices)

Go to <http://localhost:9200/_cat/indices?v> or run:

```bash
curl "http://localhost:9200/_cat/indices?v"
```

```terminal
health status index                     uuid                   pri rep docs.count docs.deleted store.size pri.store.size
green  open   .opensearch-observability 0mnbn_GMQ82C5C85RP9-6Q   1   1          0            0       416b           208b
green  open   .plugins-ml-config        BauJzT4VRrmZNtRwtiJ5ig   1   1          1            0      7.8kb          3.9kb
green  open   .ql-datasources           3HmKP_6BQFe1NaqL57zQKQ   1   1          0            0       416b           208b
green  open   sample-index              Q3HdUJd2RwyvCt79_TtuFw   1   1          0            0       416b           208b
green  open   .kibana_1                 qb-IBE5iSKSLa1S13vXutg   1   1          1            0     10.3kb          5.1kb
```

Or to limit by a specific index:

```bash
http://localhost:9200/_cat/indices/sample-index?v
```

Retrieve a doc from a given index:

```bash
http://localhost:9200/sample-index/_doc/1
```


Access the OpenSearch dashboard at: <http://localhost:5601/>

- <https://opensearch.org/docs/latest/clients/java/>
- <https://opensearch.org/docs/latest/clients/java/#indexing-data>