import java.io.*;
import java.util.*;


public class CountMin {

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

    // for storing the min count for lookup for each lookup
    static Map<String, Integer> minCountMap;


    public CountMin(int numFlows, int numHashes, int w) {
        totalFlows = numFlows;
        counters = new int[numHashes][w];
        hashes = numHashes;
        counterLength = w;
        randValue = new int[numHashes];
        ips = new ArrayList<>();
        ipsPacketMap = new HashMap<>();
        Random rd = new Random();
        randValue = new int[numHashes];
        ipHashMap = new HashMap<>();
        minCountMap = new HashMap<>();

        for (int i = 0; i < numHashes; i++) {
            randValue[i] = 0 + rd.nextInt(Integer.MAX_VALUE);// min + rd.nextInt(maxValue)
        }
    }

    public static void callCountMin()
    {
        callCountMin(3, 3000);
    }
    public static void callCountMin(int numHashes, int w) {
        int totFlows;
        try {

            File f = new File("project3input.txt");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";
            String [] temp;

            totFlows = Integer.parseInt(b.readLine());
            CountMin countMin = new CountMin(totFlows, numHashes, w);

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

    static void record() {
        Integer[] hashValues;
        int totPackets;
        for (int i = 0; i < totalFlows; i++) {
            hashValues = getHash(Math.abs(ips.get(i).hashCode()));
            ipHashMap.put(ips.get(i), hashValues);
            totPackets = ipsPacketMap.get(ips.get(i));
            for (int m = 0; m < totPackets; m++) {
                for (int j = 0; j < hashes; j++) {
                    counters[j][hashValues[j] % counterLength] = counters[j][hashValues[j] % counterLength] + 1;
                }
            }
        }
    }


    static void writeToFile(List<String> lines, int error)
    {
        BufferedWriter outputFile = null;
        try {
            File file = new File("CountMin.txt");
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

    static void lookOriginalFlows() {

        Integer[] hashVal;
        int min;

        for (int i = 0; i < totalFlows; i++) {
            hashVal = ipHashMap.get(ips.get(i));
            min = Integer.MAX_VALUE;
            for (int j = 0; j < hashes; j++) {
                min = Math.min(min, counters[j][hashVal[j] % counterLength]);
            }
            minCountMap.put(ips.get(i), min);
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
        Set<Integer> keys = new LinkedHashSet<>(estimatedSizeIps.keySet());
        int count = 0;

        String tempIp;
        List<String>
                writeToFile = new ArrayList<>();
        StringBuffer sb;
        System.out.println(error/totalFlows);
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

    static Integer[] getHash(int hashId) {
        Integer[] hashValues = new Integer[hashes];
        for (int i = 0; i < hashes; i++) {
            hashValues[i] = hashId ^ randValue[i];
        }
        return hashValues;
    }
}
