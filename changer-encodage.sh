#!/bin/bash

mkdir './corpus/recettes-utf-8'

for file in ./corpus/recettes/*.txt ; do
	echo $file
	fileName=$(echo $file | egrep -o '[A-Z].*[^\.txt]')
	echo $fileName
	iconv -f iso-8859-1 -t utf-8 $file >> ./projet_java/corpus/recettes-utf-8/$fileName-utf-8.txt
done