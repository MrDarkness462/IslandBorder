package com.github.zandy.islandborder.features.guis;

import com.github.zandy.bamboolib.gui.ClickAction;
import com.github.zandy.bamboolib.gui.GUI;
import com.github.zandy.bamboolib.gui.GUIItem;
import com.github.zandy.bamboolib.item.ItemBuilder;
import com.github.zandy.bamboolib.utils.BambooFile;
import com.github.zandy.bamboolib.utils.BambooUtils;
import com.github.zandy.bamboolib.versionsupport.material.Materials;
import com.github.zandy.islandborder.files.guis.ColorGUIFile;
import com.github.zandy.islandborder.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.*;

import static com.github.zandy.bamboolib.versionsupport.utils.BorderColor.*;
import static com.github.zandy.islandborder.files.guis.ColorGUIFile.ColorGUIEnum.INVENTORY_TYPE;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.COLOR_GUI_TITLE;
import static com.github.zandy.islandborder.files.languages.Languages.getLocaleFiles;
import static com.github.zandy.islandborder.files.languages.Languages.getPlayerLocale;

public class ColorGUI {
    private static ColorGUI instance = null;
    private final InventoryType inventoryType = INVENTORY_TYPE.getInventoryType();
    private final HashMap<String, Materials> materialsMap = new HashMap<>();
    private final HashMap<String, Integer> slotsMap = new HashMap<>();
    private final HashMap<String, String> pathMap = new HashMap<>();
    private final List<String> itemsPath = new ArrayList<>();

    public ColorGUI() {
        for (String s : ColorGUIFile.getInstance().getConfigurationSection("Slots").getKeys(false)) {
            String formatted = "Slots." + s.split("\\.")[0];
            if (!itemsPath.contains(formatted)) {
                itemsPath.add(formatted);
                slotsMap.put(formatted, ColorGUIFile.getInstance().getInt(formatSlot(formatted)) - 1);
                materialsMap.put(formatted, ColorGUIFile.getInstance().getMaterial(formatMaterial(formatted)));
                pathMap.put(formatted, "Slots.Color." + s.split("\\.")[0]);
            }
        }
        for (Map.Entry<String, BambooFile> map : getLocaleFiles().entrySet()) {
            BambooFile iso = map.getValue();
            if (map.getKey().equals("RO")) {
                iso.addDefault("Slots.Color.Red-Button.Name", "Selecteaza &cRosu");
                if (!iso.contains("Slots.Color.Blue-Button.Name")) iso.addDefault("Slots.Color.Empty-1.Name", " ");
                iso.addDefault("Slots.Color.Green-Button.Name", "Selecteaza &aVerde");
                if (!iso.contains("Slots.Color.Blue-Button.Name")) iso.addDefault("Slots.Color.Empty-2.Name", " ");
                iso.addDefault("Slots.Color.Blue-Button.Name", "Selecteaza &9Albastru");
            } else {
                iso.addDefault("Slots.Color.Red-Button.Name", "Select &cRed");
                if (!iso.contains("Slots.Color.Blue-Button.Name")) iso.addDefault("Slots.Color.Empty-1.Name", " ");
                iso.addDefault("Slots.Color.Green-Button.Name", "Select &aGreen");
                if (!iso.contains("Slots.Color.Blue-Button.Name")) iso.addDefault("Slots.Color.Empty-2.Name", " ");
                iso.addDefault("Slots.Color.Blue-Button.Name", "Select &9Blue");
            }
            iso.addDefault("Slots.Color.Red-Button.Lore", new ArrayList<>());
            if (iso.contains("Slots.Color.Empty-1.Name")) iso.addDefault("Slots.Color.Empty-1.Lore", new ArrayList<>());
            if (iso.contains("Slots.Color.Empty-2.Name")) iso.addDefault("Slots.Color.Empty-2.Lore", new ArrayList<>());
            iso.addDefault("Slots.Color.Green-Button.Lore", new ArrayList<>());
            iso.addDefault("Slots.Color.Blue-Button.Lore", new ArrayList<>());
            iso.copyDefaults();
            iso.save();
            for (String s : itemsPath) {
                boolean thorwError = false;
                String subPath = null;
                if (!iso.contains(formatPath(s) + ".Name")) {
                    thorwError = true;
                    subPath = ".Name";
                } else if (!iso.contains(formatPath(s) + ".Lore")) {
                    thorwError = true;
                    subPath = ".Lore";
                }
                if (thorwError) {
                    BambooUtils.consolePrint("&c----------------------------------------------------");
                    BambooUtils.consolePrint("&cISSUE FOUND IN ISLAND BORDER CONFIGURATION!!!");
                    BambooUtils.consolePrint("The path '&c" + formatPath(s) + subPath + "&f' is missing from '&c" + map.getValue().getName() + "&f' Language.");
                    BambooUtils.consolePrint("&cCorrect this issue or you will not receive support from the developer.");
                    BambooUtils.consolePrint("&c----------------------------------------------------");
                }
            }
        }
    }

    public void open(Player p) {
        UUID uuid = p.getUniqueId();
        PlayerData playerData = PlayerData.get(uuid);
        if (playerData.getBorderColor() == null) return;
        BambooFile iso = getLocaleFiles().get(getPlayerLocale().get(uuid));
        GUI gui = new GUI(p, inventoryType, COLOR_GUI_TITLE.getString(uuid));
        String color = iso.getString("Color." + BambooUtils.capitalizeFirstLetter(playerData.getBorderColor().name().toLowerCase()));
        for (String s : itemsPath) {
            ItemBuilder item = materialsMap.get(s).getItem().setDisplayName(iso.getString(pathMap.get(s) + ".Name").replace("[color]", color));
            if (iso.getStringList(s + ".Lore").size() > 0) item.setLore(iso.getStringList(pathMap.get(s) + ".Lore"));
            gui.addItem(new GUIItem(item.build(), slotsMap.get(s)).addClickAction(new ClickAction() {
                @Override
                public void onClick(GUIItem guiItem, GUI gui) {
                    switch (s.split("\\.")[1]) {
                        case "Red-Button": {
                            playerData.setBorderColor(RED);
                            p.closeInventory();
                            break;
                        }
                        case "Green-Button": {
                            playerData.setBorderColor(GREEN);
                            p.closeInventory();
                            break;
                        }
                        case "Blue-Button": {
                            playerData.setBorderColor(BLUE);
                            p.closeInventory();
                            break;
                        }
                    }
                }
            }));
        }
        gui.open();
    }

    public static ColorGUI getInstance() {
        if (instance == null) instance = new ColorGUI();
        return instance;
    }

    private String formatMaterial(String string) {
        return string + ".Material";
    }

    private String formatSlot(String string) {
        return string + ".Slot";
    }

    private String formatPath(String string) {
        return pathMap.get(string);
    }
}
