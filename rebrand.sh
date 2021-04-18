#!/bin/sh
set -e
name="Patina"
lowcasename="patina"
groupname="foss.patina"
url="https://github.com/foss-mc/Patina"
sed -i "s|com.ayff|$groupname|g;s|AYFF|$name|g;s|ayff|$lowcasename|g;s|https://github.com/foss-mc/A-Yatopia-Framwork-Fork|$url|g;" patches/server/0001-Modify-POM.patch patches/api/0001-Modify-POM.patch patches/server/0002-hardcode-BukkitVersion-1.16.5-R0.1-SNAPSHOT.patch settings.gradle.kts build.gradle.kts
> README.md
