package com.lhx.aggregate.tools;

import java.util.List;

/**
 * 分页
 * 
 * @since 2015年5月14日 下午5:02:38
 * @author yaowenhao
 */
public class PageBean<T> {

	private int currentPage = 1; // 当前页码

	private int count = 0; // 总条数

	private int pageAll; // 总页数

	private int offset; // 开始条数

	private int pageSize = 20; // 每页大小

	private List<T> result; // 结果

	public PageBean() {

	}

	public PageBean(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @param currentPage
	 *            当前页码
	 * @param count
	 *            数据总共条数
	 */
	public PageBean(int currentPage, int count) {

		this.offset = (currentPage - 1) * pageSize;

		this.pageAll = (count + pageSize - 1) / pageSize;

		this.currentPage = currentPage > pageAll ? pageAll
				: currentPage <= 0 ? 1 : currentPage;

		this.count = count;
	}

	/**
	 * @param currentPage
	 *            当前页码
	 * @param pageSize
	 *            页码大小
	 * @param count
	 *            数据总共条数
	 */
	public PageBean(int currentPage, int pageSize, int count) {
		this.pageSize = pageSize;
		this.offset = (currentPage - 1) * pageSize;
		this.pageAll = (count + pageSize - 1) / pageSize;
		this.currentPage = currentPage > pageAll ? pageAll
				: currentPage <= 0 ? 1 : currentPage;
		this.count = count;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPageAll() {
		this.pageAll = (count + pageSize - 1) / pageSize;
		return pageAll;
	}

	public void setPageAll(int pageAll) {
		this.pageAll = pageAll;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}