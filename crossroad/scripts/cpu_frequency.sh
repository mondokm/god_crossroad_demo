#!/bin/bash

########################################################################
# THIS SCRIPT ACQUIRES THE CURRENT FREQUENCY OF THE CPU
# PLEASE SPECIFY MINIMUM AND MAXIMUM VALUES AND THEIR ERROR MESSAGES
########################################################################


MIN=0
MAX=3000
MAXERR="CPU Frequency is too high!"
MINERR="CPU Frequency is too low!"

########################################################################
# MODIFY THE CODE BELOW IF YOU WANT TO USE OTHER METHODS FOR MONITORING
# MAKE THE CODE SET THE VARIABLE 'RET' ACCORDINGLY 
########################################################################

RET=$(lscpu | grep 'CPU MHz: ' | awk '{print int( $3 )}')

if [ "$RET" = "" ]
then
        RET=0
fi


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