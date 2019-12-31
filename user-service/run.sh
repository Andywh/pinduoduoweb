#!/usr/bin/env bash
docker stop usesr-dubbo-service
docker rm usesr-dubbo-service

docker run --name user-service -p 9003:9003 -p 20895:20895 -d pdd-user-service:latest --zookeeper.address=47.101.144.28