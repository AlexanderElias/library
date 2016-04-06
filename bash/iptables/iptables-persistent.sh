
#
#	install
apt-get -y iptables-persistent

#	test
sudo iptables-restore -t /etc/iptables/rules.v4

#	save and reload
sudo service iptables-persistent reload
