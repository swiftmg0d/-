package KumanovskiDijalekt;

import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
import java.util.stream.IntStream;

        class MapEntry<K extends Comparable<K>,E> implements Comparable<K> {

            // Each Vezbi2022_2023.MapEntry object is a pair consisting of a key (a Comparable
            // object) and a value (an arbitrary object).
            K key;
            E value;

            public MapEntry (K key, E val) {
                this.key = key;
                this.value = val;
            }

            public int compareTo (K that) {
                // Compare this map entry to that map entry.
                @SuppressWarnings("unchecked")
                MapEntry<K,E> other = (MapEntry<K,E>) that;
                return this.key.compareTo(other.key);
            }

            public String toString () {
                return "<" + key + "," + value + ">";
            }
        }

        class SLLNode<E> {
            protected E element;
            protected SLLNode<E> succ;

            public SLLNode(E elem, SLLNode<E> succ) {
                this.element = elem;
                this.succ = succ;
            }

            @Override
            public String toString() {
                return element.toString();
            }
        }

        class CBHT<K extends Comparable<K>, E> {

            // An object of class Vezbi2022_2023.CBHT is a closed-bucket hash table, containing
            // entries of class Vezbi2022_2023.MapEntry.
            private
            SLLNode<MapEntry<K,E>>[] buckets;

            @SuppressWarnings("unchecked")
            public CBHT(int m) {
                // Construct an empty Vezbi2022_2023.CBHT with m buckets.
                buckets = (SLLNode<MapEntry<K,E>>[]) new SLLNode[m];
            }

            private int hash(K key) {
                return Math.abs(key.hashCode()) % buckets.length;
            }

            public SLLNode<MapEntry<K,E>> search(K targetKey) {
                // Find which if any node of this Vezbi2022_2023.CBHT contains an entry whose key is
                // equal
                // to targetKey. Return a link to that node (or null if there is none).
                int b = hash(targetKey);
                for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
                    if (targetKey.equals(((MapEntry<K, E>) curr.element).key))
                        return curr;
                }
                return null;
            }

            public void insert(K key, E val) {		// Insert the entry <key, val> into this Vezbi2022_2023.CBHT.
                MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
                int b = hash(key);
                for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
                    if (key.equals(((MapEntry<K, E>) curr.element).key)) {
                        // Make newEntry replace the existing entry ...
                        curr.element = newEntry;
                        return;
                    }
                }
                // Insert newEntry at the front of the 1WLL in bucket b ...
                buckets[b] = new SLLNode<MapEntry<K,E>>(newEntry, buckets[b]);
            }

            public void delete(K key) {
                // Delete the entry (if any) whose key is equal to key from this Vezbi2022_2023.CBHT.
                int b = hash(key);
                for (SLLNode<MapEntry<K,E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {
                    if (key.equals(((MapEntry<K,E>) curr.element).key)) {
                        if (pred == null)
                            buckets[b] = curr.succ;
                        else
                            pred.succ = curr.succ;
                        return;
                    }
                }
            }

            public String toString() {
                String temp = "";
                for (int i = 0; i < buckets.length; i++) {
                    temp += i + ":";
                    for (SLLNode<MapEntry<K,E>> curr = buckets[i]; curr != null; curr = curr.succ) {
                        temp += curr.element.toString() + " ";
                    }
                    temp += "\n";
                }
                return temp;
            }

        }

        public class KumanovskiDijalekt {
            public static void main (String[] args) throws IOException {

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        System.in));
                int N = Integer.parseInt(br.readLine());

                String rechnik[]=new String[N];
                for(int i=0;i<N;i++){
                    rechnik[i]=br.readLine();
                }

                String tekst=br.readLine();
                CBHT<String,String>stringStringCBHT=new CBHT<>(2*N);
                IntStream.range(0,rechnik.length).forEach(i->{
                    String splited[]=rechnik[i].split(" ");
                    stringStringCBHT.insert(splited[0],splited[1]);
                });
                if(N==0){
                    System.out.println(tekst);
                    return;
                }
                String splited[]=tekst.split(" ");
                for(int i=0;i<splited.length;i++){
                    String s=null;
                    if(Character.isLetter(splited[i].charAt(splited[i].length()-1))){
                        s=splited[i];
                        SLLNode<MapEntry<String, String>> mapSLLNode=stringStringCBHT.search(s.toLowerCase());
                            if(mapSLLNode!=null){
                                    if(Character.isUpperCase(splited[i].charAt(0))){
                                        String k=mapSLLNode.element.value;
                                        splited[i]=Character.toUpperCase(k.charAt(0))+k.substring(1);
                                    }
                                    else{
                                       splited[i]=mapSLLNode.element.value;
                                    }

                            }
                    }
                    else{
                        s=splited[i].substring(0,splited[i].length()-1);
                        SLLNode<MapEntry<String, String>> mapSLLNode=stringStringCBHT.search(s.toLowerCase());
                        if(mapSLLNode!=null){
                            if(Character.isUpperCase(splited[i].charAt(0))){
                                String k=mapSLLNode.element.value;
                                splited[i]=Character.toUpperCase(k.charAt(0))+k.substring(1)+splited[i].charAt(splited[i].length()-1);
                            }
                                splited[i]=mapSLLNode.element.value+splited[i].charAt(splited[i].length()-1);
                        }
                    }

                }
                for(int i=0;i<splited.length;i++){
              System.out.print(splited[i]+" ");
             }
            }
        }
