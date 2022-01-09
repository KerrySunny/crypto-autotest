### **项目描述**

应用crypto-autotest按任务1和任务2分两个模块进行设计，task1实现任务1UI自动化测试，task2实现任务2接口自动化测试

### **技术范围**

项目环境搭建：JDK1.8 / maven3.3.9 /chromedriver 【注：浏览器驱动见crypto-autotest根路径】

实现工具： testng6.11 / httpclient4.5.5 / selenium 3.14

项目地址：

### 测试用例设计描述

1.task1测试入口：com.crypto.task1.cases.FirstSubTradeTest/com.crypto.task1.cases.LastSubTradeTest
11 用例设计描述： task1基于 testng+selenium，实现简单的页面ui自动化，分case层、parameter层以及工具类
1.2 用例范围：包含CRO交易模块中第一个产品和最后一个产品的trade页面进入【注：面试题中的XTZ产品设计期间未显示，因此随机选取其他产品进行测试】

2.task2测试入口：com.crypto.task2.cases.Forcast4NineTest
2.1 用例设计描述： task2基于 testng +httpclient + velocity，实现接口自动化，分case层、 parameter层以及工具类，并输出接口测试报告
2.2 用例范围：fnd的天气预测接口中，多语言的对应的明天和后天的湿度数据均做了返回，见测试报告，可下载通过浏览器查看【报告目录：task2/report.html】

### 实现时长

8小时