package com.hifo.dataoperation.pagination;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @author jinzhichen
 */
@ApiModel(value = "分页对象")
@Data
public class Pagination<T> implements IPage<T> {
    private static final long serialVersionUID = 1L;
    /**
     * 查询数据列表
     */
    @ApiModelProperty(hidden = true)
    private List<T> records = Collections.emptyList();
    /**
     * 总数
     */
    @ApiModelProperty(hidden = true)
    private long total = 0;

    @ApiModelProperty(hidden = true)
    private long current = 1;

    @ApiModelProperty(hidden = true)
    private long size = 10;

    /**
     * 一共有多少页
     */
    private long totalPage;
    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页", name = "page", position = 1, required = true)
    private long page = 1;
    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty(value = "每页显示条数", name = "limit", position = 2, example = "10", required = true)
    private long limit = 20;
    /**
     * <p>
     * SQL 排序 ASC 数组
     * </p>
     */
    @ApiModelProperty(value = "排序ASC数组", name = "ascs", position = 3)
    private String[] ascs;
    /**
     * <p>
     * SQL 排序 DESC 数组
     * </p>
     */
    @ApiModelProperty(value = "排序DESC数组", name = "descs", position = 4)
    private String[] descs;
    /**
     * <p>
     * 自动优化 COUNT SQL
     * </p>
     */
    @ApiModelProperty(hidden = true)
    private boolean optimizeCountSql = true;
    /**
     * <p>
     * 是否进行 count 查询
     * </p>
     */
    @ApiModelProperty(hidden = true)
    private boolean isSearchCount = true;

    public Pagination() {
    }

    /**
     * <p>
     * 分页构造函数
     * </p>
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public Pagination(long current, long size) {
        this(current, size, 0);
    }

    public Pagination(long current, long size, long total) {
        this(current, size, total, true);
    }

    public Pagination(long current, long size, boolean isSearchCount) {
        this(current, size, 0, isSearchCount);
    }

    public Pagination(long current, long size, long total, boolean isSearchCount) {
        if (current > 1) {
            this.current = current;
            this.page = current;
        }
        this.size = size;
        this.limit = size;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    /**
     * <p>
     * 是否存在上一页
     * </p>
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.current > 1;
    }

    /**
     * <p>
     * 是否存在下一页
     * </p>
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.current < this.getPages();
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public Pagination<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public Pagination<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.limit;
    }

    @Override
    public Pagination<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.page;
    }

    @Override
    public Pagination<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    @Override
    public String[] ascs() {
        return ascs;
    }

    public Pagination<T> setAscs(List<String> ascs) {
        if (CollectionUtils.isNotEmpty(ascs)) {
            this.ascs = ascs.toArray(new String[0]);
        }
        return this;
    }

    /**
     * <p>
     * 升序
     * </p>
     *
     * @param ascs 多个升序字段
     */
    public Pagination<T> setAsc(String... ascs) {
        this.ascs = ascs;
        return this;
    }

    @Override
    public String[] descs() {
        return descs;
    }

    public Pagination<T> setDescs(List<String> descs) {
        if (CollectionUtils.isNotEmpty(descs)) {
            this.descs = descs.toArray(new String[0]);
        }
        return this;
    }

    /**
     * <p>
     * 降序
     * </p>
     *
     * @param descs 多个降序字段
     */
    public void setDesc(String... descs) {
        this.descs = descs;
    }

    @Override
    public boolean optimizeCountSql() {
        return optimizeCountSql;
    }

    public boolean isSearchCount() {
        if (total < 0) {
            return false;
        }
        return isSearchCount;
    }

    public Pagination<T> setSearchCount(boolean isSearchCount) {
        this.isSearchCount = isSearchCount;
        return this;
    }

    public Pagination<T> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }


    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }


    /**
     * 获取第一条记录位置
     *
     * @return
     */
    public long getFirstResult() {
        return (this.getPage() - 1) * this.getLimit();
    }

    /**
     * 获取最后记录位置
     *
     * @return
     */
    public long getLastResult() {
        return this.getPage() * this.getLimit();
    }

    /**
     * 计算一共多少页
     */
    public void setTotalPage() {
        this.totalPage = (long) ((this.getTotal() % this.getLimit() > 0) ? (this.getTotal() / this.getLimit() + 1)
                : this.getTotal() / this.getLimit());
    }


}
