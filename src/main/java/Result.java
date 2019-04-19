import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class Result {

    private String original;
    private String encoded;
    private Huffman.Node root;
    private Map<Character, String> encodings;

    @Override
    public String toString() {
        return "Result{" +
                "original='" + original + '\'' + "\n" +
                ", encoded='" + encoded + '\'' + "\n" +
                ", encodings=" + encodings + "\n" +
                '}';
    }
}
