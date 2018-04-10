#!/bin/bash


########################################################################
# THIS SCRIPT ACQUIRES THE NUMBER OF CORES IN THE CPU
########################################################################


lscpu | grep -w "CPU(s):" | awk 'NR==1{print $2}'