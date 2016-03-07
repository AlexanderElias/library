#!/bin/bash
#
# 	Author: Alexander Elias
#	Description: This file assigns www-data as the group ownwer of /etc/nginx/sites-available and /etc/nginx/sites-enabled
#

#Set permission for nginx sites-available
chown -R $USERNAME:www-data /etc/nginx/sites-available
# chmod -R g+rwx /etc/nginx/sites-available

#Set permission for nginx sites-enabled
chown -R $USERNAME:www-data /etc/nginx/sites-enabled
# chmod -R g+rwx /etc/nginx/sites-enabled
