#!/bin/bash

docker rm -f prokurator_bot

docker run \
  -itd -p 34144:34144 \
  --name=prokurator_bot \
  --memory="800m" \
  --restart on-failure \
  tindersamurai/prokurator_bot
  
docker ps -a | grep prokurator_bot

docker logs prokurator_bot
