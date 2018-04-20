#!/bin/bash

########################################################################
# THIS SCRIPT RUNS THE SENSOR SCRIPTS
# MAKE SURE ALL ARE LINKED TO $PATH
########################################################################

../lib/scripts/cpu_frequency.sh
../lib/scripts/cpu_load.sh
../lib/scripts/cpu_temp_avg.sh
../lib/scripts/ram_used.sh
../lib/scripts/swap_used.sh