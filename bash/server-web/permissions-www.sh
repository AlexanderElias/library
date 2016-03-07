#!/bin/bash
#
#	Author: Alexander Elias
#	Description: Assigns www-data as the group ownwer of /var/www
#

#Set the owner/group of the web root (and any directories/files therein)
chown -R www-data:www-data /var/www

#To start, make it so no-one but the current user (www-data) can access the web-root content.
#We use 'go', meaning apply to 'group' and 'other'. We use '-', which means remove permissions.
#We use 'rwx' to remove read, write and execute permissions.
chmod go-rwx /var/www

#Next, allow users of the same group (and 'other') to enter the /var/www directory.
#This is not done recursively. Once again, we use 'group' and 'other' but we use '+' to allow the execute ('x') permission.
chmod go+x /var/www

#Next, change all directories and files in the web root to the same group (www-data) - just in case there are files in there currently
chgrp -R www-data /var/www

#Next, let's do another "reset" of sorts - Make it so only the user can access web content:
chmod -R go-rwx /var/www

#And finally, make it so anyone in the same group can ready/write and execute directories/files in the web root.
chmod -R g+rwx /var/www
