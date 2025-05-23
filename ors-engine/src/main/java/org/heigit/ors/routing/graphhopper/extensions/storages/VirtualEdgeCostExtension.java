package org.heigit.ors.routing.graphhopper.extensions.storages;

import com.graphhopper.storage.GraphExtension;
import java.util.HashMap;

public class VirtualEdgeCostExtension implements GraphExtension {
    private final HashMap<Integer, double[]> edgeCosts = new HashMap<>();

    public void setCosts(int edgeId, double[] costs) {
        edgeCosts.put(edgeId, costs);
    }

    public double[] getCosts(int edgeId) {
        return edgeCosts.get(edgeId);
    }

    public boolean hasCosts(int edgeId) {
        return edgeCosts.containsKey(edgeId);
    }

    @Override
    public GraphExtension copyTo(GraphExtension other) { return this; }
    @Override
    public void flush() {}
    @Override
    public void close() {}
    @Override
    public boolean isRequireNodeField() { return false; }
    @Override
    public int getDefaultNodeFieldValue() { return 0; }
    @Override
    public void setSegmentSize(int bytes) {}
    @Override
    public String getName() { return "virtual_edge_cost_extension"; }
}