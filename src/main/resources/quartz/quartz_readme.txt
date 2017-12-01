Quartz建议总是放一个自己的quartz.properties文件在classpath下，
运行时可覆盖quartz.jar中的quartz.properties的配置，因为根据自己实际的应用，
需要对quartz配置作些调整。

一般默认设置为：
org.quartz.scheduler.instanceName = DefaultQuartzScheduler
org.quartz.scheduler.rmi.export = false
org.quartz.scheduler.rmi.proxy = false
org.quartz.scheduler.wrapJobExecutionInUserTransaction = false

org.quartz.threadPool.class = org.quartz.simpl.SimpleThreadPool
org.quartz.threadPool.threadCount = 10
org.quartz.threadPool.threadPriority = 5
org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread = true

org.quartz.jobStore.misfireThreshold = 60000

org.quartz.jobStore.class = org.quartz.simpl.RAMJobStore

org.quartz.threadPool.threadCount 设置大一些，同时启动更多的线程来处理作业执行，默认值为5

org.quartz.jobStore.misfireThreshold大致意思是当前时超过已安排时间多久的作业不执行

比如说设置了一个工作线程，安排作业是一秒执行一次，作业本身执行10秒
如果
org.quartz.jobStore.misfireThreshold = 60000 #60秒

那么执行第一次作业是在10:01秒，这时会设定下一次的执行时间为10:02秒，要等一个作业执行完之后才有可用线程，
大概要在10:11秒才能执行前面安排的应该在10:02执行的作业，这时就会用到misfireThreshold, 因为
10:11与10:02之间的差值小于60000，所以执行该作业，并以10:02为基准设置下一次执行时间为10:03，
这样造成每次实际执行时间与安排时间错位

如果
org.quartz.jobStore.misfireThreshold = 6000 #6秒
同样，在10:11计划执行安排在10:02的作业，发现10:11与10:02之间的差值大于6000毫秒，那么直接跳过该作业，
执行本应在当前时间执行的作业，这时候会以10:11为基准设定下次作业执行时间为10:12

不知道这样能不能理解清楚，有时候要保证执行次数，值就可以大一些；有些时候避免更多机会交替执行同一个作业就应该设置小一些。

有一个程序跑出来输出,作业执行代码如下：

DateFormat df = new SimpleDateFormat("hh:mm:ss");
System.err.println("["+Thread.currentThread().getName()+"] Now: "+df.format(context.getFireTime())
+" Scheduled: "+df.format(context.getScheduledFireTime())
+" Previous: "+df.format(context.getPreviousFireTime())
+" Next: "+df.format(context.getNextFireTime()));
try {
Thread.sleep(10*1000);
System.out.println("["+Thread.currentThread().getName()+"] Slept 10 seconds");
} catch (InterruptedException e) {
e.printStackTrace();
}

预设的工作线程为1，执行任务时间10秒misfireThreshold为6000时输出结果为：
[UnmiQuartzScheduler_Worker-1] Now: 08:26:20 Scheduled: 08:26:20 Previous: 08:26:10 Next: 08:26:21
[UnmiQuartzScheduler_Worker-1] Slept 10 seconds
[UnmiQuartzScheduler_Worker-1] Now: 08:26:30 Scheduled: 08:26:30 Previous: 08:26:20 Next: 08:26:31
[UnmiQuartzScheduler_Worker-1] Slept 10 seconds
[UnmiQuartzScheduler_Worker-1] Now: 08:26:40 Scheduled: 08:26:40 Previous: 08:26:30 Next: 08:26:41
[UnmiQuartzScheduler_Worker-1] Slept 10 seconds
[UnmiQuartzScheduler_Worker-1] Now: 08:26:50 Scheduled: 08:26:50 Previous: 08:26:40 Next: 08:26:51
[UnmiQuartzScheduler_Worker-1] Slept 10 seconds
[UnmiQuartzScheduler_Worker-1] Now: 08:27:00 Scheduled: 08:27:00 Previous: 08:26:50 Next: 08:27:01

预设的工作线程为1，执行任务时间10秒misfireThreshold为60000时输出结果为：
[UnmiQuartzScheduler_Worker-1] Now: 08:28:23 Scheduled: 08:27:47 Previous: 08:27:46 Next: 08:27:48
[UnmiQuartzScheduler_Worker-1] Slept 10 seconds
[UnmiQuartzScheduler_Worker-1] Now: 08:28:33 Scheduled: 08:27:48 Previous: 08:27:47 Next: 08:27:49
[UnmiQuartzScheduler_Worker-1] Slept 10 seconds
[UnmiQuartzScheduler_Worker-1] Now: 08:28:43 Scheduled: 08:27:49 Previous: 08:27:48 Next: 08:27:50
[UnmiQuartzScheduler_Worker-1] Slept 10 seconds
[UnmiQuartzScheduler_Worker-1] Now: 08:28:53 Scheduled: 08:28:53 Previous: 08:27:49 Next: 08:28:54
[UnmiQuartzScheduler_Worker-1] Slept 10 seconds
[UnmiQuartzScheduler_Worker-1] Now: 08:29:03 Scheduled: 08:28:54 Previous: 08:28:53 Next: 08:28:55

当前任务执行的时间如果已经超过了设定时间，则当前任务执行完后，会立即执行原来设定将要调度的任务。