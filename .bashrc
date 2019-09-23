# .bashrc

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi

# Uncomment the following line if you don't like systemctl's auto-paging feature:
# export SYSTEMD_PAGER=

# User specific aliases and functions
export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.212.b04-0.el7_6.x86_64
export M2_HOME=/home/train/Downloads/apache-maven-3.6.1
export PATH=$PATH:$JAVA_HOME/bin:$M2_HOME/bin
export HTTP_PROXY=http://10.19.16.165:8080
export HTTPS_PROXY=http://10.19.16.165:8080
export http_proxy=http://10.19.16.165:8080
export https_proxy=http://10.19.16.165:8080
