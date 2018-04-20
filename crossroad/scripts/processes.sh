#!/bin/bash

top -n 1 -b | awk 'FNR > 7 { print }'