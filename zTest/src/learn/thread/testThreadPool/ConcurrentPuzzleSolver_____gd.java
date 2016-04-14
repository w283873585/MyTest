package com.thread.testThreadPool;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
// 面向接口编程
// 抽象
// 并行递归
public class ConcurrentPuzzleSolver_____gd<P, M> {
	private final Puzzle<P, M> puzzle;
	private final ExecutorService exec;
	private final ConcurrentMap<P, Boolean> seen;
	private ValueLatch<Node<P, M>> solution = 
			new ValueLatch<Node<P, M>>();
	
	public ConcurrentPuzzleSolver_____gd(Puzzle<P, M> puzzle, ExecutorService exec,
			ConcurrentMap<P, Boolean> seen) {
		this.puzzle = puzzle;
		this.exec = exec;
		this.seen = seen;
	}
	
	
	public List<M> solve() throws InterruptedException {
		try {
			P p = puzzle.initialPosition();
			// 执行最开始的引子线程
			exec.execute(newTask(p, null, null));
			// 通过闭锁，阻塞等待结果
			Node<P, M> solnNode = solution.getValue();
			return (solnNode == null) ? null : solnNode.asMoveList();
		} finally {
			exec.shutdown();
		}
	}
	
	
	protected Runnable newTask(P p, M m, Node<P, M> n) {
		return new SolverTask(p, m, n);
	}
	
	// 根据接下来可能要走的路，
	// 分发线程去走，直到走到终点，
	// 用一个同步的map去防止重复走
	private class SolverTask extends Node<P, M> implements Runnable{

		SolverTask(P pos, M move, Node<P, M> prev) {
			super(pos, move, prev);
		}

		@Override
		public void run() {
			if (solution.isSet() || seen.putIfAbsent(pos, true) != null) {
				return;
			}
			if (puzzle.isGoal(pos)) {
				solution.setValue(this);
			} else {
				for (M m : puzzle.legalMoves(pos)) {
					exec.execute(newTask(puzzle.move(pos, m), m, this));
				}
			}
		}
	}
	
	
	// 一个线程安全类，
	// 该类的隐性不变性条件是，
	// 1. 锁打开和值设置必须是原子性操作，这样才能保证数据的正确性
	// 2. 取到的值和设置的值必须一致
	// 3. 不能重复设置值，所以判断是否已设置值和设置数据也必须保证原子性
	// 实现方法：用synchronized保证相关属性的同步
	// @threadSafe
	private static class ValueLatch<T>{
		private T value = null;
		private CountDownLatch latch = new CountDownLatch(1);
		
		// 返回是否已得到值，该方法的线程安全约束完全委托给了CountDownLatch
		public boolean isSet() {
			return latch.getCount() == 0;
		}
		
		// 设置内部值，用同步块保护变量
		public synchronized void setValue(T value) {
			if (!isSet()) {
				this.value = value;
				latch.countDown();
			}
		}
		
		public T getValue() throws InterruptedException {
			latch.await();
			synchronized(this) {
				return value;
			}
		}
	}
	
	// 牛逼的抽象
	// 抽象为放在迷宫中小旗子
	// 棋子有初始化位置，有目标，还知道附近是否能走动，最后棋子还能通过move得到新的位置
	private static interface Puzzle<P, M>{
		// 初始化位置
		P initialPosition();
		// 是否是目标
		boolean isGoal(P position);
		// 合法的moves，产生Move对象的工厂方法，
		Set<M> legalMoves(P position);
		// move执行，产生新的位置
		P move(P position, M move);
	}
	
	// 节点数据对象
	private static class Node<P, M> {
		final P pos;
		final M move;
		final Node<P, M> prev;
		
		Node(P pos, M move, Node<P, M> prev) {
			this.pos = pos;
			this.move = move;
			this.prev = prev;
		}
		
		List<M> asMoveList() {
			List<M> solution = new LinkedList<M>();
			for (Node<P, M> n = this; n.move != null; n = n.prev) {
				solution.add(0, n.move);
			}
			return solution;
		}
	}
}