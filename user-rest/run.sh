#!/usr/bin/env bash
docker stop user-rest
docker rm user-rest

docker run --name user-rest -p 9002:9002 -d user-rest:latest --zookeeper.address=47.101.144.28