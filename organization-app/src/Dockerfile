FROM openjdk:13.0-jdk
RUN addgroup -S runtime && adduser -S runtime -G runtime
USER runtime:runtime
COPY target/dependency/BOOT-INF/lib /app/lib
COPY target/dependency/META-INF /app/META-INF
COPY target/dependency/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.company.organization.OrganizationApplication"]
