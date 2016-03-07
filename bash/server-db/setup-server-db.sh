#!/bin/bash
#
#	Author: Alexander Elias
#	Description: This script is for first time MongoDB database server setup
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

echo "Give user sudo? y/n"
read SUDO_YN

echo "Remove root ssh? y/n"
read ROOT_SSH_YN

echo "Add your development IP:"
read DEVIP

echo "Add your website server IP:"
read WEBIP


#
#	Create user account & directory
#
useradd $ID -m -d /home/$ID -c "$FULLNAME"


#
#	Update & install tools
#
apt-get update
apt-get install -y git
apt-get install -y iptables-persistent



#
#	Create firewall block all ips except the exceptions
#
iptables -N MongoDB
iptables -I INPUT -s 0/0 -p tcp --dport 27017 -j MongoDB
iptables -I INPUT -s 0/0 -p tcp --dport 28017 -j MongoDB
iptables -I MongoDB -s 127.0.0.1 -j ACCEPT
iptables -I MongoDB -s $DEVIP -j ACCEPT
iptables -I MongoDB -s $WEBIP -j ACCEPT
iptables -A MongoDB -s 0/0 -j DROP

iptables-save > /etc/iptables/rules.v4


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
