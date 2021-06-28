#!/bin/bash
set -e

rm -fr *.patch

ver="$(git rev-parse HEAD)"

rootProject.name(){
  name="$2"
}
eval "$(grep '^rootProject.name =' settings.gradle.kts)"

cd "$name-Server"
onecommit() {
git reset --soft base
git commit -m"$name $1 Changes
commit $ver

$(git log --format=%B --reverse HEAD..HEAD@{1})"
}
onecommit Server
git format-patch --no-signature --zero-commit --full-index --no-stat -N -o .. HEAD^
cd "../$name-API"
onecommit API
git format-patch --no-signature --zero-commit --full-index --no-stat -N -o .. HEAD^
cd ..
