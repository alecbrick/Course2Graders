FROM ubuntu:latest

RUN \
    apt-get update && \
    apt-get install -y openjdk-7-jdk

RUN apt-get install -y zip
RUN apt-get install -y junit4

RUN mkdir /shared
RUN mkdir /shared/submission

RUN mkdir /grader
RUN mkdir /grader/mod1
RUN mkdir /grader/mod2
RUN mkdir /grader/mod2/document
RUN mkdir /grader/mod3/
RUN mkdir /grader/mod3/part1
RUN mkdir /grader/mod3/part2
RUN mkdir /grader/mod3/part1/textgen
RUN mkdir /grader/mod3/part2/textgen

COPY executeGrader.sh /grader/executeGrader.sh
COPY mod1/* /grader/mod1/
COPY document/* /grader/mod2/document/
COPY mod3/part1/* /grader/mod3/part1/
COPY mod3/part1/textgen/* /grader/mod3/part1/textgen/
COPY mod3/part2/textgen/* /grader/mod3/part2/textgen/
COPY grader.py /grader/grader.py

RUN chmod a+rwx -R /grader/

ENTRYPOINT ["./grader/executeGrader.sh"]
