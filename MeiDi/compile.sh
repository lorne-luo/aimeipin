#!/bin/bash

if [ -z "$1" ]
  then
    echo "Please input version tag name."
    exit 0
fi

VERSION=$1
DEPLOYDIR=/opt/webapps/meidi/WEB-INF

echo $VERSION

git tag -d $VERSION
git fetch --all
git reset --hard $VERSION
rm -rf target/*
mvn package -q
pushd .

cd target/meidi/WEB-INF/
zip -qr $VERSION.zip classes/
cp -f $VERSION.zip ../../../backups/
cp -f $VERSION.zip $DEPLOYDIR/
cp -n lib/* $DEPLOYDIR/lib/

/usr/local/tomcat8/bin/shutdown.sh
rm -rf $DEPLOYDIR/classes/
cp -r classes $DEPLOYDIR/
/usr/local/tomcat8/bin/startup.sh

popd
