#!/bin/bash

########################################################################
# THIS SCRIPT ACQUIRES THE TEMPERATURE ACROSS THE CORES IN THE CPU
# PLEASE SPECIFY MINIMUM AND MAXIMUM VALUES AND THEIR ERROR MESSAGES
########################################################################


MIN=20
MAX=80
MAXERR="CPU Temperature is too high!"
MINERR="CPU Temperature is too low!"

########################################################################
# MODIFY THE CODE BELOW IF YOU WANT TO USE OTHER METHODS FOR MONITORING
# IN THIS FORM IT DEPENDS ON THE 'lm-sensors' PACKAGE
# MAKE THE CODE SET THE VARIABLE 'RET' ACCORDINGLY 
########################################################################

CORES=$(./no_cores.sh)
RET=0
if hash sensors 2>/dev/null; then
    CURRENT=0
    for (( i=0; i<$CORES; i++))
    do
        CURRENT=$(sensors | grep 'Core '$i | awk '{printf("%d", $3 * 10)}')
        let 'CURRENT /= CORES'
        let 'RET += CURRENT'
    done
    let 'RET /= 10'
else
    RET=$MIN
fi

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