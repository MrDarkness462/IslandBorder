package com.github.zandy.islandborder.files.guis;

import org.bukkit.event.inventory.InventoryType;
import org.magenpurp.api.utils.FileManager;
import org.magenpurp.api.versionsupport.materials.Materials;

import static com.github.zandy.islandborder.files.guis.ColorGUIFile.ColorGUIEnum.*;
import static org.bukkit.event.inventory.InventoryType.HOPPER;
import static org.magenpurp.api.versionsupport.materials.Materials.*;

public class ColorGUIFile extends FileManager {
    public enum ColorGUIEnum {
        INVENTORY_TYPE("Inventory.Type", HOPPER.name()),
        SLOTS_RED_BUTTON_SLOT("Slots.Red-Button.Slot", 1),
        SLOTS_RED_BUTTON_MATERIAL("Slots.Red-Button.Material", RED_DYE.name()),
        SLOTS_EMPTY_1_SLOT("Slots.Empty-1.Slot", 2),
        SLOTS_EMPTY_1_MATERIAL("Slots.Empty-1.Material", WHITE_STAINED_GLASS_PANE.name()),
        SLOTS_GREEN_BUTTON_SLOT("Slots.Green-Button.Slot", 3),
        SLOTS_GREEN_BUTTON_MATERIAL("Slots.Green-Button.Material", LIME_DYE.name()),
        SLOTS_EMPTY_2_SLOT("Slots.Empty-2.Slot", 4),
        SLOTS_EMPTY_2_MATERIAL("Slots.Empty-2.Material", WHITE_STAINED_GLASS_PANE.name()),
        SLOTS_BLUE_BUTTON_SLOT("Slots.Blue-Button.Slot", 5),
        SLOTS_BLUE_BUTTON_MATERIAL("Slots.Blue-Button.Material", LIGHT_BLUE_DYE.name())
        ;

        final String path;
        final Object defaultValue;

        ColorGUIEnum(String path, Object defaultValue) {
            this.path = path;
            this.defaultValue = defaultValue;
        }

        public String getPath() {
            return path;
        }

        public Object getDefaultValue() {
            return defaultValue;
        }

        public InventoryType getInventoryType() {
            return InventoryType.valueOf(getColorGUIFile().getString(getPath()));
        }

        public Materials getMaterial() {
            return Materials.valueOf(getColorGUIFile().getString(getPath()));
        }
    }
    private static FileManager colorGUIFile;

    public ColorGUIFile() {
        super("Color", "GUIs");
        addDefault(INVENTORY_TYPE.getPath(), INVENTORY_TYPE.getDefaultValue());
        addDefault(SLOTS_RED_BUTTON_SLOT.getPath(), SLOTS_RED_BUTTON_SLOT.getDefaultValue());
        addDefault(SLOTS_RED_BUTTON_MATERIAL.getPath(), SLOTS_RED_BUTTON_MATERIAL.getDefaultValue());
        if (!contains(SLOTS_BLUE_BUTTON_MATERIAL.getPath())) {
            addDefault(SLOTS_EMPTY_1_SLOT.getPath(), SLOTS_EMPTY_1_SLOT.getDefaultValue());
            addDefault(SLOTS_EMPTY_1_MATERIAL.getPath(), SLOTS_EMPTY_1_MATERIAL.getDefaultValue());
        }
        addDefault(SLOTS_GREEN_BUTTON_SLOT.getPath(), SLOTS_GREEN_BUTTON_SLOT.getDefaultValue());
        addDefault(SLOTS_GREEN_BUTTON_MATERIAL.getPath(), SLOTS_GREEN_BUTTON_MATERIAL.getDefaultValue());
        if (!contains(SLOTS_BLUE_BUTTON_MATERIAL.getPath())) {
            addDefault(SLOTS_EMPTY_2_SLOT.getPath(), SLOTS_EMPTY_2_SLOT.getDefaultValue());
            addDefault(SLOTS_EMPTY_2_MATERIAL.getPath(), SLOTS_EMPTY_2_MATERIAL.getDefaultValue());
        }
        addDefault(SLOTS_BLUE_BUTTON_SLOT.getPath(), SLOTS_BLUE_BUTTON_SLOT.getDefaultValue());
        addDefault(SLOTS_BLUE_BUTTON_MATERIAL.getPath(), SLOTS_BLUE_BUTTON_MATERIAL.getDefaultValue());
        copyDefaults();
        save();
        colorGUIFile = this;
    }

    public static FileManager getColorGUIFile() {
        return colorGUIFile;
    }
}
