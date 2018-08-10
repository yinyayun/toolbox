package org.yinyayun.lb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单的一致性HASH
 * 
 * @author yinyayun
 *
 */
public class ConsistentHash {
	private List<Integer> hashRing;
	private Map<Integer, String> keyHashMap = new HashMap<Integer, String>();

	/**
	 * @param nodes
	 *            节点列表
	 * @param replicatNodeSize
	 *            每个节点的副本数
	 */
	public ConsistentHash(List<String> nodes, int replicatNodeSize) {
		hashRing = new ArrayList<Integer>(nodes.size() * replicatNodeSize + 1);
		for (String node : nodes) {
			for (int i = 0; i < replicatNodeSize; i++) {
				String key = node + "_" + i;
				int hash = hash(key);
				keyHashMap.put(hash, key);
				hashRing.add(hash);
			}
		}
		Collections.sort(hashRing);
	}

	public String get(String key) {
		int hash = hash(key);
		for (int h : hashRing) {
			if (hash < h) {
				return keyHashMap.get(h);
			}
		}
		return keyHashMap.get(hashRing.get(0));
	}

	private int hash(String key) {
		return key.hashCode();
	}
}
