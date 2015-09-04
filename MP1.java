import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MP1 {
    Random generator;
    String userName;
    String inputFileName;
    String delimiters = " \t,;.?!-:@[](){}_*/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};
	private BufferedReader br;
	private PrintWriter outputFile;


    void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(seed.toLowerCase().trim().getBytes());
        byte[] seedMD5 = messageDigest.digest();

        long longSeed = 0;
        for (int i = 0; i < seedMD5.length; i++) {
            longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
        }

        this.generator = new Random(longSeed);
    }

    Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        this.initialRandomGenerator(this.userName);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public MP1(String userName, String inputFileName) {
        this.userName = userName;
        this.inputFileName = inputFileName;
    }

    public String[] process() throws Exception {
        String[] ret = new String[20];
                       
         FileReader fr = new FileReader(this.inputFileName);
     	 outputFile = new PrintWriter(new FileWriter("./output.txt"));
         br = new BufferedReader(fr);
         List<String> allLines=new ArrayList<String>();
         List<String> linesListIndexed = new ArrayList<String>();
         String currentLine;
         final HashMap<String, Integer> map = new HashMap<>();
         
         while((currentLine = br.readLine())!=null)
         {
        	 allLines.add(currentLine);
         }
         
         for (int i : getIndexes()) {
             String line = allLines.get(i).toLowerCase().trim();
             StringTokenizer st = new StringTokenizer(line, delimiters);
             while (st.hasMoreTokens()) {
                 linesListIndexed.add(st.nextToken());                
             }
         }
         
         Set<String> stopWordsSet = new HashSet<>(Arrays.asList(stopWordsArray));
         linesListIndexed.removeAll(stopWordsSet);
                 
         for(String word: linesListIndexed)
         {
        	 if(map.containsKey(word))
        	 {
        		 map.put(word, map.get(word)+1);
        	 }
        	 else
        	 {
        		 map.put(word, 1);
        	 }
         }
         
         List<String> finalWords = new ArrayList<>();
         
         for(String key: map.keySet())
         {
        	 finalWords.add(key);        	
         }
                  
         Collections.sort(finalWords, new Comparator<String>() {    	 

			@Override
			public int compare(String o1, String o2) {
				
				if(map.get(o1)<map.get(o2))
				{
					return 1;
				}
				else if (map.get(o1)>map.get(o2))
				{
					return -1;					
				}
				int cmp = o1.compareTo(o2);
                return cmp;
			}      	        	 
		});
         
         for (int j = 0; j < ret.length; j++) {
             ret[j] = finalWords.get(j);    
             outputFile.println(ret[j]);
         }
        outputFile.close();
        return ret;
    }
    
    public static void main(String[] args) throws Exception {
        
    	if (args.length < 1)
        {
            System.out.println("MP1 <User ID>");
        }
        else 
        {
            String userName = args[0];
            String inputFileName = "./input.txt";
            MP1 mp = new MP1(userName, inputFileName);
            String[] topItems = mp.process();
            for (String item: topItems){
                System.out.println(item);
        }
            
    }
    }
    
    
    
}
