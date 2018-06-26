#!/bin/bash

########################################################################
# THIS SCRIPT CALCULATES THE AVERAGE LOAD ACROSS THE CORES IN THE CPU
# PLEASE SPECIFY MINIMUM AND MAXIMUM VALUES AND THEIR ERROR MESSAGES
########################################################################


MIN=0
MAX=1000000
MAXERR="CPU Utilisation is too high!"
MINERR="CPU Utilisation is too low!"

########################################################################
# MODIFY THE CODE BELOW IF YOU WANT TO USE OTHER METHODS FOR MONITORING
# MAKE THE CODE SET THE VARIABLE 'RET' ACCORDINGLY 
########################################################################

CORES=$(../lib/scripts/no_cores.sh)
RET=0
CURRENT=0
for (( i=0; i<$CORES; i++))
do
    INITIAL_LOAD[$i]=$(grep 'cpu'$i' ' /proc/stat | awk '{usage=$2+$4} END {print usage}')
    INITIAL_IDLE[$i]=$(grep 'cpu'$i' ' /proc/stat | awk '{usage=$5} END {print usage}')
done
sleep 0.75
for (( i=0; i<$CORES; i++))
do
    MEASURE_LOAD[$i]=$(expr $(grep 'cpu'$i' ' /proc/stat | awk '{usage=$2+$4} END {print usage}') - ${INITIAL_LOAD[$i]})
    MEASURE_IDLE[$i]=$(expr $(grep 'cpu'$i' ' /proc/stat | awk '{usage=$5} END {print usage}') - ${INITIAL_IDLE[$i]})
    CURRENT=$(expr $(expr ${MEASURE_LOAD[$i]} \* 1000000) / $(expr ${MEASURE_LOAD[$i]} + ${MEASURE_IDLE[$i]}))
    let 'RET += (CURRENT / CORES)'
done

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


