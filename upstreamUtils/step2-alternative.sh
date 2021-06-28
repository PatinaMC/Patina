#!/bin/bash
set -e
upstream=`cd "$1" && pwd`
upstream_commmit=`cd "$upstream" && git rev-parse HEAD`
upstream_name="$(eval echo `cd "$upstream" && grep '^rootProject.name =' settings.gradle.kts | awk '{print $3;}'`)"

cd Patina-API
if cat "$upstream/patches/api"/*.patch | patch -p1 --merge --no-backup-if-mismatch; then
  :
else
  :
fi
git add .
git commit -m"$upstream_name API Changes
commit $upstream_commmit"


cd ../Patina-Server
if cat "$upstream/patches/server"/*.patch | patch -p1 --merge --no-backup-if-mismatch; then
  :
else
  :
fi
git add .
git commit -m"$upstream_name Server Changes
commit $upstream_commmit"
