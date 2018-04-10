#!/bin/bash

########################################################################
# THIS SCRIPT RUNS THE SENSOR SCRIPTS
# MAKE SURE ALL ARE LINKED TO $PATH
########################################################################

./cpu_frequency.sh
./cpu_load.sh
./cpu_temp_avg.sh
./ram_used.sh
./swap_used.sh