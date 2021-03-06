package cn.org.japd.common.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 实体公共类<br>
 * 所有的实体都继承该对象
 * 
 * @author WuZhiFeng
 * @date 2016年7月29日
 */
@MappedSuperclass
public abstract class BaseEntity implements Persistable<String> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id", nullable = false, unique = true, length = 33)
	private String id;

	/**
	 * 创建时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false)
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 时间戳
	 */
	private Date ts;

	public String getId() {
		return id;
	}

	protected void setId(final String id) {
		this.id = id;
	}

	
	public boolean isNew() {
		return this.id == null;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	/**
	 * 新增前触发
	 */
	@PrePersist
	public void prePersist() {
		Date nowDate=new Date();
		this.createTime = nowDate;//设置创建时间为当前时间
		this.ts = nowDate;//设置时间戳为当前时间
	}

	/**
	 * 更新前触发
	 */
	@PreUpdate
	public void preUpdate() {
		Date nowDate=new Date();
		this.modifyTime = nowDate;//设置最后修改时间为当前时间
		this.ts = nowDate;//设置时间戳为当前时间
	}

	public String toJSONString() {
		return JSON.toJSONString(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!getClass().equals(obj.getClass())) {
			return false;
		}
		BaseEntity rhs = (BaseEntity) obj;
		return this.id == null ? false : this.id.equals(rhs.id);
	}
}