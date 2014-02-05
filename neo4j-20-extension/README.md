Project base for Neo4j 2.0 unmanaged extension
================================

This code extends to Neo4j 2.0 the project by @dmontag's https://github.com/dmontag/neo4j-unmanaged-extension-template . 
It offers a project base for implementing Server Unmanaged extensions. 

1. Build it: 

        mvn clean package

2. Copy target/neo4j-20-extension-0.1.jar to the plugins/ directory of your Neo4j server.

3. Configure Neo4j by adding a line to conf/neo4j-server.properties:

        org.neo4j.server.thirdparty_jaxrs_classes=org.neo4j.example.unmanagedextension=/example

4. Start Neo4j server.

5. Query it over HTTP:

        curl http://localhost:7474/example/helloworld

