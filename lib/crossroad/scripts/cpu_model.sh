#!/bin/bash

########################################################################
# THIS SCRIPT ACQUIRES THE MODEL OF THE CPU
########################################################################

lscpu | grep 'Model name: ' | awk '{ s = ""; for (i = 3; i <= NF; i++) s = s $i " "; print s }'