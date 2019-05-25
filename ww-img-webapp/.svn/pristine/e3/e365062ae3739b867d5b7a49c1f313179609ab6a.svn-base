#!/bin/sh

if curl -s -I --connect-timeout 5 --max-time 10 http://img.zhuangdianbi.com/sys/check  | grep -q '200 OK'; then
        echo ' ok'
else
        date
        echo ' to restart'
        source /etc/profile
        cd /opt/apps/imgapp/
        ./restart.sh
fi