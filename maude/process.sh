#!/bin/sh

echo "STARTING PROCESSING DATA.TXT FILE "

sort data.txt | uniq -i > body.txt

SIZE=`wc -l < body.txt | sed 's/ //g'`

cat head.txt body.txt > env-data.maude

echo " empty .\neq size = ${SIZE} .\nendm" >> env-data.maude

rm body.txt

TARGET="/Users/student/Home/JAIST/maude/env/data"
#TARGET="/Users/canhdominh/Home/JAIST/Master/master-thesis/maude/env/data"

echo "MOVING TO $TARGET ..."

mv env-data.maude $TARGET

echo "FINISHED !!!"