#!/bin/bash

# indexName

echo "开始创建mapping"

# mapping

echo -e "\n成功创建mapping"

# for file in `ls /*`

do
    filename=$file
    echo "开始处理..."
    echo "原始文件名:"$filename

    # 原始文件消除乱码
    echo "开始消除乱码"
    sed -r -i -e
    # 去除\N
    sed -i 's/\\N//g' $filename

    echo "开始切割文件"
    # 切割文件到/home/deeplin/ddy/file2ES_temp/split_
    split -d -a 4 -l 10000 $filename /home/deeplin/ddy/file2ES_temp/split_

    # 读取文件夹,awk改json,然后入库
    for split_file in `ls /home/deeplin/ddy/file2ES_temp/split_*`
    do
        echo "开始处理切分文件:"$split_file
        ###

        # awk

        curl -X POST "http://1.2.69.6:9201/"$indexName"/type/_bulk" -H 'Content-type: application/json' --data-binary @$split_file"-awk" >> /dev/null

        rm $split_file
        rm $split_file"-awk"

        ###
    done

    echo "所有文件处理结束!"

done