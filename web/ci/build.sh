#!/usr/bin/env sh

set -e
cd DDD-Landing/DDD-Landing-Usercenter/web
git stash
git pull --reb
npm i
npm run build

cp -r  build/* ~/DDD-Landing/user-center
