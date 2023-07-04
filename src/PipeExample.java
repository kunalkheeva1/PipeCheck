import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PipeExample {
    public static void main(String[] args) throws IOException {
        // Create two pipes
        final ProcessBuilder builder = new ProcessBuilder();
        final Process process = builder.command("sh", "-c", "echo hi there").start();

        final InputStream inputStream = process.getInputStream();
        final OutputStream outputStream = process.getOutputStream();

        // Read the original message from the input stream
        StringBuilder message = new StringBuilder();
        int character;
        while ((character = inputStream.read()) != -1) {
            message.append((char) character);
        }

        // Modify the message by reversing the case of each character
        String modifiedMessage = reverseCase(message.toString());

        // Write the modified message to the output stream
        outputStream.write(modifiedMessage.getBytes());
        outputStream.flush();

        // Close the streams
        inputStream.close();
        outputStream.close();

        // Wait for the process to exit
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print the modified message received from the process
        System.out.println("Modified message received from the process: " + modifiedMessage);
    }

    private static String reverseCase(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isUpperCase(c)) {
                chars[i] = Character.toLowerCase(c);
            } else if (Character.isLowerCase(c)) {
                chars[i] = Character.toUpperCase(c);
            }
        }
        return new String(chars);
    }
}
