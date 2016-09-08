
if [ -z "$1" ]
  then
    echo "input tag name plz"
    exit 0
fi

git tag -d $1
git remote update
git reset --hard $1
rm target/* -rf
mvn package
pushd .
cd target/meidi/WEB-INF/
zip -qr $1.zip classes/
cp -f $1.zip ../../../backups/
cp -f $1.zip /opt/webapps/meidi/WEB-INF/
popd
cp -n target/meidi/WEB-INF/lib/* /opt/webapps/meidi/WEB-INF/lib/