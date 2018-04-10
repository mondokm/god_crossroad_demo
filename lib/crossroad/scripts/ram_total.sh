#!/bin/bash

########################################################################
# THIS SCRIPT ACQUIRES THE TOTAL INSTALLED RAM IN THE SYSTEM
# PLEASE SPECIFY MINIMUM AND MAXIMUM VALUES AND THEIR ERROR MESSAGES
########################################################################


MIN=0
MAX=16000000    #16GB
MAXERR="Total installed RAM amount is too high!"
MINERR="Total installed RAM amount is too low!"

########################################################################
# MODIFY THE CODE BELOW IF YOU WANT TO USE OTHER METHODS FOR MONITORING
# MAKE THE CODE SET THE VARIABLE 'RET' ACCORDINGLY 
########################################################################

RET=$(free | grep Mem | awk '{print $2}')

########################################################################
# DO NOT MODIFY CODE BELOW
########################################################################



if [ "$RET" -gt "$MAX" ]
then
    echo "ERROR! " $MAXERR
elif [ "$MIN" -gt "$RET" ]
then
    echo "ERROR! " $MINERR
else
    echo $RET
fi