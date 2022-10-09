package me.devoria.core.itemSystem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
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




    public static Collection<ItemStack> generate(String type, String level) throws FileNotFoundException {

        String dataFolder = Bukkit.getPluginManager().getPlugin("Devoria").getDataFolder().toString();

        Collection<ItemStack> items = new ArrayList<>();


        List<String> subPathList = null;
        String rarity;
        int randomNumber;

        Random randomNumbers = new Random();

        randomNumber = randomNumbers.ints(0, 101).findAny().getAsInt();

        if(randomNumber >= 50) {


            randomNumber = randomNumbers.ints(0, 101).findAny().getAsInt();

            if (randomNumber <= 5) {
                rarity = "mythic";
            } else if (randomNumber <= 15) {
                rarity = "legendary";
            } else if (randomNumber <= 30) {
                rarity = "epic";
            } else if (randomNumber <= 60) {
                rarity = "rare";
            } else {
                rarity = "common";
            }


            Path path = Paths.get(dataFolder + "/loot/15-20/" + rarity);

            System.out.println(path);

            try (Stream<Path> subPaths = Files.walk(path, 1)) {

                subPathList = subPaths.filter(Files::isRegularFile).map(Object::toString).collect(Collectors.toList());


            } catch (IOException e) {
                e.printStackTrace();
            }


            assert subPathList != null;
            Collections.shuffle(subPathList);


            try {
                FileInputStream inputStream = new FileInputStream(subPathList.get(0));
                Yaml yaml = new Yaml();
                ItemStack drop;
                Map<String, Object> attributes = yaml.load(inputStream);

                if (attributes.get("rarity").equals("common")) {
                    drop = UpdateItem.update(",fileName:"+attributes.get("file_name"));
                } else {
                    drop = MakeUnidentifiedItem.makeUnidentifiedItem(attributes.get("file_name"), attributes.get("rarity"), attributes.get("type"), attributes.get("level"));
                }

                items.add(drop);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        randomNumber = randomNumbers.ints(1, 7).findAny().getAsInt();
        for(int i = 0; i < randomNumber; i++) {
            ItemStack coin = new ItemStack(Material.EMERALD);
            items.add(coin);
        }

        return items;
    }
}





