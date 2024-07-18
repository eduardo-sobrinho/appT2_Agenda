package com.example.agenda;

public class SimpleMaskFormatter extends MaskFormatter {
    public static class SimpleMaskCharacter {
        public static final String NUMBER = "N";
        public static final String LETTER = "L";
    }

    public SimpleMaskFormatter(String mask) {
        super(mask);

        registerPattern(SimpleMaskCharacter.NUMBER, new MaskPattern("\\p{Digit}"));
        registerPattern(SimpleMaskCharacter.LETTER, new MaskPattern("[a-zA-ZÁÉÃÕáéãõ ]"));
    }
}
