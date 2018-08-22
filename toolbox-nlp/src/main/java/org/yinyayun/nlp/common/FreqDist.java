package org.yinyayun.nlp.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * key值计数器
 * 
 * @author yinyayun
 *
 * @param <T>
 */
public class FreqDist<K> {
	private double total;
	private Map<K, Double> dist = new HashMap<K, Double>();

	public void add(K key) {
		dist.compute(key, (k, v) -> v == null ? 1d : ++v);
		++total;
	}

	public double freq(K key) {
		return dist.getOrDefault(key, 0d);
	}

	public Set<K> keys() {
		return dist.keySet();
	}

	public Set<Entry<K, Double>> entrys() {
		return dist.entrySet();
	}

	public double total() {
		return total;
	}
}
