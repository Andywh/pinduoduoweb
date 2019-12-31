#!/usr/bin/env bash
docker stop pdd-product
docker rm pdd-product

docker run --name pdd-product -p 9001:9001 -p 20891:20891 -d pdd-product-service:latest --zookeeper.address=47.101.144.28