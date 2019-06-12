#!/bin/bash
APP_NAME=mblog-latest.jar

usage() {
    echo "case: sh run.sh [start|stop|restart|status]"
    exit 1
}

LOG_PATH="/data/var/logs/javalog/blog/runtime/mblog"

# Xms 初始堆大小; Xmx 最大堆大小; Xmn 年轻代大小(1.4or lator);
# verbose:gc 表示输出虚拟机中GC的详细情况, 是 -XX:+PrintGC 的别名
# XX:PermSize 设置持久代(perm gen)初始值; XX:MaxPermSize 设置持久代最大值
# XX:+PrintGCDetails ; XX:+PrintGCDateStamps ;
# -XX:+PrintHeapAtGC 打印GC前后的详细堆栈信息;
# XX:+PrintTenuringDistribution 查看每次minor GC后新的存活周期的阈值;
# Xloggc ; XX:NativeMemoryTracking=summary
# XX:+HeapDumpOnOutOfMemoryError 让虚拟机出现OOM的时候自动生成dump文件;
# XX:HeapDumpPath
JAVA_OPTIONS="-Xms128m -Xmx128m -Xmn64m  -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -XX:+PrintTenuringDistribution -Xloggc:$LOG_PATH/gc.log  -XX:NativeMemoryTracking=summary -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$LOG_PATH"

echo "JAVA_OPTIONS is : $JAVA_OPTIONS"

is_exist(){
        #先判断进程是否存在，存在即先关闭
        pid=$(ps -ef|grep ${APP_NAME}.jar|grep java|grep -v grep|grep -v kill|awk '{print $2}')
        #pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}' `
        if [ -z "${pid}" ]; then
                return 1
        else
                return 0
        fi
}

start(){
        is_exist
        if [ $? -eq "0" ]; then
                echo "${APP_NAME} running. pid=${pid}"
        else
                nohup java -server $JAVA_OPTIONS -jar  ./$APP_NAME  > /dev/null 2>&1 &
                echo "${APP_NAME} started"
        fi

       pid=$(echo $!)
        echo "pid ${pid}"
        times=0
        port=0

        # 循环监听端口是否启动成功
        while [ ${times} -lt 60 ]
          do
            if  lsof -i -P | grep ${pid}
                then
                    port=$(lsof -i -P -sTCP:LISTEN|grep ${pid}|cut -d: -f2|cut -d ' ' -f1)
                    if [ -n "${port}" ] && [ "${port}" -gt 0 ]
                        then
                            times=1024
                    else
                        echo -e "\033[32m[Check port !] \033[0m"
                        times=$[ ${times}+4 ]
                        sleep 4
                    fi
            else
                    echo -e "\033[32m[Check port !] \033[0m"
                    times=$[ ${times}+4 ]
                    sleep 4
            fi
          done
}

stop(){
        is_exist
        if [ $? -eq "0" ]; then
                kill -9 $pid
                echo "${pid} stopped"
        else
                echo "${APP_NAME} not running"
        fi
}

status(){
        is_exist
        if [ $? -eq "0" ]; then
                echo "${APP_NAME} running. Pid is ${pid}"
        else
                echo "${APP_NAME} not running"
        fi
}

restart(){
	stop
	start
}

case "$1" in
	"start")
		start
		;;
	"stop")
		stop
		;;
	"status")
		status
		;;
	"restart")
		restart
		;;
	*)
    usage
    ;;
esac