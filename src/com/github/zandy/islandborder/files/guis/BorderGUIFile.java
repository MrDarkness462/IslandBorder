package com.github.zandy.islandborder.files.guis;

import org.bukkit.event.inventory.InventoryType;
import org.magenpurp.api.utils.FileManager;
import org.magenpurp.api.versionsupport.materials.Materials;

import static com.github.zandy.islandborder.files.guis.BorderGUIFile.BorderGUIEnum.*;
import static org.bukkit.event.inventory.InventoryType.HOPPER;
import static org.magenpurp.api.versionsupport.materials.Materials.*;

public class BorderGUIFile extends FileManager {
    public enum BorderGUIEnum {
        INVENTORY_TYPE("Inventory.Type", HOPPER.name()),
        SLOTS_ENABLE_BUTTON_SLOT("Slots.Enable-Button.Slot", 1),
        SLOTS_ENABLE_BUTTON_MATERIAL("Slots.Enable-Button.Material", LIME_BANNER.name()),
        SLOTS_DISABLE_BUTTON_SLOT("Slots.Disable-Button.Slot", 3),
        SLOTS_DISABLE_BUTTON_MATERIAL("Slots.Disable-Button.Material", RED_BANNER.name()),
        SLOTS_COLOR_BUTTON_SLOT("Slots.Color-Button.Slot", 5),
        SLOTS_COLOR_BUTTON_RED_MATERIAL("Slots.Color-Button.Red.Material", RED_DYE.name()),
        SLOTS_COLOR_BUTTON_GREEN_MATERIAL("Slots.Color-Button.Green.Material", LIME_DYE.name()),
        SLOTS_COLOR_BUTTON_BLUE_MATERIAL("Slots.Color-Button.Blue.Material", LIGHT_BLUE_DYE.name());

        final String path;
        final Object defaultValue;

        BorderGUIEnum(String path, Object defaultValue) {
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
            return InventoryType.valueOf(getBorderGUIFile().getString(getPath()));
        }

        public Materials getMaterial() {
            return Materials.valueOf(getBorderGUIFile().getString(getPath()));
        }
    }
    private static FileManager borderGuiFile;

    public BorderGUIFile() {
        super("Border", "GUIs");
        addDefault(INVENTORY_TYPE.getPath(), INVENTORY_TYPE.getDefaultValue());
        addDefault(SLOTS_ENABLE_BUTTON_SLOT.getPath(), SLOTS_ENABLE_BUTTON_SLOT.getDefaultValue());
        addDefault(SLOTS_ENABLE_BUTTON_MATERIAL.getPath(), SLOTS_ENABLE_BUTTON_MATERIAL.getDefaultValue());
        if (!contains(SLOTS_COLOR_BUTTON_BLUE_MATERIAL.getPath())) {
            addDefault("Slots.Empty-1.Slot", 2);
            addDefault("Slots.Empty-1.Material", WHITE_STAINED_GLASS_PANE.name());
        }
        addDefault(SLOTS_DISABLE_BUTTON_SLOT.getPath(), SLOTS_DISABLE_BUTTON_SLOT.getDefaultValue());
        addDefault(SLOTS_DISABLE_BUTTON_MATERIAL.getPath(), SLOTS_DISABLE_BUTTON_MATERIAL.getDefaultValue());
        if (!contains(SLOTS_COLOR_BUTTON_BLUE_MATERIAL.getPath())) {
            addDefault("Slots.Empty-2.Slot", 4);
            addDefault("Slots.Empty-2.Material", WHITE_STAINED_GLASS_PANE.name());
        }
        addDefault(SLOTS_COLOR_BUTTON_SLOT.getPath(), SLOTS_COLOR_BUTTON_SLOT.getDefaultValue());
        addDefault(SLOTS_COLOR_BUTTON_RED_MATERIAL.getPath(), SLOTS_COLOR_BUTTON_RED_MATERIAL.getDefaultValue());
        addDefault(SLOTS_COLOR_BUTTON_GREEN_MATERIAL.getPath(), SLOTS_COLOR_BUTTON_GREEN_MATERIAL.getDefaultValue());
        addDefault(SLOTS_COLOR_BUTTON_BLUE_MATERIAL.getPath(), SLOTS_COLOR_BUTTON_BLUE_MATERIAL.getDefaultValue());
        copyDefaults();
        save();
        borderGuiFile = this;
    }

    public static FileManager getBorderGUIFile() {
        return borderGuiFile;
    }
}