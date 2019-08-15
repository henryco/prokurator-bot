#!/bin/bash

docker rm -f prokurator_bot

docker run \
  -itd -p 8888:8888 \
  --name=prokurator_bot \
  --memory="800m"
  --restart=on-failure:10 \
  tindersamurai/prokurator_bot
  
docker ps -a | grep prokurator_bot

docker logs prokurator_bot
