FROM tomcat:latest

ENV GRADLE_VERSION 8.6

RUN apt-get update -y && apt-get upgrade -y
RUN apt-get install unzip

RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp
RUN unzip -d /opt/gradle /tmp/gradle-*.zip
ENV GRADLE_HOME=/opt/gradle/gradle-${GRADLE_VERSION}
ENV PATH=${GRADLE_HOME}/bin:${PATH}

WORKDIR /app

COPY . .

RUN gradle clean build

RUN cp /app/build/libs/web.war /usr/local/tomcat/webapps/ROOT.war

CMD [ "catalina.sh", "run" ]
