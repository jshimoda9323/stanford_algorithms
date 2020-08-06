#!/usr/bin/env bash
a=$1
b=$2
python -c "print $a*$b" > pycomp.out
java mult.java $a $b > javacomp.out
diff pycomp.out javacomp.out
if (( $? != 0 )) ; then
	echo "Fail!"
	exit 1
else
	echo "Pass"
	exit 0
fi
