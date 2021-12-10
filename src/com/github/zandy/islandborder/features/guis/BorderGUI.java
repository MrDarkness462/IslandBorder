package com.github.zandy.islandborder.features.guis;

import com.github.zandy.islandborder.files.languages.Languages;
import com.github.zandy.islandborder.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.magenpurp.api.gui.ClickAction;
import org.magenpurp.api.gui.GUI;
import org.magenpurp.api.gui.GuiItem;
import org.magenpurp.api.item.ItemBuilder;
import org.magenpurp.api.utils.FileManager;
import org.magenpurp.api.versionsupport.BorderColor;
import org.magenpurp.api.versionsupport.materials.Materials;

import java.util.*;

import static com.github.zandy.islandborder.Main.getBorder;
import static com.github.zandy.islandborder.Main.getColorGUI;
import static com.github.zandy.islandborder.files.guis.BorderGUIFile.BorderGUIEnum.*;
import static com.github.zandy.islandborder.files.guis.BorderGUIFile.getBorderGUIFile;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.BORDER_GUI_TITLE;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.NO_PERMISSION_COMMAND;
import static com.github.zandy.islandborder.files.languages.Languages.getLocaleFiles;
import static com.github.zandy.islandborder.files.languages.Languages.getPlayerLocale;
import static org.magenpurp.api.MagenAPI.getVersionSupport;
import static org.magenpurp.api.MagenAPI.print;
import static org.magenpurp.api.versionsupport.BorderColor.*;

public class BorderGUI {
    private final InventoryType inventoryType = INVENTORY_TYPE.getInventoryType();
    private final HashMap<String, Materials> materialsMap = new HashMap<>();
    private final HashMap<String, Integer> slotsMap = new HashMap<>();
    private final HashMap<String, String> pathMap = new HashMap<>();
    private final HashMap<BorderColor, Materials> borderColorMaterialsMap = new HashMap<>();
    private final List<String> itemsPath = new ArrayList<>();

    public BorderGUI() {
        for (String s : getBorderGUIFile().getConfigurationSection("Slots").getKeys(false)) {
            String formatted = "Slots." + s.split("\\.")[0];
            if (!itemsPath.contains(formatted)) {
                itemsPath.add(formatted);
                slotsMap.put(formatted, getBorderGUIFile().getInt(formatSlot(formatted)) - 1);
                pathMap.put(formatted, "Slots.Border." + s.split("\\.")[0]);
                if (!s.contains("Color-Button")) materialsMap.put(formatted, getBorderGUIFile().getMaterial(formatMaterial(formatted)));
            }
            borderColorMaterialsMap.put(RED, SLOTS_COLOR_BUTTON_RED_MATERIAL.getMaterial());
            borderColorMaterialsMap.put(GREEN, SLOTS_COLOR_BUTTON_GREEN_MATERIAL.getMaterial());
            borderColorMaterialsMap.put(BLUE, SLOTS_COLOR_BUTTON_BLUE_MATERIAL.getMaterial());
        }
        for (Map.Entry<String, FileManager> map : getLocaleFiles().entrySet()) {
            FileManager iso = map.getValue();
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
                    print("&c----------------------------------------------------");
                    print("&cISSUE FOUND IN ISLAND BORDER CONFIGURATION!!!");
                    print("The path '&c" + formatPath(s) + subPath + "&f' is missing from '&c" + map.getValue().getName() + "&f' Language.");
                    print("&cCorrect this issue or you will not receive support from the developer.");
                    print("&c----------------------------------------------------");
                }
            }
        }
    }

    public void open(Player p) {
        UUID uuid = p.getUniqueId();
        PlayerData playerData = PlayerData.get(uuid);
        if (playerData.getBorderColor() == null) return;
        FileManager iso = getLocaleFiles().get(getPlayerLocale().get(uuid));
        GUI gui = new GUI(p, inventoryType, BORDER_GUI_TITLE.getString(uuid));
        String color = iso.getString("Color." + getVersionSupport().makeFirstLetterUppercase(playerData.getBorderColor().name().toLowerCase()));
        for (String s : itemsPath) {
            Materials finalMaterial = materialsMap.get(s);
            if (s.contains("Color-Button")) finalMaterial = borderColorMaterialsMap.get(playerData.getBorderColor());
            ItemBuilder item = finalMaterial.getItem().setDisplayName(iso.getString(pathMap.get(s + ".Name")).replace("[color]", color));
            if (iso.getStringList(s + ".Lore").size() > 0) item.setLore(iso.getStringList(pathMap.get(s + ".Lore")));
            gui.addItem(new GuiItem(item.build(), slotsMap.get(s)).addClickAction(new ClickAction() {
                @Override
                public void onClick(GuiItem guiItem, GUI gui) {
                    switch (s.split("\\.")[1]) {
                        case "Enable-Button": {
                            getBorder().setState(uuid, true);
                            break;
                        }
                        case "Disable-Button": {
                            getBorder().setState(uuid, false);
                            break;
                        }
                        case "Color-Button": {
                            if (p.hasPermission("isborder.color.gui") || p.hasPermission("isborder.*")) getColorGUI().open(p);
                            else p.sendMessage(NO_PERMISSION_COMMAND.getString(uuid));
                            break;
                        }
                    }
                }
            }));
        }
        gui.open();
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
