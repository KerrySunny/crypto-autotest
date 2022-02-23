package com.crypto.task2.report;

 import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
 import org.apache.velocity.app.Velocity;
 import org.apache.velocity.app.VelocityEngine;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.crypto.task2.report.ReporterData;

 public class GenerateReporter implements IReporter {
     //@Override
     public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
             String outputDirectory) {
         try {
             // 初始化并取得Velocity引擎
             VelocityEngine ve = new VelocityEngine();
             Properties p = new Properties();
             //虽然不懂为什么这样设置,但结果是好的.可以用了
             p.setProperty("resource.loader", "class");
             p.setProperty("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
             p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "G:/kerry/work/testWorkspace/crypto-autotest/task2/template");
             ve.init(p);
             Template t = ve.getTemplate("overview_ch.vm","UTF-8");
             VelocityContext context = new VelocityContext();

             for (ISuite suite : suites) {
                 Map<String, ISuiteResult> suiteResults = suite.getResults();
                 for (ISuiteResult suiteResult : suiteResults.values()) {
                     ReporterData data = new ReporterData();
                     ITestContext testContext = suiteResult.getTestContext();
                     // 把数据填入上下文
                     context.put("overView", data.testContext(testContext));//测试结果汇总信息
                     //ITestNGMethod[] allTests = testContext.getAllTestMethods();//所有的测试方法
                     //Collection<ITestNGMethod> excludeTests = testContext.getExcludedMethods();//未执行的测试方法
                     IResultMap passedTests = testContext.getPassedTests();//测试通过的测试方法
                     IResultMap failedTests = testContext.getFailedTests();//测试失败的测试方法
                     IResultMap skippedTests = testContext.getSkippedTests();//测试跳过的测试方法

                     context.put("pass", data.testResults(passedTests, ITestResult.SUCCESS));
                     context.put("fail", data.testResults(failedTests, ITestResult.FAILURE));
                     context.put("skip", data.testResults(skippedTests, ITestResult.FAILURE));


                 }
             }
             // 输出流
			 //<span style="white-space:pre">            </span>
			 //Writer writer = new BufferedWriter(new FileWriter("report.html"));
			 //<span style="white-space:pre">            </span>
			 OutputStream out=new FileOutputStream("report.html");
			 //<span style="white-space:pre">            </span>
			 Writer writer = new BufferedWriter(new OutputStreamWriter(out,"utf-8"));//解决乱码问题
             // 转换输出
             t.merge(context, writer);
             //System.out.println(writer.toString());
             writer.flush();
         } catch (Exception e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
     }


 }
