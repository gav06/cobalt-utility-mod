package me.gav06.cobalt.api.util;

import me.gav06.cobalt.Cobalt;

import java.io.*;
import java.util.ArrayList;

// to save and load shit

public class FileHandler {

    private static File module_file = new File("cobalt_modules.txt");

    public static ArrayList<String> array = new ArrayList<>();

    public static void setup() {
        try {
            if (!module_file.exists()) {
                module_file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadModulesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(module_file))) {
            while (reader.ready()) {
                array.add(reader.readLine());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String s : array) {
            Cobalt.moduleManager.getModuleByName(s).toggle();
        }
    }

    public static void saveModulesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(module_file))) {
            for (String s : Cobalt.moduleManager.getModulesByName()) {
                if (Cobalt.moduleManager.getModuleByName(s).getState()) {
                    writer.write(s);
                    writer.newLine();
                }
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
