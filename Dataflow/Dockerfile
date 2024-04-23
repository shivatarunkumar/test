FROM debian:latest

# Update package index and install necessary packages
RUN apt-get update && \
    apt-get install -y \
    openjdk-11-jdk \
    git \
    bash \
    sudo \
    maven \
    krb5-user \
    ca-certificates

# Set JAVA_HOME environment variable
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64

# Your additional configurations or commands here

# Example CMD to start a bash shell
CMD ["bash"]
