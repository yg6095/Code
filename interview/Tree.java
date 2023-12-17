package interview;

import java.util.PriorityQueue;

/**
 * 题意：
 * 给你一些中文字符串和这个字符串所拥有的权值，实现按照权值推荐下一个字
 * 		 我要吃苹果  30
 * 		 我要吃芒果  40
 * 		 我要学习    50
 * 		 我想唱歌    100
 * 输入 "我要"  输出"吃"  因为吃的权值为70，学的权值为50
 */
public class Tree {
    public static void main(String[] args) {
        Tree tree = Tree.generate();
        tree.build("我爱吃薯条", 30);
        tree.build("我爱吃辣片", 40);
        tree.build("我爱学习", 50);
        tree.build("我想学习", 100);

        System.out.println(tree.query("我爱"));
    }

    Node root;

    public Tree() {
        this.root = new Node(null, 0);
    }

    public static Tree generate() {
        return new Tree();
    }

    public String query(String str) {
        return search(root, str);
    }


    public void build(String str, int value) {
        addNode(root, str, value);
    }

    private String search(Node node, String str) {
        if (str.isBlank()) {
            if (!node.childNode.isEmpty()) {
                return node.childNode.peek().word;
            }
            return "";
        }
        for (Node child : node.childNode) {
            if (child.word.equals(str.substring(0, 1))) {
                return search(child, str.substring(1));
            }
        }
        return "";
    }

    private void addNode(Node node, String str, int value) {
        if (str.isBlank()) {
            return;
        }
        for (Node child : node.childNode) {
            if (child.word.equals(str.substring(0, 1))) {
                child.addValue(value);
                addNode(child, str.substring(1), value);
                return;
            }
        }
        Node newNode = new Node(str.substring(0, 1), value);
        node.addChild(newNode);
        addNode(newNode, str.substring(1), value);
    }


    public static class Node implements Comparable<Node> {
        String word;
        int value;
        PriorityQueue<Node> childNode = new PriorityQueue<>();

        public Node(String word, int value) {
            this.word = word;
            this.value = value;
        }

        public void addValue(int value) {
            this.value += value;
        }

        public void addChild(Node node) {
            this.childNode.add(node);
        }

        @Override
        public int compareTo(Node o) {
            return o.value - this.value;
        }
    }
}
