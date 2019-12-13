#!/bin/sh
# run airhacks brokers with user: admin and pwd: admin
docker rm -f artemis && docker run -d -p 61616:61616 -p 8161:8161 --name artemis airhacks/artemis