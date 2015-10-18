#! /bin/bash

docker run -v /home/alec/mooc/course2/autograde:/shared/submission grader partId "$1"
