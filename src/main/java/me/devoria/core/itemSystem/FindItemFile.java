package me.devoria.core.itemSystem;


import me.devoria.core.Core;
import org.bukkit.Bukkit;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;


public class FindItemFile {

    public static Map<String, String> parse(String folder, String name) throws FileNotFoundException {


        FileInputStream inputStream = new FileInputStream(Core.dataFolder+"/"+folder+"/"+name+".yml");
        Yaml yaml = new Yaml();
        return yaml.load(inputStream);

}

}
