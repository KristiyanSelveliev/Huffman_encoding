import lombok.Builder;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {


    public static Result encoding(String str){

       Map<Character, String> encodedCharacters = getMapOfEncodings(str);
       return Result.builder()
               .original(str)
               .root(getTreeRoot(str))
               .encodings(encodedCharacters)
               .encoded(encodeString(str, encodedCharacters))
               .build();
    }

    private static Node getTreeRoot(String str){
        Map<Character, Integer> map = countFrequencies(str);
        PriorityQueue<Node> priorityQueue = buildPriorityQueue(map);
        return buildHuffmanTree(priorityQueue);
    }

    private static Map<Character, String> getMapOfEncodings(String str){
        Map<Character, Integer> map = countFrequencies(str);
        PriorityQueue<Node> priorityQueue = buildPriorityQueue(map);
        Node root = buildHuffmanTree(priorityQueue);
        return getEncodings(root);
    }

    private static Map<Character, Integer> countFrequencies(String str){
        Map<Character, Integer> map = new HashMap<>();

        for(char i : str.toCharArray()){
            if(map.containsKey(i)){
                map.put(i, map.get(i) + 1);
            }else{
                map.put(i, 1);
            }
        }
       return map;
    }

    private static PriorityQueue<Node> buildPriorityQueue(Map<Character, Integer> map){

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getFrequency));
        for(Character ch : map.keySet()){
            queue.add(Node.builder()
                    .character(ch)
                    .frequency(map.get(ch))
                    .build());
        }
        return queue;
    }

    private static Node buildHuffmanTree(PriorityQueue<Node> priorityQueue){
        while(priorityQueue.size() > 1){
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();

            int frequency = left.frequency + right.frequency;

            Node root  = new Node('\0', frequency, left, right);
            priorityQueue.add(root);
        }
        return priorityQueue.poll();
    }

    private static Map<Character, String> getEncodings(Node root){
        Map<Character,String> map = new HashMap<>();
        fillMap(root, map, "");
        return map;
    }

    private static void fillMap(Node node, Map<Character, String> map, String str) {
        if(node == null){
            return;
        }
        if(node.getCharacter() != '\0'){
            map.put(node.getCharacter(), str);
            return;
        }

        fillMap(node.getLeft(), map, str + "0");
        fillMap(node.getRight(), map, str + "1");
    }


    private static String encodeString(String str, Map<Character, String> map){
        StringBuilder sb = new StringBuilder();
        for(char ch : str.toCharArray()){
            sb.append(map.get(ch));
        }
        return sb.toString();
    }

    public static String decode(String encoded, Node root) {
        StringBuilder result = new StringBuilder();

        Node traversed = root;
        for(int i = 0; i < encoded.length(); i++){

            if(encoded.charAt(i) == '0'){
                traversed = traversed.getLeft();
            }

            if(encoded.charAt(i) == '1'){
                traversed = traversed.getRight();
            }

           if(traversed.getCharacter() != '\0'){
               result.append(traversed.getCharacter());
               traversed = root;
           }
        }
        return result.toString();
    }

    @Getter
    @Builder
     static class Node{
        private char character;
        private int frequency;
        private Node left;
        private Node right;
    }
}
