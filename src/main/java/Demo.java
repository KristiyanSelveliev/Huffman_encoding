
public class Demo {

    public static void main(String ...args){
        String str = "Huffman coding is a data compression algorithm.";

        Result result = Huffman.encoding(str);

        String decoded = Huffman.decode(result.getEncoded(), result.getRoot());
        System.out.println(result);
        System.out.println("Decoded string is :");
        System.out.println(decoded);

    }
}
