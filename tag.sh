#!/bin/bash

if [ -z "$1" ]
  then
    echo "Please input version tag name."
    exit 0
fi

VERSION=$1

git tag -d $VERSION
git push origin :refs/tags/$VERSION

git push origin HEAD
git tag $VERSION
git push origin --tags