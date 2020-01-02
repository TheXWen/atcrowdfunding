package junit.activiti;

import com.xw.atcrowdfunding.activiti.listener.NoListener;
import com.xw.atcrowdfunding.activiti.listener.YesListener;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestActiviti {

    ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-*.xml");
    ProcessEngine processEngine = ioc.getBean(ProcessEngine.class);

    //12.测试流程监听器
    @Test
    public void test12(){
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        Map<String, Object> varibales = new HashMap<String, Object>();
        varibales.put("yesListener", new YesListener());
        varibales.put("noListener", new NoListener());

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), varibales);
        System.out.println("processInstance = " + processInstance);
    }

    @Test
    public void test121(){
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> list = taskQuery.taskAssignee("zhangsan").list();

        for (Task task : list) {
            taskService.setVariable(task.getId(), "flag", "true");
            taskService.complete(task.getId());
        }
    }

    //11.网关 - 包含网关(排他+并行)
    @Test
    public void test11(){
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        Map<String, Object> varibales = new HashMap<String, Object>();
        varibales.put("days", "5");
        varibales.put("cost", "8000");

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), varibales);
        System.out.println("processInstance = " + processInstance);
    }

    @Test
    public void test111(){
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> list = taskQuery.taskAssignee("lisi").list();

        for (Task task : list) {
            taskService.complete(task.getId());
        }
    }

    //10.网关 - 并行网关(会签)
    @Test
    public void test10(){
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        System.out.println("processInstance = " + processInstance);
    }

    //10.网关 - 并行网关(会签) - 项目经理和财务经理都审批后,流程结束;如果只有一个经理审批,流程需要等待.
    @Test
    public void test101(){
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> list = taskQuery.taskAssignee("zhangsan").list();

        for (Task task : list) {
            taskService.complete(task.getId());
        }
    }

    //9.网关 - 排他网关(互斥)
    @Test
    public void test09(){
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        Map<String, Object> varibales = new HashMap<String, Object>();
        varibales.put("days", "5");

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), varibales);
        System.out.println("processInstance = " + processInstance);
    }

    //9.网关 - 排他网关(互斥) - 完成组长审批,观察流程走向
    @Test
    public void test091(){
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> list = taskQuery.taskAssignee("lisi").list();

        for (Task task : list) {
            taskService.complete(task.getId());
        }
    }

    //8.流程变量
    //如果存在流程变量,那么,在启动流程实例时,要给流程变量赋值.否则,启动流程实例会报错.
    //org.activiti.engine.ActivitiException: Unknown property used in expression: ${tl}
    @Test
    public void test08(){
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> varibales = new HashMap<String, Object>();
        varibales.put("tl", "zhangsan");
        varibales.put("pm", "lisi");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), varibales);
        System.out.println("processInstance = " + processInstance);
    }

    //7.领取任务
    @Test
    public void test07(){
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> list = taskQuery.taskCandidateGroup("tl").list();

        long count = taskQuery.taskAssignee("zhangsan").count();
        System.out.println("zhangsan领取前的任务数量:" + count);

        for (Task task : list) {
            System.out.println("id = " + task.getId());
            System.out.println("name = " + task.getName());
            taskService.claim(task.getId(), "zhangsan");
        }
        taskQuery = taskService.createTaskQuery();
        count = taskQuery.taskAssignee("zhangsan").count();
        System.out.println("zhangsan领取后的任务数量:" + count);

    }

    //6.历史数据查询
    @Test
    public void test06(){
        HistoryService historyService = processEngine.getHistoryService();
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        HistoricProcessInstance historicProcessInstance = historicProcessInstanceQuery.processInstanceId("101").finished().singleResult();
        System.out.println("historicProcessInstance = " + historicProcessInstance);
    }

    //5.查询流程实例的任务数据
    /**
     */
    @Test
    public void test05(){
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();

        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> list1 = taskQuery.taskAssignee("zhangsan").list();
        List<Task> list2 = taskQuery.taskAssignee("lisi").list();

        //zhangsan的任务:
        System.out.println("zhangsan的任务:");
        for (Task task : list1) {
            System.out.println("id = " + task.getId());
            System.out.println("name = " + task.getName());
            //zhangsan完成任务
            taskService.complete(task.getId());
        }
        System.out.println("------------------------------------");
        //lisi的任务:
        System.out.println("lisi的任务:");
        for (Task task : list2) {
            System.out.println("id = " + task.getId());
            System.out.println("name = " + task.getName());
            taskService.complete(task.getId());
        }
        System.out.println("========================================");

        list1 = taskQuery.taskAssignee("zhangsan").list();
        list2 = taskQuery.taskAssignee("lisi").list();

        //zhangsan的任务:
        System.out.println("zhangsan的任务:");
        for (Task task : list1) {
            System.out.println("id="+task.getId());
            System.out.println("name="+task.getName());
            //zhangsan完成任务
            taskService.complete(task.getId());
        }
        System.out.println("------------------------------------");
        //lisi的任务:
        System.out.println("lisi的任务:");
        for (Task task : list2) {
            System.out.println("id="+task.getId());
            System.out.println("name="+task.getName());
        }
    }

    //4.启动流程实例
    /**
     * act_hi_actinst, 历史的活动的任务表.
     * act_hi_procinst, 历史的流程实例表.
     * act_hi_taskinst, 历史的流程任务表
     * act_ru_execution, 正在运行的任务表.
     * act_ru_task, 运行的任务数据表.
     */
    @Test
    public void test04(){
        ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().singleResult();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        System.out.println("processInstance = " + processInstance);
    }

    //3.查询部署流程定义
    @Test
    public void test03(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = processDefinitionQuery.list();//查询所有流程定义
        for (ProcessDefinition processDefinition : list) {
            System.out.println("Id = "+processDefinition.getId());
            System.out.println("Key = "+processDefinition.getKey());
            System.out.println("Name = "+processDefinition.getName());
            System.out.println("Version = "+processDefinition.getVersion());
            System.out.println("-----------------------");
        }
        long count = processDefinitionQuery.count();//查询流程定义记录数
        System.out.println("count = "+count);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        //查询最后一次部署的流程定义
        ProcessDefinition processDefinition = processDefinitionQuery.latestVersion().singleResult();
        System.out.println("Id = "+processDefinition.getId());
        System.out.println("Key = "+processDefinition.getKey());
        System.out.println("Name = "+processDefinition.getName());
        System.out.println("Version = "+processDefinition.getVersion());
        System.out.println("******************************");

        //排序查询流程定义,分页查询流程定义.
        ProcessDefinitionQuery definitionQuery = processDefinitionQuery.orderByProcessDefinitionVersion().desc();
        List<ProcessDefinition> listPage = definitionQuery.listPage(0, 2);
        for (ProcessDefinition processDefinition2 : listPage) {
            System.out.println("Id = "+processDefinition2.getId());
            System.out.println("Key = "+processDefinition2.getKey());
            System.out.println("Name = "+processDefinition2.getName());
            System.out.println("Version = "+processDefinition2.getVersion());
            System.out.println("-----------------------");
        }

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        //根据流程定义的 Key 查询流程定义对象
        ProcessDefinition processDefinition2 = processDefinitionQuery.processDefinitionKey("myProcess_1").latestVersion().singleResult();
        System.out.println("Id = "+processDefinition2.getId());
        System.out.println("Key = "+processDefinition2.getKey());
        System.out.println("Name = "+processDefinition2.getName());
        System.out.println("Version = "+processDefinition2.getVersion());
        System.out.println("-----------------------");
    }

    //2.部署流程定义
    @Test
    public void test02(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("MyProcess9.bpmn").deploy();
        System.out.println("deploy = " + deploy);
    }

    //1.创建流程引擎,创建23张表
    @Test
    public void test01(){
        System.out.println(processEngine);
    }

}
