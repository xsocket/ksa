#!/bin/bash

echo '===============更新项目版本==============='
mvn versions:set -DnewVersion=3.8.9

echo '===============移除备份文件==============='
find . -name "pom.xml.versionsBackup" -exec rm -r {} \;