package com.tyrantqiao.request;

import java.io.Serializable;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
public class Pagination implements Serializable {
	private static final int DEFAULT_PAGE_SIZE = 20;

	private int start = 0;

	/**
	 * 每页的记录数。
	 */
	private int size = DEFAULT_PAGE_SIZE;
	/**
	 * 当前页
	 */
	private int currPage = 1;
	/**
	 * 总计有多少页。
	 */
	private int totalPage = 0;
	/**
	 * 总记录数
	 */
	private int total = 0;
	/**
	 * 当前页记录数.
	 */
	private int pageCount = 0;

	public Pagination() {
	}

	/**
	 * @param currPage
	 * @deprecated 使用 Pagination.newInstance() 更加严谨和方便
	 */
	@Deprecated
	public Pagination(int currPage) {
		this.currPage = currPage;
	}

	/**
	 * @param page
	 * @deprecated 使用 Pagination.newInstance() 更加严谨和方便
	 */
	@Deprecated
	public Pagination(Page page) {
		this.size = page.getSize();
		this.currPage = page.getPage();
	}

	/**
	 * 通过请求的分页信息、结果总数和当页实际记录数构建一个分页信息对象。
	 *
	 * @param page      请求的分页信息
	 * @param total     结果总数
	 * @param pageCount 当页实际记录数
	 * @return 分页信息实例
	 */
	public static Pagination newInstance(Page page, int total, int pageCount) {
		Pagination instance = new Pagination(page);
		instance.setTotal(total);
		instance.setPageCount(pageCount);
		return instance;
	}

	/**
	 * 通过请求的分页信息、结果总数和当页实际记录数构建一个分页信息对象。
	 *
	 * @param size      分页的数量
	 * @param currPage  当前页码
	 * @param total     结果总数
	 * @param pageCount 当页实际记录数
	 * @return 分页信息实例
	 */
	public static Pagination newInstance(int size, int currPage, int total, int pageCount) {
		Pagination instance = new Pagination();
		instance.setSize(size);
		instance.setCurrPage(currPage);
		instance.setTotal(total);
		instance.setPageCount(pageCount);
		return instance;
	}

	public int getLastPage() {
		if (this.hasLastPage()) {
			return this.currPage - 1;
		}
		return this.currPage;
	}

	public boolean hasLastPage() {
		return this.currPage > 1;
	}

	public int getNextPage() {
		if (this.hasNextPage()) {
			return this.currPage + 1;
		}
		return -1;
	}

	public boolean hasNextPage() {
		if (this.total == -1) {
			return this.pageCount >= this.size;
		} else {
			return this.currPage < this.totalPage;
		}
	}

	public int getCurrPage() {
		return this.currPage;
	}

	public int getSize() {
		return this.size;
	}

	public int getStart() {
		return this.start;
	}

	public int getTotal() {
		return this.total;
	}

	public int getTotalPage() {
		return this.totalPage;
	}

	public void setCurrPage(int currPage) {
		if (currPage > 0) {
			this.start = (currPage - 1) * this.size;
			this.currPage = currPage;
		}
	}

	public Pagination setSize(int size) {
		this.size = size;
		return this;
	}

	protected void setStart() {
	}

	public void setTotal(int total) {
		this.total = total;
		if (total == -1) {
			this.totalPage = -1;
		} else {
			this.totalPage = Double.valueOf(Math.ceil((total + this.size - 1) / this.size)).intValue();
		}
		this.start = (this.currPage - 1) * this.size;
	}

	protected void setTotalPage() {

	}

	public int getPageCount() {
		return this.pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
}
