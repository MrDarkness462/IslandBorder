package com.github.zandy.islandborder.files.languages.iso;

import com.github.zandy.bamboolib.utils.BambooFile;
import com.github.zandy.islandborder.files.languages.Languages.LanguageEnum;

public class EN extends BambooFile {

    public EN() {
        super("Language_EN", "Languages");
        for (LanguageEnum langEnum : LanguageEnum.values()) addDefault(langEnum.getPath(), langEnum.getDefaultValue());
        copyDefaults();
        save();
    }
}