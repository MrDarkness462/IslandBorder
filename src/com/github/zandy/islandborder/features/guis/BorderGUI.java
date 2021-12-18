package com.github.zandy.islandborder.features.guis;

import com.github.zandy.bamboolib.gui.ClickAction;
import com.github.zandy.bamboolib.gui.GUI;
import com.github.zandy.bamboolib.gui.GUIItem;
import com.github.zandy.bamboolib.item.ItemBuilder;
import com.github.zandy.bamboolib.utils.BambooFile;
import com.github.zandy.bamboolib.utils.BambooUtils;
import com.github.zandy.bamboolib.versionsupport.material.Materials;
import com.github.zandy.bamboolib.versionsupport.utils.BorderColor;
import com.github.zandy.islandborder.features.borders.Border;
import com.github.zandy.islandborder.files.guis.BorderGUIFile;
import com.github.zandy.islandborder.files.languages.Languages;
import com.github.zandy.islandborder.files.languages.Languages.LanguageEnum;
import com.github.zandy.islandborder.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.*;

public class BorderGUI {
    private static BorderGUI instance = null;
    private final InventoryType inventoryType = BorderGUIFile.BorderGUIEnum.INVENTORY_TYPE.getInventoryType();
    private final HashMap<String, Materials> materialsMap = new HashMap<>();
    private final HashMap<String, Integer> slotsMap = new HashMap<>();
    private final HashMap<String, String> pathMap = new HashMap<>();
    private final HashMap<BorderColor, Materials> borderColorMaterialsMap = new HashMap<>();
    private final List<String> itemsPath = new ArrayList<>();

    public BorderGUI() {
        for (String s : BorderGUIFile.getInstance().getConfigurationSection("Slots").getKeys(false)) {
            String formatted = "Slots." + s.split("\\.")[0];
            if (!itemsPath.contains(formatted)) {
                itemsPath.add(formatted);
                slotsMap.put(formatted, BorderGUIFile.getInstance().getInt(formatSlot(formatted)) - 1);
                pathMap.put(formatted, "Slots.Border." + s.split("\\.")[0]);
                if (!s.contains("Color-Button")) materialsMap.put(formatted, BorderGUIFile.getInstance().getMaterial(formatMaterial(formatted)));
            }
            borderColorMaterialsMap.put(BorderColor.RED, BorderGUIFile.BorderGUIEnum.SLOTS_COLOR_BUTTON_RED_MATERIAL.getMaterial());
            borderColorMaterialsMap.put(BorderColor.GREEN, BorderGUIFile.BorderGUIEnum.SLOTS_COLOR_BUTTON_GREEN_MATERIAL.getMaterial());
            borderColorMaterialsMap.put(BorderColor.BLUE, BorderGUIFile.BorderGUIEnum.SLOTS_COLOR_BUTTON_BLUE_MATERIAL.getMaterial());
        }
        for (Map.Entry<String, BambooFile> map : Languages.getInstance().getLocaleFiles().entrySet()) {
            BambooFile iso = map.getValue();
            if (map.getKey().equals("RO")) {
                iso.addDefault("Slots.Border.Enable-Button.Name", "&aPorneste Border-ul");
                if (!iso.contains("Slots.Border.Color-Button.Name")) iso.addDefault("Slots.Border.Empty-1.Name", " ");
                iso.addDefault("Slots.Border.Disable-Button.Name", "&aOpreste Border-ul");
                if (!iso.contains("Slots.Border.Color-Button.Name")) iso.addDefault("Slots.Border.Empty-2.Name", " ");
                iso.addDefault("Slots.Border.Color-Button.Name", "&7Culoarea border-ului: [color]");
            } else {
                iso.addDefault("Slots.Border.Enable-Button.Name", "&aEnable Border");
                if (!iso.contains("Slots.Border.Color-Button.Name")) iso.addDefault("Slots.Border.Empty-1.Name", " ");
                iso.addDefault("Slots.Border.Disable-Button.Name", "&aDisable Border");
                if (!iso.contains("Slots.Border.Color-Button.Name")) iso.addDefault("Slots.Border.Empty-2.Name", " ");
                iso.addDefault("Slots.Border.Color-Button.Name", "&7Border Color: [color]");
            }
            iso.addDefault("Slots.Border.Enable-Button.Lore", new ArrayList<>());
            if (iso.contains("Slots.Border.Empty-1.Name")) iso.addDefault("Slots.Border.Empty-1.Lore", new ArrayList<>());
            if (iso.contains("Slots.Border.Empty-2.Name")) iso.addDefault("Slots.Border.Empty-2.Lore", new ArrayList<>());
            iso.addDefault("Slots.Border.Disable-Button.Lore", new ArrayList<>());
            iso.addDefault("Slots.Border.Color-Button.Lore", new ArrayList<>());
            iso.copyDefaults();
            iso.save();
            for (String s : itemsPath) {
                boolean throwError = false;
                String subPath = null;
                if (!iso.contains(formatPath(s) + ".Name")) {
                    throwError = true;
                    subPath = ".Name";
                } else if (!iso.contains(formatPath(s) + ".Lore")) {
                    throwError = true;
                    subPath = ".Lore";
                }
                if (throwError) {
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
        BambooFile iso = Languages.getInstance().getLocaleFiles().get(Languages.getInstance().getPlayerLocale().get(uuid));
        GUI gui = new GUI(p, inventoryType, LanguageEnum.BORDER_GUI_TITLE.getString(uuid));
        String color = iso.getString("Color." + BambooUtils.capitalizeFirstLetter(playerData.getBorderColor().name().toLowerCase()));
        for (String s : itemsPath) {
            Materials finalMaterial = materialsMap.get(s);
            if (s.contains("Color-Button")) finalMaterial = borderColorMaterialsMap.get(playerData.getBorderColor());
            ItemBuilder item = finalMaterial.getItem().setDisplayName(iso.getString(pathMap.get(s) + ".Name").replace("[color]", color));
            if (iso.getStringList(s + ".Lore").size() > 0) item.setLore(iso.getStringList(pathMap.get(s) + ".Lore"));
            gui.addItem(new GUIItem(item.build(), slotsMap.get(s)).addClickAction(new ClickAction() {
                @Override
                public void onClick(GUIItem guiItem, GUI gui) {
                    switch (s.split("\\.")[1]) {
                        case "Enable-Button": {
                            Border.getInstance().setState(uuid, true);
                            p.closeInventory();
                            break;
                        }
                        case "Disable-Button": {
                            Border.getInstance().setState(uuid, false);
                            p.closeInventory();
                            break;
                        }
                        case "Color-Button": {
                            if (p.hasPermission("isborder.color.gui") || p.hasPermission("isborder.*")) ColorGUI.getInstance().open(p);
                            else p.sendMessage(LanguageEnum.NO_PERMISSION_COMMAND.getString(uuid));
                            break;
                        }
                    }
                }
            }));
        }
        gui.open();
    }

    public static BorderGUI getInstance() {
        if (instance == null) instance = new BorderGUI();
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
