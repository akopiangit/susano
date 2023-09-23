FROM openjdk:8-alpine
COPY ./target/susano.jar susano.jar
EXPOSE 8080
ENTRYPOINT \
	echo "Start 'susano' application" && \
	java \
	 -Xms4g \
	 -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 susano.jar
