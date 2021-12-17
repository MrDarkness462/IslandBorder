package com.github.zandy.islandborder.storage;

import com.github.zandy.bamboolib.database.utils.Column;

import java.util.ArrayList;
import java.util.List;

import static com.github.zandy.bamboolib.database.utils.Column.ColumnType.VARCHAR;
import static com.github.zandy.islandborder.files.Settings.SettingsEnum.*;

public class Database {
    private final String state = DEFAULT_BORDER_STATE.getString(), color = DEFAULT_BORDER_COLOR.getString(), defaultLanguage = DEFAULT_LANGUAGE.getString();

    public Database() {
        com.github.zandy.bamboolib.database.Database.init();
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("Player").setType(VARCHAR).setLength(25).setNotNull());
        columns.add(new Column("UUID").setType(VARCHAR).setLength(40).setNotNull().setPrimaryKey());
        columns.add(new Column("Enabled").setType(VARCHAR).setLength(5).setNotNull().setDefault(state));
        columns.add(new Column("Color").setType(VARCHAR).setLength(5).setNotNull().setDefault(color));
        columns.add(new Column("Language").setType(VARCHAR).setLength(3).setNotNull().setDefault(defaultLanguage));
        com.github.zandy.bamboolib.database.Database.getInstance().createTable("Island-Border", columns);
    }
}
