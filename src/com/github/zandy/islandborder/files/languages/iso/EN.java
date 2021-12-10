package com.github.zandy.islandborder.files.languages.iso;

import com.github.zandy.islandborder.files.languages.Languages.LanguageEnum;
import org.magenpurp.api.utils.FileManager;

public class EN extends FileManager {

    public EN() {
        super("Language_EN", "Languages");
        for (LanguageEnum langEnum : LanguageEnum.values()) addDefault(langEnum.getPath(), langEnum.getDefaultValue());
        copyDefaults();
        save();
    }

}
