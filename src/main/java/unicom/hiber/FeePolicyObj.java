package unicom.hiber;
// default package

import java.util.Date;


/**
 * FeePolicyObj entity. @author MyEclipse Persistence Tools
 */

public class FeePolicyObj  implements java.io.Serializable {


    // Fields    

     private Long feepolicyInsId;
     private Short partitionId;
     private String idType;
     private Long id;
     private Integer feepolicyId;
     private Long relaUserId;
     private Integer productId;
     private Integer servBundId;
     private Integer servId;
     private Integer feepolicyBundId;
     private Date startDate;
     private Date endDate;
     private Date updateTime;
     private String updateDepartId;
     private String updateStaffId;


    // Constructors

    /** default constructor */
    public FeePolicyObj() {
    }

	/** minimal constructor */
    public FeePolicyObj(Short partitionId, String idType, Long id, Integer feepolicyId, Date startDate, Date endDate, Date updateTime) {
        this.partitionId = partitionId;
        this.idType = idType;
        this.id = id;
        this.feepolicyId = feepolicyId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.updateTime = updateTime;
    }
    
    /** full constructor */
    public FeePolicyObj(Short partitionId, String idType, Long id, Integer feepolicyId, Long relaUserId, Integer productId, Integer servBundId, Integer servId, Integer feepolicyBundId, Date startDate, Date endDate, Date updateTime, String updateDepartId, String updateStaffId) {
        this.partitionId = partitionId;
        this.idType = idType;
        this.id = id;
        this.feepolicyId = feepolicyId;
        this.relaUserId = relaUserId;
        this.productId = productId;
        this.servBundId = servBundId;
        this.servId = servId;
        this.feepolicyBundId = feepolicyBundId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.updateTime = updateTime;
        this.updateDepartId = updateDepartId;
        this.updateStaffId = updateStaffId;
    }

   
    // Property accessors

    public Long getFeepolicyInsId() {
        return this.feepolicyInsId;
    }
    
    public void setFeepolicyInsId(Long feepolicyInsId) {
        this.feepolicyInsId = feepolicyInsId;
    }

    public Short getPartitionId() {
        return this.partitionId;
    }
    
    public void setPartitionId(Short partitionId) {
        this.partitionId = partitionId;
    }

    public String getIdType() {
        return this.idType;
    }
    
    public void setIdType(String idType) {
        this.idType = idType;
    }

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFeepolicyId() {
        return this.feepolicyId;
    }
    
    public void setFeepolicyId(Integer feepolicyId) {
        this.feepolicyId = feepolicyId;
    }

    public Long getRelaUserId() {
        return this.relaUserId;
    }
    
    public void setRelaUserId(Long relaUserId) {
        this.relaUserId = relaUserId;
    }

    public Integer getProductId() {
        return this.productId;
    }
    
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getServBundId() {
        return this.servBundId;
    }
    
    public void setServBundId(Integer servBundId) {
        this.servBundId = servBundId;
    }

    public Integer getServId() {
        return this.servId;
    }
    
    public void setServId(Integer servId) {
        this.servId = servId;
    }

    public Integer getFeepolicyBundId() {
        return this.feepolicyBundId;
    }
    
    public void setFeepolicyBundId(Integer feepolicyBundId) {
        this.feepolicyBundId = feepolicyBundId;
    }

    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateDepartId() {
        return this.updateDepartId;
    }
    
    public void setUpdateDepartId(String updateDepartId) {
        this.updateDepartId = updateDepartId;
    }

    public String getUpdateStaffId() {
        return this.updateStaffId;
    }
    
    public void setUpdateStaffId(String updateStaffId) {
        this.updateStaffId = updateStaffId;
    }
   








}