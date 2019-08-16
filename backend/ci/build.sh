#!/usr/bin/env sh


cd DDD-Landing/DDD-Landing-Usercenter/backend
git stash
git pull --reb
sudo rm -rf  ~/.gradle/caches/4.4/fileHashes/fileHashes.lock
docker-compose restart -t 1
