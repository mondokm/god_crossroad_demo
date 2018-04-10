#!/bin/bash

########################################################################
# THIS SCRIPT ACQUIRES THE USED SWAP IN THE SYSTEM
# PLEASE SPECIFY MINIMUM AND MAXIMUM VALUES AND THEIR ERROR MESSAGES
########################################################################


MIN=0
MAX=3000000    #3GB
MAXERR="Total used SWAP amount is too high!"
MINERR="Total used SWAp amount is too low!"

########################################################################
# MODIFY THE CODE BELOW IF YOU WANT TO USE OTHER METHODS FOR MONITORING
# MAKE THE CODE SET THE VARIABLE 'RET' ACCORDINGLY 
########################################################################

RET=$(free | grep Swap | awk '{print $3}')

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