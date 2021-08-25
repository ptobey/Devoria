package me.devoria.core.itemSystem;


import org.bukkit.Bukkit;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;


public class FindItemFile {

    public static Map<String, Object> parse(String name) throws FileNotFoundException {
        String dataFolder = Bukkit.getPluginManager().getPlugin("Devoria").getDataFolder().toString();

        FileInputStream inputStream = new FileInputStream(dataFolder+"/items/"+name+".yml");
        Yaml yaml = new Yaml();
        return yaml.load(inputStream);

}

}
