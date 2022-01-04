package com.aibaixun.common.mybatisplus;

/**
 * 自动填充
 * @author wangxiao@aibaixun.com
 * @date 2021/12/30
 */
public class AutoFillProperties {

    /**
     * 是否开启自动填充字段
     */
    private Boolean enabled = true;
    /**
     * 是否开启了插入填充
     */
    private Boolean enableInsertFill = true;
    /**
     * 是否开启了更新填充
     */
    private Boolean enableUpdateFill = true;
    /**
     * 创建时间字段名
     */
    private String createTimeField = "createTime";
    /**
     * 更新时间字段名
     */
    private String updateTimeField = "updateTime";


    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnableInsertFill() {
        return enableInsertFill;
    }

    public void setEnableInsertFill(Boolean enableInsertFill) {
        this.enableInsertFill = enableInsertFill;
    }

    public Boolean getEnableUpdateFill() {
        return enableUpdateFill;
    }

    public void setEnableUpdateFill(Boolean enableUpdateFill) {
        this.enableUpdateFill = enableUpdateFill;
    }

    public String getCreateTimeField() {
        return createTimeField;
    }

    public void setCreateTimeField(String createTimeField) {
        this.createTimeField = createTimeField;
    }

    public String getUpdateTimeField() {
        return updateTimeField;
    }

    public void setUpdateTimeField(String updateTimeField) {
        this.updateTimeField = updateTimeField;
    }
}
