# Builder
FROM maven:3.5.3-jdk-8 as builder
MAINTAINER ASC-LAB

ENV \
  MULTICHAIN_RPC_HOST=${MULTICHAIN_RPC_HOST:-multichain-master}

RUN git clone https://github.com/SimplyUb/MultiChainJavaAPI.git
RUN mvn clean install -f MultiChainJavaAPI/pom.xml

COPY . /opt/app
RUN \
  sed -i 's|^spring.profiles.active=.*$|spring.profiles.active=prd|' /opt/app/src/main/resources/application.properties && \
  sed -i 's|^multichain.host=.*$|multichain.host=multichain-master|' /opt/app/src/main/resources/application.properties
RUN mvn clean package -Dmaven.test.skip=true -f /opt/app/pom.xml


# Runner
FROM openjdk:8-jdk-alpine
MAINTAINER ASC-LAB

COPY --from=builder /opt/app/target/multichain-server-1.0.0-SNAPSHOT.jar ./multichain-server-1.0.0-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar","./multichain-server-1.0.0-SNAPSHOT.jar"]

