package com.pl.skijumping.batch.importplaceevent;

import com.pl.skijumping.domain.model.HillType;

class HillSizeUtil {
    private HillSizeUtil() {
//
    }

    static HillType checkHillType(Integer hillSize) {
        if (hillSize > 0 && hillSize <= 50) {
            return HillType.SMALL_HILL;
        } else if (hillSize > 50 && hillSize <= 84) {
            return HillType.MEDIUM_HILL;
        } else if (hillSize > 84 && hillSize <= 109) {
            return HillType.NORMAL_HILL;
        } else if (hillSize > 109 && hillSize <= 184) {
            return HillType.NORMAL_HILL;
        }
        return HillType.SKI_FLYING_HILL;
    }
}