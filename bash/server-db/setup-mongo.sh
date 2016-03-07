#!/bin/bash
#
#	Author: Alexander Elias
#	Description: This script sets up mongo db
#

echo "Enter password:"
read PASSWORD

echo "Allow external ip connections? y/n"
read EXTERNAL_YN

if [ $EXTERNAL_YN = "y" ]; then
	echo "Enter Public IP:"
	read IP
fi


#
#	Creates MongoDB Superuser
#
echo "use admin
db.createUser( { user: 'admin', pwd: '$PASSWORD', roles: [ {'role' : 'userAdminAnyDatabase', 'db' : 'admin' }, { 'role' : 'readWriteAnyDatabase', 'db' : 'admin' }, { 'role' : 'dbAdminAnyDatabase', 'db' : 'admin' }, { 'role' : 'clusterAdmin', 'db' : 'admin' } ]});
exit" | mongo



if [ $EXTERNAL_YN = "y" ]; then

	#	Allow external ip connections
	sed -i -e 's/127.0.0.1/0.0.0.0/g' /etc/mongod.conf

	#	Set public ip
	sed -i -e 's/bindIp: 127.0.0.1/bindIp: 127.0.0.1,'$IP'/g' /etc/mongod.conf
fi


#
#	Require authorization
#
sed -i -e 's/#security:/security:\n authorization:\ enabled/g' /etc/mongod.conf


#
#	Restart mongod
#
service mongod restart
