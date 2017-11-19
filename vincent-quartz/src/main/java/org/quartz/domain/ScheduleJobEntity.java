package org.quartz.domain;
import java.io.Serializable;

/** 定时器
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月28日 下午12:54:44 */
public class ScheduleJobEntity implements Serializable {
	
	private static final long	serialVersionUID	= 1L;
	/** 任务调度参数key */
	public static final String	JOB_PARAM_KEY		= "JOB_PARAM_KEY";
	/** 任务id */
	private Long				job_Id;
	/** spring bean名称 */
	private String				bean_Name;
	/** 方法名 */
	private String				method_Name;
	/** 参数 */
	private String				params;
	/** cron表达式 */
	private String				cron_Expression;
	/** 任务状态 */
	private Integer				status;									// 0暂停 1启用
	/** 备注 */
	private String				remark;
	/** 创建时间 */
	private String				create_Time;
	
	@Override
	public String toString() {
		return "ScheduleJobDO [jobId=" + job_Id + ", beanName=" + bean_Name + ", methodName=" + method_Name + ", params=" + params + ", cronExpression=" + cron_Expression + ", status=" + status + ", remark=" + remark + ", createTime=" + create_Time + "]";
	}
	
	public Long getJob_Id() {
		return job_Id;
	}
	
	public void setJob_Id(Long job_Id) {
		this.job_Id = job_Id;
	}
	
	public String getBean_Name() {
		return bean_Name;
	}
	
	public void setBean_Name(String beanName) {
		this.bean_Name = beanName;
	}
	
	public String getMethod_Name() {
		return method_Name;
	}
	
	public void setMethod_Name(String methodName) {
		this.method_Name = methodName;
	}
	
	public String getParams() {
		return params;
	}
	
	public void setParams(String params) {
		this.params = params;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/** 设置：任务状态
	 * 
	 * @param status
	 *            任务状态 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/** 获取：任务状态
	 * 
	 * @return String */
	public Integer getStatus() {
		return status;
	}
	
	/** 设置：cron表达式
	 * 
	 * @param cronExpression
	 *            cron表达式 */
	public void setCron_Expression(String cronExpression) {
		this.cron_Expression = cronExpression;
	}
	
	/** 获取：cron表达式
	 * 
	 * @return String */
	public String getCron_Expression() {
		return cron_Expression;
	}
	
	/** 设置：创建时间
	 * 
	 * @param createTime
	 *            创建时间 */
	public void setCreate_Time(String createTime) {
		this.create_Time = createTime;
	}
	
	/** 获取：创建时间
	 * 
	 * @return Date */
	public String getCreate_Time() {
		return create_Time;
	}
}
