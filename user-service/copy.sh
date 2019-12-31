#!/usr/bin/expect

set product_server_host [lindex $argv 0]

spawn scp target/user-dubbo-service.jar root@47.101.144.28:/home/web/user/dubbo
expect "*password:"
send "Andy007!\r"

interact