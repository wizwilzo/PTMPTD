import java.util.*;

class Solution {
    public double solve(int[][] edges, double[] success, int start, int end) {
        // maps a node to a set of pairs where the key in the pair is a neighbor and the value in
        // the pair is the weight
        Map<Integer, Set<Pair<Integer, Double>>> map = new HashMap<>();
        Map<Integer, Double> ref = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            // declare dem variables
            int u = edges[i][0];
            int v = edges[i][1];
            double weight = success[i];
            // add the nodes to our graph
            map.putIfAbsent(u, new HashSet());
            map.get(u).add(new Pair(v, weight));

            map.putIfAbsent(v, new HashSet());
            map.get(v).add(new Pair(u, weight));
            // build the reference array
            ref.putIfAbsent(u, Double.MIN_VALUE);
            ref.putIfAbsent(v, Double.MIN_VALUE);
        }

        // first element is the node
        // second element is the probability
        PriorityQueue<double[]> pq = new PriorityQueue<>(new Comparator<double[]>() {
            public int compare(double[] a, double[] b) {
                return -1 * Double.compare(a[1], b[1]);
            }
        });
        pq.offer(new double[] {(double) start, 1.0});

        HashSet<Integer> vis = new HashSet();
        while (!pq.isEmpty()) {
            double[] temp = pq.poll();
            int node = (int) (temp[0]);
            double probability = temp[1];
            if (vis.contains(node) || probability < ref.get(node)) {
                continue;
            }
            vis.add(node);
            for (Pair<Integer, Double> neighbor : map.get(node)) {
                int newNode = neighbor.getKey();
                double newProbability = neighbor.getValue() * probability;
                if (newProbability >= ref.get(newNode)) {
                    pq.offer(new double[] {(double) newNode, newProbability});
                    ref.put(newNode, newProbability);
                }
            }
        }
        return ref.get(end);
    }
}
