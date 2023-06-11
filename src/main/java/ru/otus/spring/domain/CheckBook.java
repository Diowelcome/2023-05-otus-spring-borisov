package ru.otus.spring.domain;

import java.util.List;

public class CheckBook {

    private final List<CheckUnit> checkUnits;

    public CheckBook(List<CheckUnit> checkUnits) {
        this.checkUnits = checkUnits;
    }

    public int getSize() {
        return checkUnits.size();
    }

    public List<CheckUnit> getCheckUnits() {
        return checkUnits;
    }

    public CheckUnit getCheckUnit(int unitIndex) {
        return checkUnits.get(unitIndex);
    }

}
