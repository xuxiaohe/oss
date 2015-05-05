#/bin/sh 
#author xurui

echo "===========================切换oss目录==========================="
cd /data/source/yxt_oss/yunxuetang_oss/oss/
echo "===========================svn更新=============================="
svn up
echo "===========================mvn install工程======================"
mvn install
echo "============================切换target目录======================="
cd target/
echo "===========================负责项目到tomcat目录===================="
cp oss-1.0.0-BUILD-SNAPSHOT.war  /data/tomcat/webapps/
echo "============================关闭服务==============================="
cd /data/tomcat/bin/
./shutdown.sh
ps -ef |grep tomcat  |awk {'print $2'} | sed -e "s/^/kill -9 /g" | sh -
echo "============================切换到tomcat目录下====================="
cd /data/tomcat/webapps/
echo "============================删除原oss=========================="
rm -rf oss.war
rm -rf oss
echo "===========================修改项目名称为oss.war===================="
mv oss-1.0.0-BUILD-SNAPSHOT.war oss.war

echo "============================开启服务==============================="
cd /data/tomcat/bin/
./startup.sh