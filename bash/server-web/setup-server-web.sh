#!/bin/bash
#
# 	Author: Alexander Elias
# 	Description: This script is for first time server setup
#


#
#	Interface
#
echo "User's fulll name:"
read FULLNAME

echo "User’s ID:"
read ID

echo "User’s password:"
read PASSWORD

# echo "User's SSH passphrase:"
# read PASSPHRASE

echo "Give user sudo? y/n"
read SUDO_YN

echo "Remove root ssh? y/n"
read ROOT_SSH_YN

echo "Start PM2 deamon? y/n"
read PM2DEAMON_YN


#
#	Create user account & directory
#
echo "$PASSWORD
$PASSWORD
$FULLNAME
None
None
None
None
Y" | adduser $ID


#
#	Create a public SSH key
su $ID
echo "key" | ssh-keygen
ssh-agent /bin/bash
ssh-add ~/.ssh/key
#
#	Exit new user
exit


#
#	Update & install packages
#
apt-get update
apt-get install -y git
apt-get install -y nginx
apt-get install -y iptables-persistent
apt-get install -y build-essential
apt-get upgrade


#
#	Setup nginx
#
rm /etc/nginx/sites-available/default
rm /etc/nginx/sites-enabled/default
service nginx restart


#
#	Create www directory
#
mkdir /var/www
#
#	Add user to www-data group
usermod -a -G www-data $ID


#
#	Set user permission
#
. permissions-www.sh
. permissions-nginx.sh


#
#	Create firewall block all ports except 80,7900,7901
#
#
rm /etc/iptables/rules.v4
rm /etc/iptables/rules.v6
cp iptables/rules.v4 /etc/iptables/rules.v4
cp iptables/rules.v6 /etc/iptables/rules.v6
#	test syntax v4
iptables-restore -t < /etc/iptables/rules.v4
#	test syntax v6
ip6tables-restore -t < /etc/iptables/rules.v6
iptables-save > /etc/iptables/rules.v4
service iptables-persistent reload


#
#	Install node.js
#
. install-node.sh


#
#	Install npm packages
#
npm install pm2 -g
npm update -g



#
#	Setup PM2 deamon
#
if [ $PM2DEAMON_YN = "y" ]; then
	pm2 startup ubuntu
	echo "Past command:"
	read GENCMD
	$GENCMD
fi


#
#	Create user password
#
echo "$PASSWORD
$PASSWORD" | passwd $ID


#
#	Give user sudo
#
if [ $SUDO_YN = "y" ]; then
	gpasswd -a $ID sudo
fi


#
#	Remove root ssh access & restart ssh
#
if [ $ROOT_SSH_YN = "y" ]; then
	sed -i -e 's/PermitRootLogin\ yes/PermitRootLogin\ no/g' /etc/ssh/sshd_config
	service ssh restart
fi

echo 'Rebooting'
reboot
