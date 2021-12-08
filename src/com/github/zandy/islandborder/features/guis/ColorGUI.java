package com.github.zandy.islandborder.features.guis;

import com.github.zandy.islandborder.player.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.magenpurp.api.gui.ClickAction;
import org.magenpurp.api.gui.GUI;
import org.magenpurp.api.gui.GuiItem;
import org.magenpurp.api.item.ItemBuilder;
import org.magenpurp.api.utils.FileManager;
import org.magenpurp.api.versionsupport.materials.Materials;

import java.util.*;

import static com.github.zandy.islandborder.files.guis.ColorGUIFile.ColorGUIEnum.INVENTORY_TYPE;
import static com.github.zandy.islandborder.files.guis.ColorGUIFile.getColorGUIFile;
import static com.github.zandy.islandborder.files.languages.Languages.LanguageEnum.COLOR_GUI_TITLE;
import static com.github.zandy.islandborder.files.languages.Languages.getLocaleFiles;
import static com.github.zandy.islandborder.files.languages.Languages.getPlayerLocale;
import static org.magenpurp.api.MagenAPI.getVersionSupport;
import static org.magenpurp.api.MagenAPI.print;
import static org.magenpurp.api.versionsupport.BorderColor.*;

public class ColorGUI {
    private final InventoryType inventoryType = INVENTORY_TYPE.getInventoryType();
    private final HashMap<String, Materials> materialsMap = new HashMap<>();
    private final HashMap<String, Integer> slotsMap = new HashMap<>();
    private final HashMap<String, String> pathMap = new HashMap<>();
    private final List<String> itemsPath = new ArrayList<>();

    public ColorGUI() {
        for (String s : getColorGUIFile().getConfigurationSection("Slots").getKeys(false)) {
            String formatted = "Slots." + s.split("\\.")[0];
            if (!itemsPath.contains(formatted)) {
                itemsPath.add(formatted);
                slotsMap.put(formatted, getColorGUIFile().getInt(formatSlot(formatted)) - 1);
                materialsMap.put(formatted, getColorGUIFile().getMaterial(formatMaterial(formatted)));
                pathMap.put(formatted, "Slots.Color." + s.split("\\.")[0]);
            }
        }
        for (Map.Entry<String, FileManager> map : getLocaleFiles().entrySet()) {
            FileManager iso = map.getValue();
            if (map.getKey().equals("RO")) {
                iso.addDefault("Slots.Color.Red-Button.Name", "Selecteaza &cRosu");
                if (iso.contains("Slots.Color.Blue-Button.Name")) iso.addDefault("Slots.Color.Empty-1.Name", " ");
                iso.addDefault("Slots.Color.Green-Button.Name", "Selecteaza &aVerde");
                if (iso.contains("Slots.Color.Blue-Button.Name")) iso.addDefault("Slots.Color.Emtpty-2.Name", " ");
                iso.addDefault("Slots.Color.Blue-Button.Name", "Selecteaza &9Albastru");
            } else {
                iso.addDefault("Slots.Color.Red-Button.Name", "Select &cRed");
                if (iso.contains("Slots.Color.Blue-Button.Name")) iso.addDefault("Slots.Color.Empty-1.Name", " ");
                iso.addDefault("Slots.Color.Green-Button.Name", "Select &aGreen");
                if (iso.contains("Slots.Color.Blue-Button.Name")) iso.addDefault("Slots.Color.Emtpty-2.Name", " ");
                iso.addDefault("Slots.Color.Blue-Button.Name", "Select &9Blue");
            }
            iso.addDefault("Slots.Color.Red-Button.Lore", new ArrayList<>());
            if (!iso.contains("Slots.Color.Blue-Button.Name")) {
                iso.addDefault("Slots.Color.Empty-1.Lore", new ArrayList<>());
                iso.addDefault("Slots.Color.Empty-2.Lore", new ArrayList<>());
            }
            iso.addDefault("Slots.Color.Green-Button.Lore", new ArrayList<>());
            iso.addDefault("Slots.Color.Blue-Button.Lore", new ArrayList<>());
            iso.copyDefaults();
            iso.save();
            for (String s : itemsPath) {
                boolean thorwError = false;
                String subPath = null;
                if (!iso.contains(s + ".Name")) {
                    thorwError = true;
                    subPath = ".Name";
                } else if (!iso.contains(s + ".Lore")) {
                    thorwError = true;
                    subPath = ".Lore";
                }
                if (thorwError) {
                    print("&c----------------------------------------------------");
                    print("&cISSUE FOUND IN ISLAND BORDER CONFIGURATION!!!");
                    print("The path '&c" + s + subPath + "&f' is missing from '&c" + map.getValue().getName() + "&f' Language.");
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
        GUI gui = new GUI(p, inventoryType, COLOR_GUI_TITLE.getString(uuid));
        String color = iso.getString("Color." + getVersionSupport().makeFirstLetterUppercase(playerData.getBorderColor().name().toLowerCase()));
        for (String s : itemsPath) {
            ItemBuilder item = materialsMap.get(s).getItem().setDisplayName(iso.getString(pathMap.get(s + ".Name")).replace("[color]", color));
            if (iso.getStringList(s + ".Lore").size() > 0) item.setLore(iso.getStringList(pathMap.get(s + ".Lore")));
            gui.addItem(new GuiItem(item.build(), slotsMap.get(s)).addClickAction(new ClickAction() {
                @Override
                public void onClick(GuiItem guiItem, GUI gui) {
                    switch (s.split("\\.")[1]) {
                        case "Red-Button": {
                            playerData.setBorderColor(RED);
                            break;
                        }
                        case "Green-Button": {
                            playerData.setBorderColor(GREEN);
                            break;
                        }
                        case "Blue-Button": {
                            playerData.setBorderColor(BLUE);
                            break;
                        }
                    }
                }
            }));
        }
    }

    private String formatMaterial(String string) {
        return string + ".Material";
    }

    private String formatSlot(String string) {
        return string + ".Slot";
    }
}
