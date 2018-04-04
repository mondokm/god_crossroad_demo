run: start_monitoring
	. ./ospl/specialrelease.com && 	cd classes && java -cp :../ospl/jar/dcpssaj5.jar:../lib/'*'::  -Djava.library.path=../ospl/lib/ priorbnode.PriorityBProgram
clean:
	rm -rf classes
compile: clean find_sources
	mkdir -p classes/
	javac -cp :lib/'*':: -d classes/ @sources.txt
find_sources:
	find src/ src-gen/ -name "*.java"  > sources.txt
start_monitoring:
	. ./ospl/specialrelease.com && java -cp :/ospl/jar/dcpssaj5.jar:lib/'*': hu.bme.mit.ftsrg.monitoring.client.Start &
