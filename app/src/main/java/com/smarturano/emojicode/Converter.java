package com.smarturano.emojicode;

/**
 * Created by Steven on 1/20/2017.
 */

public class Converter {

    private static final String emo_regex = "([[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+])";

    static int [] emojiArray = {0x1F600, 0x1F601, 0x1F602, 0x1F603, 0x1F604, 0x1F605,
                                0x1F606, 0x1F609, 0x1F60A, 0x1F60B, 0x1F60E, 0x1F60D,
                                0x1F618, 0x1F617, 0x1F61A, 0x1F642, 0x1F917, 0x1F914,
                                0x1F610, 0x1F611, 0x1F636, 0x1F644, 0x1F60F, 0x1F623,
                                0x1F625, 0x1F62E, 0x1F910, 0x1F62F, 0x1F62A, 0x1F62B,
                                0x1F634, 0x1F60C, 0x1F913, 0x1F61B, 0x1F61C, 0x1F61D,
                                0x1F612, 0x1F613, 0x1F614, 0x1F615, 0x1F643, 0x1F911,
                                0x1F632, 0x1F641, 0x1F616, 0x1F61E, 0x1F61F, 0x1F624,
                                0x1F622, 0x1F62D, 0x1F626, 0x1F627, 0x1F628, 0x1F629,
                                0x1F62C, 0x1F630, 0x1F631, 0x1F633, 0x1F635, 0x1F621,
                                0x1F620, 0x1F607, 0x1F637, 0x1F912, 0x1F915, 0x1F608,
                                0x1F47F, 0x1F479, 0x1F480, 0x1F47B, 0x1F47D, 0x1F47E,
                                0x1F4A9, 0x1F63A, 0x1F638, 0x1F639, 0x1F63B, 0x1F63C,
                                0x1F63D, 0x1F640, 0x1F63F, 0x1F63E, 0x1F648, 0x1F649,
                                0x1F64A, 0x1F466, 0x1F467, 0x1F469, 0x1F474, 0x1F475,
                                0x1F476, 0x1F47C, 0x1F46E};

    static String textToEmoji(String input){
        String ts;
        String output = "";
        for(int i = 0; i < input.length(); i++){
            char mm = input.charAt(i);
            int mmVal = (int) Character.valueOf(mm);
            if(mmVal == 32){
                output += new String("  ");
            } else if(mm >= 33 && mm <= 126){
                output += new String(Character.toChars(emojiArray[mmVal - 33]));
            } else{

            }

        }
        return output;
    }

    static String emojiToText(String input){
        String ts;
        String output = "";
        for(int i = 0; i < input.length()/2; i++){
            String ms = input.substring((2 * i), (2 * i) + 2);
            //int mmVal = (int) Character.valueOf(mm);
            if(ms.matches(emo_regex)){ //character is an emoji
                String ps = ms;
                char cs[] = ps.toCharArray();
                if(!ps.equals("")) {
                    int emojiCode = ("" + cs[0] + cs[1]).codePointAt(0);
                    int asciiIntValue = getIndexInEmojiArray(emojiCode);
                    output += new String("" + ((char) (asciiIntValue + 33)));
                    //output = String.format("0x%08x", output);
                }
            } else{
                output += new String(" ");
            }
        }
        return output;
    }

    static int getIndexInEmojiArray(int emojiCode){
        for(int i = 0; i < emojiArray.length; i++){
            if(emojiArray[i] == emojiCode){
                return i;
            }
        }
        return -1;
    }

    public String encode(String input){
        String output;
        output = input;
        return output;
    }

    public String decode(String input){
        String output;
        output = input;
        return output;
    }
}
