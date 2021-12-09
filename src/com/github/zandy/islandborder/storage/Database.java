package com.github.zandy.islandborder.storage;

import org.magenpurp.api.database.utils.Column;

import java.util.ArrayList;
import java.util.List;

import static com.github.zandy.islandborder.files.Settings.SettingsEnum.DEFAULT_BORDER_COLOR;
import static com.github.zandy.islandborder.files.Settings.SettingsEnum.DEFAULT_BORDER_STATE;
import static org.magenpurp.api.MagenAPI.getDatabase;
import static org.magenpurp.api.database.utils.Column.ColumnType.VARCHAR;

public class Database {
    private final String state = DEFAULT_BORDER_STATE.getString(), color = DEFAULT_BORDER_COLOR.getString();

    public Database() {
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("Player").setType(VARCHAR).setLength(25).setNotNull());
        columns.add(new Column("UUID").setType(VARCHAR).setLength(40).setNotNull().setPrimaryKey());
        columns.add(new Column("Enabled").setType(VARCHAR).setLength(5).setNotNull().setDefault(state));
        columns.add(new Column("Color").setType(VARCHAR).setLength(5).setNotNull().setDefault(color));
        getDatabase().createTable("Island-Border", columns);
    }
}
