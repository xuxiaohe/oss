#/bin/sh 

#echo "===============创建目录==============="
#`mkdir /data/testoss`
#echo "===============复制文件==============="
#`cp /usr/local/tomcat/webapps/oss.war  /data/testoss`



echo "===============切换oss目录==============="
cd /data/source/yxt_oss/yunxuetang_oss/oss/
echo "===============svn up==============="
svn up
echo "===============mvn install==============="
mvn install
echo "===============target==============="
cd target/
echo "===============cp oss-1.0.0-BUILD-SNAPSHOT.war  /data/tomcat/webapps/==============="
cp oss-1.0.0-BUILD-SNAPSHOT.war  /data/tomcat/webapps/
echo "===============cd /data/tomcat/webapps/==============="
cd /data/tomcat/webapps/
echo "===============mv==============="
rm -rf oss.war
echo "===============mv==============="
mv oss-1.0.0-BUILD-SNAPSHOT.war oss.war






#echo "===============关闭服务==============="
#sh /usr/local/tomcat/bin/shutdown.sh
#echo "===============开启服务==============="
#sh /usr/local/tomcat/bin/startup.sh