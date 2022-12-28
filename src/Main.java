import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Input;
import handler.Handler;

public final class Main {
    private Main() {
    }

    /**
     * Runs every test
     * @param args args[0] is the input file's path and args[1] is the output file's path
     */
    public static void main(final String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode output = objectMapper.createArrayNode();

        File in = new File(args[0]);
        File out = new File(args[1]);

        /* Reading the input from the input file */
        Input input;
        try {
            input = objectMapper.readValue(in, Input.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Handler.handle(input, output);

        /* Writing the output to the output file */
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        try {
            objectWriter.writeValue(out, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
