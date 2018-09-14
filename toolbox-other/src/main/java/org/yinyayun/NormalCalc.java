package org.yinyayun;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

public class NormalCalc {
	public void calc(String express) {
		// 分离操作符和操作数
		Set<String> set = new HashSet<String>(Arrays.asList(new String[] { "+", "-", "*", "/", "(", ")" }));
		StringTokenizer tokenizer = new StringTokenizer(express, "+-*/()", true);
		List<String> ops = new ArrayList<String>();
		List<Float> numbers = new ArrayList<Float>();
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (set.contains(token)) {
				ops.add(token);
			} else {
				numbers.add(Float.valueOf(token));
			}
		}
		// 对操作符操作数压栈的同时进行四则运算
		Stack<String> opStack = new Stack<String>();
		Stack<Float> numStack = new Stack<Float>();
		int operIndex = 0;
		for (String sym : ops) {
			if (sym.equals("+") || sym.equals("-")) {
				opStack.push(sym);
				numStack.push(numbers.get(operIndex++));
				numStack.push(numbers.get(operIndex++));
			} else if (sym.equals("*") || sym.equals("/")) {
				if (numStack.isEmpty()) {
					// 如果操作数栈中无数据，计算接下来要压栈的两个数结果,将结果压栈。示例：1*2
					float tmp = cal(numbers.get(operIndex++), numbers.get(operIndex++), sym);
					numStack.push(tmp);
				} else {
					// 如果操作数栈中有数据，则从操作数栈中弹出一个数，再取下一个要压栈的数，结算结果压栈。示例：1+2*3
					float tmp = cal(numStack.pop(), numbers.get(operIndex++), sym);
					numStack.push(tmp);
				}
			} else if (sym.equals(")")) {
				// 注意（1+2-3）
				String op = opStack.pop();
				while (!op.equals("(")) {
					float n2 = numStack.pop();
					float n1 = numStack.pop();
					numStack.push(cal(n1, n2, op));
					op = opStack.pop();
				}
			} else {
				opStack.push(sym);
			}
		}
		// 操作符栈中只剩下+-
		while (!opStack.isEmpty()) {
			String op = opStack.pop();
			float n2 = numStack.pop();
			float n1 = numStack.pop();
			numStack.push(cal(n1, n2, op));
		}
		System.out.println(numStack.pop());
	}

	public float cal(float n1, float n2, String oper) {
		if (oper.equals("*")) {
			return n1 * n2;
		} else if (oper.equals("/")) {
			return n1 / n2;
		} else if (oper.equals("+")) {
			return n1 + n2;
		} else {
			return n1 - n2;
		}
	}

	public static void main(String[] args) {
		NormalCalc calc = new NormalCalc();
		calc.calc("3*(1+2)");
	}

}
