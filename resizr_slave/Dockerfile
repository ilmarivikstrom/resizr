FROM java:8
LABEL maintainer="viktoriya.kutsarova@gmail.com"
VOLUME /tmp
EXPOSE 8090
ADD build/libs/resizr_slave-0.0.1-SNAPSHOT.jar resizr_slave-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","resizr_slave-0.0.1-SNAPSHOT.jar"]