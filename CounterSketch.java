import java.io.*;
import java.util.*;

public class CounterSketch {
    static int totalFlows;

    static int hashes;
    static int[] randValue;
    static int counterLength;

    static int counters[][];
    static List<String> ips;
    static Map<String, Integer>
            ipsPacketMap;
    static Map<String, Integer[]>
            ipHashMap;

    static Map<String, Integer> minCountMap;

    static int min;
    static int max;

    public CounterSketch(int numFlows, int numHashes, int w) {

        totalFlows = numFlows;
        counters = new int[numHashes][w];
        hashes = numHashes;
        counterLength = w;
        min = -65536;
        max = 65535;
        randValue = new int[numHashes];
        ips = new ArrayList<>();
        ipsPacketMap = new HashMap<>();
        Random rd = new Random();
        randValue = new int[numHashes];
        ipHashMap = new HashMap<>();
        minCountMap = new HashMap<>();
        for (int i = 0; i < numHashes; i++) {
            // min + rd.nextInt(maxValue)
            randValue[i] = rd.nextInt(max - min) + min;
        }
    }

    public static void callCounterSketch()
    {
        callCounterSketch(3, 3000);
    }

    public static void callCounterSketch(int numHashes, int w) {
        int totFlows = 0;
        try {

            File f = new File("project3input.txt");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";
            String [] temp;

            totFlows = Integer.parseInt(b.readLine());
            CounterSketch counterSketch = new CounterSketch(totFlows, numHashes, w);
            while ((readLine = b.readLine()) != null) {
                temp = readLine.split("\\s+");
                ips.add(temp[0]);
                if(ipsPacketMap.containsKey(temp[0]))
                {
                    int cou = ipsPacketMap.get(temp[0]);
                    ipsPacketMap.put(temp[0], cou + Integer.parseInt(temp[1]));
                }
                else {
                    ipsPacketMap.put(temp[0], Integer.parseInt(temp[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        record();
        lookOriginalFlows();
    }

    static void lookOriginalFlows() {

        Integer[] hashVal;
        int packetCount;
        List<Integer>
                vals;
        for (int i = 0; i < totalFlows; i++) {
            hashVal = ipHashMap.get(ips.get(i));
            vals = new ArrayList<>();
            for (int j = 0; j < hashes; j++) {
                String str = String.format("%32s", Integer.toBinaryString(hashVal[j])).replaceAll(" ", "0");

                if(str.charAt(1) == '1') {
                    vals.add(counters[j][Math.abs(hashVal[j]) % counterLength]);
                }
                else
                {
                    vals.add(-counters[j][Math.abs(hashVal[j]) % counterLength]);
                }
            }
            Collections.sort(vals);
            packetCount = vals.get(vals.size()/2);
            minCountMap.put(ips.get(i), packetCount);
        }
        Set<String>
                ipSet = minCountMap.keySet();

        Map<Integer, List<String>>
                estimatedSizeIps = new TreeMap<>(Collections.reverseOrder());
        int error = 0;
        for(String key : ipSet)
        {
            if(estimatedSizeIps.containsKey(minCountMap.get(key)))
            {
                estimatedSizeIps.get(minCountMap.get(key)).add(key);
            }
            else
            {
                List<String>
                        ipList = new ArrayList<>();
                ipList.add(key);
                estimatedSizeIps.put(minCountMap.get(key), ipList);
            }
            error += Math.abs(minCountMap.get(key) - ipsPacketMap.get(key));
        }
        System.out.println(error/totalFlows);

        String tempIp;
        List<String>
                writeToFile = new ArrayList<>();
        StringBuffer sb;

        Set<Integer> keys = new LinkedHashSet<>(estimatedSizeIps.keySet());

        int count = 0;
        for (int temp : keys)
        {
            List<String>
                    ips = estimatedSizeIps.get(temp);
            for (int i = 0; i < ips.size(); i++)
            {
                tempIp = ips.get(i);
                sb = new StringBuffer(tempIp + "        " +  minCountMap.get(tempIp) + "        " + ipsPacketMap.get(tempIp));
                System.out.println(sb);
                writeToFile.add(sb.toString());
                count++;
                if(count == 100)
                {
                    break;
                }
            }
            if(count == 100)
            {
                break;
            }
        }
        writeToFile(writeToFile, error/totalFlows);
    }

    static void record() {
        Integer[] hashValues;
        int totPackets;
        for (int i = 0; i < totalFlows; i++) {
            hashValues = getHash(ips.get(i).hashCode());
            totPackets = ipsPacketMap.get(ips.get(i));
            ipHashMap.put(ips.get(i), hashValues);
                for (int j = 0; j < hashes; j++) {
                    String str = String.format("%32s", Integer.toBinaryString(hashValues[j])).replaceAll(" ", "0");
                    if(str.charAt(1) == '1') {
                        counters[j][Math.abs(hashValues[j]) % counterLength] = counters[j][Math.abs(hashValues[j]) % counterLength] + totPackets;
                    }
                    else {
                        counters[j][Math.abs(hashValues[j]) % counterLength] = counters[j][Math.abs(hashValues[j]) % counterLength] - totPackets;
                    }
                }
        }
    }

    static void writeToFile(List<String> lines, int error)
    {
        BufferedWriter outputFile = null;
        try {
            File file = new File("CounterSketch.txt");
            outputFile = new BufferedWriter(new FileWriter(file));
            outputFile.write(String.valueOf(error));
            outputFile.newLine();

            for (int i = 0; i < lines.size(); i++)
            {
                outputFile.write(lines.get(i));
                outputFile.newLine();
            }
        } catch ( IOException e ) {
        } finally {
            if ( outputFile != null ) {
                try {
                    outputFile.close();
                } catch (IOException e) {
                }
            }
        }
    }

    static Integer[] getHash(int hashId) {
        Integer[] hashValues = new Integer[hashes];
        for (int i = 0; i < hashes; i++) {
            hashValues[i] = hashId ^ randValue[i];
        }
        return hashValues;
    }
}
