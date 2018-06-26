#!/bin/bash

########################################################################
# THIS SCRIPT RUNS THE DATA COLLECTION SCRIPTS
# MAKE SURE ALL ARE LINKED TO $PATH
########################################################################

./cpu_model.sh
./kernel_name.sh
./ram_total.sh
./swap_total.sh
./no_cores.sh