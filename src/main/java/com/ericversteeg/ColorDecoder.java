package com.ericversteeg;

import java.awt.*;

public class ColorDecoder {

    public static Color decodeARGB(String hexCode)
    {
        if (hexCode.startsWith("#"))
        {
            hexCode = hexCode.substring(1);
        }

        long argb = Long.parseLong(hexCode, 16);

        int a = (int) ((argb >> 24) & 0xFF);
        int r = (int) ((argb >> 16) & 0xFF);
        int g = (int) ((argb >> 8) & 0xFF);
        int b = (int) (argb & 0xFF);

        return new Color(r, g, b, a);
    }
}
