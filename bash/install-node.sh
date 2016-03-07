#!/bin/bash

echo "Node Linux Installer by www.github.com/taaem"
echo "Need Root for installing NodeJS"
sudo sh -c 'echo "Got Root!"'

echo "Get v4.2.4 Version Number..."
{
wget --output-document=node-updater.html https://nodejs.org/dist/v4.2.4/

ARCH=$(uname -m)

if [ $ARCH = x86_64 ]
then
	grep -o '>node-v.*-linux-x64.tar.gz' node-updater.html > node-cache.txt 2>&1

	VER=$(grep -o 'node-v.*-linux-x64.tar.gz' node-cache.txt)
else
	grep -o '>node-v.*-linux-x86.tar.gz' node-updater.html > node-cache.txt 2>&1

	VER=$(grep -o 'node-v.*-linux-x86.tar.gz' node-cache.txt)
fi
rm ./node-cache.txt
rm ./node-updater.html
} &> /dev/null

echo "Done"

DIR=$( cd "$( dirname $0 )" && pwd )

echo "Downloading v4.2.4 stable Version $VER..."
{
wget https://nodejs.org/dist/v4.2.4/$VER -O $DIR/$VER
} &> /dev/null

echo "Done"

echo "Installing..."
cd /usr/local && sudo tar --strip-components 1 -xzf $DIR/$VER

rm $DIR/$VER

cd ~

echo "Finished installing!"