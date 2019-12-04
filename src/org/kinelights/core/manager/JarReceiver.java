package org.kinelights.core.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JarReceiver {

    private BufferedReader reader;

    public int getBpm(String fileName) {
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "/Users/joeltang/Documents/JavaProjects/pact55/audio/BPM_PAN4_v2.jar", fileName);
        pb.directory(new File("/usr/bin/"));

        try {
            Process p = pb.start();
            int res = logStreamReader(p.getInputStream());
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int logStreamReader(InputStream is) {
        this.reader = new BufferedReader(new InputStreamReader(is));
        try {
            String line = reader.readLine();
            System.out.println(line);
            reader.close();
            float fl = Float.parseFloat(line);
            return (int) fl;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
