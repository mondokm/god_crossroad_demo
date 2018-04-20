#!/bin/bash

########################################################################
# THIS SCRIPT ACQUIRES THE TOTAL AVAILABLE SWAP IN THE SYSTEM
# PLEASE SPECIFY MINIMUM AND MAXIMUM VALUES AND THEIR ERROR MESSAGES
########################################################################

MIN=0
MAX=16000000    #16GB
MAXERR="Total available SWAP amount is too high!"
MINERR="Total available SWAP amount is too low!"

########################################################################
# MODIFY THE CODE BELOW IF YOU WANT TO USE OTHER METHODS FOR MONITORING
# MAKE THE CODE SET THE VARIABLE 'RET' ACCORDINGLY 
########################################################################

RET=$(free | grep Swap | awk '{print $2}')

########################################################################
# DO NOT MODIFY CODE BELOW
########################################################################


if [ "$RET" -gt "$MAX" ]
then
    echo "ERROR! " $MAXERR $RET
elif [ "$MIN" -gt "$RET" ]
then
    echo "ERROR! " $MINERR $RET
else
    echo $RET
fi