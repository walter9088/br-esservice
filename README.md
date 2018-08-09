# es


**安装：**

git clone https://github.com/walter9088/es.git

进入es，修EsClientFactory类的es相关配置为自己本地配置

执行：mvn clean install -Dmaven.test.skip

java -jar target/es-service-1.0.0.jar 启动服务

**接口1：**
key：搜索关键词
start:翻页起始位置
end:翻页结整位置
in:所属行业
http://localhost:8080/query.html??key=建筑&start=0&end=10&in=其他服务业


**接口2：**
post请求：
头部：Content-Type:application/json
http://localhost:8080/store.html
接收json 格式：
{"businessscope":"生产、加工：摄影袋、摄影脚架、闪光灯、色镜、服装、电子设备。 (依法须经批准的项目，经相关部门批准后方可开展经营活动)〓","id":"13","industry":"零售业","legalpersonname":"邓剑","name":"东莞市东城赛富图摄影器材厂","orgnumber":"null","regcapital":"","regnumber":"441900602579005","regstatus":"存续"}




**接口3:**
http://localhost:8080/cs.html
创建索引，程序启动后执行一次就可以，不需要多次执行。


