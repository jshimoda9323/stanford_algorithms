package week11q1;

import java.io.File;
import java.util.*;

public class week11q1 {

	public static void main(String[] args) {
		int totalNodes;
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(new NodeComparator());
		
		if (args.length != 1) { System.err.println("Expecting input filename."); return; }
		File inFile = new File(args[0]);
		try (Scanner inScanner = new Scanner(inFile)) {
			totalNodes = Integer.parseInt(inScanner.nextLine());
			while(inScanner.hasNextLine()) {
				nodes.add(new Node(Long.parseLong(inScanner.nextLine())));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		Node top = constructHuffman(nodes);
		int longestDepth = computeDepths(top,-1);
		System.out.println("Longest code is length "+longestDepth);
		System.out.println("Shortest code is length "+findDepthOfShallowestLeaf(top));
	}
	
	public static Node constructHuffman(PriorityQueue<Node> nodes) {
		while(nodes.size() > 1) {
			Node right = nodes.poll();
			Node left = nodes.poll();
			Node newNode = new Node(right.weight + left.weight);
			newNode.left = left;
			newNode.right = right;
			nodes.add(newNode);
		}
		return(nodes.poll());
	}
	
	public static int findDepthOfShallowestLeaf(Node top) {
		LinkedList<Node> nodes = new LinkedList<Node>();
		Node curNode = top;
		while ((curNode.left != null) | (curNode.right != null)) {
			if (curNode.left != null) { nodes.add(curNode.left); }
			if (curNode.right != null) { nodes.add(curNode.right); }
			curNode = nodes.poll();
		}
		return(curNode.depth);
		
	}
	
	public static int computeDepths(Node top, int parentDepth) {
		int leftDepth = 0;
		int rightDepth = 0;
		top.depth = parentDepth + 1;
		if (top.left != null) {
			leftDepth = computeDepths(top.left,top.depth);
		}
		if (top.right != null) {
			rightDepth = computeDepths(top.right,top.depth);
		}
		return(Math.max(top.depth, Math.max(leftDepth, rightDepth)));
	}

}
