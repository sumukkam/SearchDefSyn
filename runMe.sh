#!/bin/bash

clear

while :
do
	echo -n "Enter a word to search: "
	read searchWord


	tput sc
	printf "[..........................] (0%%)\r"


	python searchDefinitions.py $searchWord > definition.txt
	tput rc
	tput el
	printf "[###.......................] (12.5%%)\r"

	javac parseDefinitions.java
	tput rc
	tput el
	printf "[#####.....................] (25%%)\r"

	java parseDefinitions ${PWD}/definition.txt
	tput rc
	tput el
	printf "[########..................] (37.5%%)\r"

	rm parseDefinitions.class
	tput rc
	tput el
	printf "[#############.............] (50%%)\r"


	python searchSynonyms.py $searchWord > synonyms.txt
	tput rc
	tput el
	printf "[#################.........] (62.5%%)\r"

	javac parseSynonyms.java
	tput rc
	tput el
	printf "[####################......] (75%%)\r"

	java parseSynonyms ${PWD}/definition.txt
	tput rc
	tput el
	printf "[#######################...] (87.5%%)\r"

	rm parseSynonyms.class
	tput rc
	tput el
	printf "[##########################] (100%%)\r"

	tput rc
	tput el
	

	cat definition.txt
	cat synonyms.txt
	echo
done
