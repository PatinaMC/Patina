#!/bin/bash
set -e

rm -fr *.patch

ver="$(git rev-parse HEAD)"

rootProject.name(){
  name="$2"
}
eval "$(grep '^rootProject.name =' settings.gradle.kts)"

dirname="$(basename "$PWD")"

for d in "$name-Server" "$name-server" "$dirname-Server" "$dirname-server"; do
  if [ -d "$d" ]; then
    serverd="$d"
  fi
done
cd "$serverd"

onecommit() {
git reset --soft base
git commit -m"$name $1 Changes
commit $ver

$(git log --format=%B --reverse HEAD..HEAD@{1})"
}
onecommit Server
git format-patch --no-signature --zero-commit --full-index --no-stat -N -o .. HEAD^
cd ..
for d in "$name-API" "$name-api" "$dirname-API" "$dirname-api"; do
  if [ -d "$d" ]; then
    apid="$d"
  fi
done
cd "$apid"
onecommit API
git format-patch --no-signature --zero-commit --full-index --no-stat -N -o .. HEAD^
cd ..
