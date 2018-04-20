#!/bin/bash

########################################################################
# THIS SCRIPT RUNS THE DATA COLLECTION SCRIPTS
# MAKE SURE ALL ARE LINKED TO $PATH
########################################################################

../lib/scripts/cpu_model.sh
../lib/scripts/kernel_name.sh
../lib/scripts/ram_total.sh
../lib/scripts/swap_total.sh
../lib/scripts/no_cores.sh