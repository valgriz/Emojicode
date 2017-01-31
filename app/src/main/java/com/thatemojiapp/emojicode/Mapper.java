package com.thatemojiapp.emojicode;

/**
 * Created by Steven on 1/26/2017.
 */

public class Mapper {

    public static char map(char c, int pos){
        char ret = 32;
        if(c == 32){ //char is a space
            return ret;
        } else { //char is a not a space
            char zeroed_ret = (char)(c - 33);
            zeroed_ret = (char)((zeroed_ret + pos) % 93);
            return (char) (zeroed_ret + 33);
        }
    }

    public static char unmap(char c, int pos){
        char ret = 32;
        if(c == 32){ //char is a space
            return ret;
        } else { //char is a not a space
            char zeroed_ret = (char)(c - 33);
            for(int i = 0; i < pos; i++){
                if(zeroed_ret > 0){
                    zeroed_ret--;
                } else if(zeroed_ret == 0){
                    zeroed_ret = 92;
                }
            }
            return (char) (zeroed_ret + 33);
        }
    }
}
