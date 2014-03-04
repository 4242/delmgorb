#!/bin/sh
echo -e "Publishing artifacts...\n"
cp -R ./target $HOME/artifacts
cp -R ./target/site/apidocs $HOME/javadoc

cd $HOME
git config --global user.email "travis@travis-ci.org"
git config --global user.name "travis-ci"
git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/4242/delmgorb gh-pages > /dev/null

cd gh-pages
git rm -rf ./artifacts
git rm -rf ./javadoc
cp -Rf $HOME/artifacts ./artifacts
cp -Rf $HOME/javadoc ./javadoc
git add -f ./artifacts
git add -f ./javadoc
git commit -m "Lastest artifacts and javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
git push -fq origin gh-pages > /dev/null

echo -e "Published artifacts to gh-pages.\n"