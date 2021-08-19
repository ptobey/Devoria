package me.devoria.core;


import org.bukkit.Bukkit;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;


public class YMLParser {


    String dataFolder = Bukkit.getPluginManager().getPlugin("Devoria").getDataFolder().toString();


    public static void main(String[] args) {

    }

    public Map<String, Object> parse(String name) throws FileNotFoundException {
        System.out.println(dataFolder+"/items/"+name+".yml");

        FileInputStream inputStream = new FileInputStream(dataFolder+"/items/"+name+".yml");
        Yaml yaml = new Yaml();
        return yaml.load(inputStream);

}

}
