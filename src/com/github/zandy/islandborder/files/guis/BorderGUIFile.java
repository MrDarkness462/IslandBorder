package com.github.zandy.islandborder.files.guis;

import com.github.zandy.bamboolib.utils.BambooFile;
import com.github.zandy.bamboolib.versionsupport.material.Materials;
import org.bukkit.event.inventory.InventoryType;

import static com.github.zandy.bamboolib.versionsupport.material.Materials.*;
import static com.github.zandy.islandborder.files.guis.BorderGUIFile.BorderGUIEnum.*;

public class BorderGUIFile extends BambooFile {
    public enum BorderGUIEnum {
        INVENTORY_TYPE("Inventory.Type", InventoryType.HOPPER.name()),
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
            return InventoryType.valueOf(BorderGUIFile.getInstance().getString(getPath()));
        }

        public Materials getMaterial() {
            return Materials.valueOf(BorderGUIFile.getInstance().get().getString(getPath()));
        }
    }
    private static BorderGUIFile instance = null;
    private final BambooFile borderGuiFile;

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

    public BambooFile get() {
        return borderGuiFile;
    }

    public static BorderGUIFile getInstance() {
        if (instance == null) instance = new BorderGUIFile();
        return instance;
    }
}