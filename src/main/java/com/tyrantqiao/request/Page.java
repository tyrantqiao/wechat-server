package com.tyrantqiao.request;

import com.tyrantqiao.util.EmptyUtil;

import java.io.Serializable;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
public class Page implements Serializable {
	/**
	 * 当前页。
	 */
	private int page = 1;

	/**
	 * 每页显示记录数。
	 */
	private int size = 10;

	/**
	 * 排序列标识。
	 */
	private String order;

	public Page() {

	}

	public Page(String page, String size) {
		if (EmptyUtil.isNotEmpty(page)) {
			this.page = Integer.valueOf(page);
		}
		if (EmptyUtil.isNotEmpty(size)) {
			this.size = Integer.valueOf(size);
		}
	}

	@Override
	public String toString() {
		return "Page{" + "page=" + this.page + ", size=" + this.size + ", order='" + this.order + '\'' + '}';
	}

	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size < 1 ? 10 : size;
	}

	public int getOffset() {
		return (this.page - 1) * this.size;
	}
}
