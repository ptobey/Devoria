package me.devoria.core.itemSystem;

import org.bukkit.Bukkit;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenerateLoot {




    public static Map<String, Object> generate(String type, int weight, String level) throws FileNotFoundException {

        String dataFolder = Bukkit.getPluginManager().getPlugin("Devoria").getDataFolder().toString();

        List<String> subPathList = null;
        ArrayList<String> itemTypes = new ArrayList<>();
        String rarity = "common";

        itemTypes.add("bows");
        itemTypes.add("swords");


        if (type.equals("huntsman")) {
            itemTypes.add("bows");
        } else if (type.equals("knight")) {
            itemTypes.add("swords");
        }

        Collections.shuffle(itemTypes);

        Random randomNumbers = new Random();

        int randomNumber = randomNumbers.ints(0, 101).findAny().getAsInt();

        if (randomNumber == 0) {
            rarity = "mythic";
        }
        if (randomNumber > 0 && randomNumber <= 10) {
            rarity = "legendary";
        }
        if (randomNumber > 10 && randomNumber <= 30) {
            rarity = "epic";
        }
        if (randomNumber > 30 && randomNumber <= 60) {
            rarity = "rare";
        }
        if (randomNumber > 60) {
            rarity = "common";
        }

        Path path = Paths.get(dataFolder + "/loot_tables/15-20/" + itemTypes.get(0) + "/" + rarity);

        try(Stream<Path> subPaths= Files.walk(path,1)) {

            subPathList=subPaths.filter(Files :: isRegularFile)
                    .map(Object::toString)
                    .collect(Collectors.toList());


        } catch (IOException e) {
            e.printStackTrace();
        }


        assert subPathList != null;
        Collections.shuffle(subPathList);


        try {
            FileInputStream inputStream = new FileInputStream(subPathList.get(0));
            Yaml yaml = new Yaml();
            return yaml.load(inputStream);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }
}





