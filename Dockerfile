FROM ubuntu:latest
LABEL authors="jbain"

ENTRYPOINT ["top", "-b"]