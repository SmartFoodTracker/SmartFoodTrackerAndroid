package aaku492.smartfoodtracker.common;

import org.junit.Test;

import static aaku492.smartfoodtracker.common.StringUtils.titleCase;
import static org.junit.Assert.assertEquals;

/**
 * Created by Udey Rishi (udeyrishi) on 2017-03-20.
 * Copyright © 2017 ECE 492 Group 2 (Winter 2017), University of Alberta. All rights reserved.
 */
public class StringUtilsTest {

    @Test
    public void titleCase_changesCase() {
        String in = "   a PPle ; \t fooOOn\n\rarm     ";
        String converted = titleCase(in);
        assertEquals("   A Pple ; \t Foooon\n\rArm     ", converted);

        assertEquals("    ", titleCase("    "));
        assertEquals("", titleCase(""));
    }
}
