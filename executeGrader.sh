#! /bin/bash

export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64

cd /grader

MOD2_PART2_ID="mEOEF"
MOD3_PART1_ID="In7Bo"
MOD3_PART2_ID="p6Yhk"

while [ $# -gt 1 ]
  do
    key="$1"
    case $key in
      partId)
        PARTID="$2"
        shift
        ;;
      userId)
        USERID="$2"
        shift
        ;;
      filename)
        ORIGINAL_FILENAME="$2"
        shift
        ;;
    esac
  shift
done

if [ "$PARTID" == "$MOD2_PART2_ID" ]; then
  GRADER_DIRECTORY=mod2
  FILENAME="document.DocumentBenchmarking"
  unzip /shared/submission/mod2.zip -d zipfile > /dev/null
  cd zipfile
  cp * ../mod2/document/ 
  cd ../"$GRADER_DIRECTORY"
  javac -sourcepath document document/*.java
elif [ "$PARTID" == "$MOD3_PART1_ID" ]; then
  GRADER_DIRECTORY=mod3/part1
  FILENAME="mod3part1.py"
  cp /shared/submission/MyLinkedListTester.java "$GRADER_DIRECTORY"/textgen
  cd "$GRADER_DIRECTORY"
  python "$FILENAME"
  exit 0
elif [ "$PARTID" == "$MOD3_PART2_ID" ]; then
  GRADER_DIRECTORY=mod3/part2
  FILENAME="textgen.MarkovTextGeneratorGrader"
  cp /shared/submission/MarkovTextGeneratorLoL.java "$GRADER_DIRECTORY"/textgen
  cd "$GRADER_DIRECTORY"
  javac textgen/*.java
else
  echo "No PartId matched!" 1>&2
  exit 1
fi

if [ ! $? -eq 0 ]; then
  echo "{ \"fractionalScore\": 0.0, \"feedback\":\"Compile error\" }"
  exit 0
fi

if [ "$PARTID" == "$MOD2_PART2_ID" ] || [ "$PARTID" == "$MOD3_PART2_ID" ] ; then
  java "$FILENAME"
fi
