#!/usr/bin/expect

set product_server_host [lindex $argv 0]

spawn scp target/pdd-product-0.0.1-SNAPSHOT.jar root@47.101.144.28:/home/web/product
expect "*password:"
send "Andy007!\r"

interact