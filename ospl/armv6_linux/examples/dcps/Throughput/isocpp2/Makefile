# -*-Makefile-*-
#----------------------------------------------------------------------------
#       Make Workspace
#
# $Id$
#
# This file was generated by MPC.  Any changes made directly to
# this file will be lost the next time it is generated.
#
# MPC Command:
# /home/pi/opensplice/bin/mwc.pl -features ospl_os_host_linux=1 -features ospl_os_linux=1 -features ospl_64_bit=0 -value_template lib_modifier= -include /bin/MakeProjectCreator/config -include /bin/MakeProjectCreator/config/TAO -features armv6l_linux=1 -features ospl_arch_armv6l=1 -type make -features isocpp2_cxx11=1 HDE/armv6l.linux/examples/examples_simple.mwc
#
#----------------------------------------------------------------------------


all: ISO_Cxx_V2_Throughput_Types ISO_Cxx_V2_Throughput_Impl ISO_Cxx_V2_Throughput_Pub ISO_Cxx_V2_Throughput_Sub

clean depend generated realclean check-syntax $(CUSTOM_TARGETS):
	@$(MAKE) -f Makefile.ISO_Cxx_V2_Throughput_Types $(@)
	@$(MAKE) -f Makefile.ISO_Cxx_V2_Throughput_Impl $(@)
	@$(MAKE) -f Makefile.ISO_Cxx_V2_Throughput_Pub $(@)
	@$(MAKE) -f Makefile.ISO_Cxx_V2_Throughput_Sub $(@)

.PHONY: ISO_Cxx_V2_Throughput_Types
ISO_Cxx_V2_Throughput_Types:
	@$(MAKE) -f Makefile.ISO_Cxx_V2_Throughput_Types all

.PHONY: ISO_Cxx_V2_Throughput_Impl
ISO_Cxx_V2_Throughput_Impl: ISO_Cxx_V2_Throughput_Types
	@$(MAKE) -f Makefile.ISO_Cxx_V2_Throughput_Impl all

.PHONY: ISO_Cxx_V2_Throughput_Pub
ISO_Cxx_V2_Throughput_Pub: ISO_Cxx_V2_Throughput_Impl
	@$(MAKE) -f Makefile.ISO_Cxx_V2_Throughput_Pub all

.PHONY: ISO_Cxx_V2_Throughput_Sub
ISO_Cxx_V2_Throughput_Sub: ISO_Cxx_V2_Throughput_Impl
	@$(MAKE) -f Makefile.ISO_Cxx_V2_Throughput_Sub all

project_name_list:
	@echo ISO_Cxx_V2_Throughput_Impl
	@echo ISO_Cxx_V2_Throughput_Pub
	@echo ISO_Cxx_V2_Throughput_Sub
	@echo ISO_Cxx_V2_Throughput_Types
