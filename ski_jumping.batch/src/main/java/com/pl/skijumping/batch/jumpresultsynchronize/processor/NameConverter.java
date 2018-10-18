package com.pl.skijumping.batch.jumpresultsynchronize.processor;

import com.pl.skijumping.diagnosticmonitor.DiagnosticMonitor;
import org.apache.commons.lang3.StringUtils;

class NameConverter {
    private final DiagnosticMonitor diagnosticMonitor;

    NameConverter(DiagnosticMonitor diagnosticMonitor) {
        this.diagnosticMonitor = diagnosticMonitor;
    }

    public String convert(String name) {
        if (name == null) {
            diagnosticMonitor.logWarn("Cannot convert null name");
            return null;
        }

        StringBuilder newName = new StringBuilder();
        String surname = null;
        String[] partOfNameList = name.toLowerCase().split(" ");
        for (String part : partOfNameList) {
            part = StringUtils.capitalize(part);
            if (!partOfNameList[0].equalsIgnoreCase(part)) {
                newName.append(part + " ");
            } else {
                surname = part;
            }
        }

        newName.append(surname);
        return newName.toString();
    }
}
