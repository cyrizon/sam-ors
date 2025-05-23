/* -------------------------------
* New code for SAM project
* -------------------------------*/
package org.heigit.ors.routing.graphhopper.extensions;

import com.graphhopper.storage.GraphHopperStorage;
import com.graphhopper.util.EdgeIteratorState;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class VirtualEdgeInjector {
    /**
     * Injects virtual edges into the GraphHopperStorage from a CSV file using an OSM to GH node ID map.
     * The CSV must have columns: osm_from_node_id, osm_to_node_id, distance_km, ...
     */
    public static void inject(GraphHopperStorage g, String csvPath, HashMap<Long, Integer> osmToGh) {
        int injected = 0;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(csvPath))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] tokens = line.split(",");
                if (tokens.length < 3) continue;

                long osmFrom = (long) Double.parseDouble(tokens[0]);
                long osmTo = (long) Double.parseDouble(tokens[1]);
                Integer from = osmToGh.get(osmFrom);
                Integer to = osmToGh.get(osmTo);
                if (from == null || to == null) {
                    System.out.println("Skip: OSM from=" + osmFrom + " to=" + osmTo + " (mapping not found)");
                    continue;
                }

                double distance = Double.parseDouble(tokens[2]);
                System.out.println("Inject edge: from=" + from + ", to=" + to);
                g.edge(from, to).setDistance(distance * 1000);
                injected++;
            }
            System.out.println("[VirtualEdgeInjector] Injected " + injected + " virtual edges from " + csvPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<Long, Integer> loadOsmToGhNodeIdMap(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (HashMap<Long, Integer>) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("Cannot load OSM→GH node mapping", e);
        }
    }
}
/* -------------------------------
* End of new code for SAM project
* -------------------------------*/