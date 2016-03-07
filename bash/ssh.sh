#!/bin/bash
#
#	Author: Alexander Elias
#	Description: Generates ssh without prompts

#
#	Generates key without password
#
ssh-keygen -f key -t rsa -N ''
