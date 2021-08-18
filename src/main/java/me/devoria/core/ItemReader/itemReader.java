package me.devoria.core.ItemReader;

import java.io.*;
import java.util.List;

public class itemReader {

    public static List reader (String filename) throws Exception{
        File file;
        List item = null;
        InputStream is = itemReader.class.getResourceAsStream(filename);
        InputStreamReader isr = new InputStreamReader(is);
        try (BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                item.add(line);

            }
            br.close();
        }catch (IOException e)
        {
            System.out.println(" file don't exist");
        }
        isr.close();
        isr.close();

        return item;
    }
}
