#!/bin/bash

docker rm -f prokurator_bot

docker run \
  -itd -p 33635:8080 \
  --name=prokurator_bot \
  --memory="800m" \
  --restart on-failure \
  tindersamurai/prokurator_bot
  
docker ps -a | grep prokurator_bot

docker logs prokurator_bot
