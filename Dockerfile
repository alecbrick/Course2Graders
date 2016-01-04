FROM java:openjdk-8u66-jdk

RUN apt-get update
RUN apt-get install -y zip
RUN apt-get install -y junit4
RUN apt-get install -y python
RUN apt-get install -y p7zip-full

RUN mkdir /shared
RUN mkdir /shared/submission

RUN mkdir /grader
RUN mkdir /grader/mod1
RUN mkdir /grader/mod1/part1
RUN mkdir /grader/mod1/part2
RUN mkdir /grader/mod1/part1/document
RUN mkdir /grader/mod1/part2/document
RUN mkdir /grader/mod2
RUN mkdir /grader/mod2/part1
RUN mkdir /grader/mod2/part2
RUN mkdir /grader/mod2/part1/document
RUN mkdir /grader/mod2/part2/document
RUN mkdir /grader/mod3/
RUN mkdir /grader/mod3/part1
RUN mkdir /grader/mod3/part2
RUN mkdir /grader/mod3/part1/textgen
RUN mkdir /grader/mod3/part2/textgen
RUN mkdir /grader/mod4
RUN mkdir /grader/mod4/part1
RUN mkdir /grader/mod4/part2
RUN mkdir /grader/mod4/part1/spelling
RUN mkdir /grader/mod4/part2/spelling
RUN mkdir /grader/mod5
RUN mkdir /grader/mod5/part1
RUN mkdir /grader/mod5/part2
RUN mkdir /grader/mod5/part3
RUN mkdir /grader/mod5/part1/spelling
RUN mkdir /grader/mod5/part2/spelling
RUN mkdir /grader/mod5/part3/spelling
RUN mkdir /grader/mod5/part3/data

COPY executeGrader.sh /grader/executeGrader.sh
COPY mod1/* /grader/mod1/
COPY mod1/part1/* /grader/mod1/part1/
COPY mod1/part2/* /grader/mod1/part2/
COPY mod1/part1/document/* /grader/mod1/part1/document/
COPY mod1/part2/document/* /grader/mod1/part2/document/
COPY mod2/part1/* /grader/mod2/part1/
COPY mod2/part2/* /grader/mod2/part2/
COPY mod2/part1/document/* /grader/mod2/part1/document/
COPY mod2/part2/document/* /grader/mod2/part2/document/
COPY mod3/part1/* /grader/mod3/part1/
COPY mod3/part1/textgen/* /grader/mod3/part1/textgen/
COPY mod3/part2/textgen/* /grader/mod3/part2/textgen/
COPY mod4/part1/spelling/* /grader/mod4/part1/spelling/
COPY mod4/part2/spelling/* /grader/mod4/part2/spelling/
COPY mod5/part1/* /grader/mod5/part1/
COPY mod5/part2/* /grader/mod5/part2/
COPY mod5/part1/spelling/* /grader/mod5/part1/spelling/
COPY mod5/part2/spelling/* /grader/mod5/part2/spelling/
COPY mod5/part3/spelling/* /grader/mod5/part3/spelling/
COPY mod5/part3/data/* /grader/mod5/part3/data/
COPY compile_error.py /grader/compile_error.py

RUN chmod a+rwx -R /grader/

ENTRYPOINT ["./grader/executeGrader.sh"]
