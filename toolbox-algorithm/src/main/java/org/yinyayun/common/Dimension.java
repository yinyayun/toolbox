package org.yinyayun.common;

/**
 * 维度
 * 
 * @author yinyayun
 *
 */
public class Dimension {
	public long x;
	public long y;

	public Dimension(long x, long y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}

}
