docker run --name postgres-spring -e POSTGRES_PASSWORD=password -d -p 5432:5432 postgres:alpine

docker ps

docker exec -it <CONTAINER ID> bin/bash

psql -U postgres

CREATE DATABASE demodb;
