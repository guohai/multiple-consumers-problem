#!/bin/bash
#for test mutiple consumers problem

MY_HOME=$(echo `pwd` | sed 's/\/bin//')
PIDFILE=$MY_HOME/run.pid

case "$1" in

  test)
    for ((i = 0; i < $2; i++))
        do
            echo "test program goes $i $$"
            nohup java org.xkit.labs.consumer.Main 2>/dev/null 1>runlog$i.txt &
            echo $! > $PIDFILE
            sleep 70
            kill -9 `cat $PIDFILE`
            rm -rf $PIDFILE
        done
    ;;

 start)
    nohup java org.xkit.labs.consumer.Main 2>/dev/null 1>runlog.txt &
    echo $! > $PIDFILE
    ;;

  stop)
    kill -9 `cat $PIDFILE`
    rm -rf $PIDFILE
    ;;

     *)
    echo "Usage: $0 {test N|start|stop}"
    ;;

esac

exit 0
