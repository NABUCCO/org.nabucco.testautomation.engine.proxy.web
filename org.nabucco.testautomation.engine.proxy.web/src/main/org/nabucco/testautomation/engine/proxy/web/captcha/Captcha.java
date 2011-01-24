/*
* Copyright 2010 PRODYNA AG
*
* Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.opensource.org/licenses/eclipse-1.0.php or
* http://www.nabucco-source.org/nabucco-license.html
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.nabucco.testautomation.engine.proxy.web.captcha;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * A captcha is a graphical representation of a number or a code. This class is used to map the code
 * back to a string representation.
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class Captcha {

    private static final int BORDER_BLACK_WHITE = 5000000;

    private BufferedImage image;

    /**
     * Creates a new captcha for a specific image file.
     * 
     * @param file
     *            the image file
     * 
     * @throws IOException
     */
    public Captcha(File file) throws IOException {
        image = ImageIO.read(file);
    }

    /**
     * Creates a new captcha for a specific image url.
     * 
     * @param url
     *            the image url
     * 
     * @throws IOException
     */
    public Captcha(URL url) throws IOException {
        image = ImageIO.read(url);
    }

    /**
     * Recognizes digit sequences in a captcha and maps them to a string sequence.
     * 
     * @return the string representation of the captcha
     */
    public String readSequence() {

        StringBuilder sequence = new StringBuilder();

        List<int[]> character = new ArrayList<int[]>();

        for (int x = 0; x < image.getWidth(); x++) {

            int blackPixel = 0;

            int[] column = new int[image.getHeight()];

            for (int y = 0; y < image.getHeight(); y++) {

                int rgb = Math.abs(image.getRGB(x, y));

                if (rgb > BORDER_BLACK_WHITE) {
                    blackPixel++;
                    column[y] = 1;
                } else {
                    column[y] = 0;
                }
            }

            if (blackPixel != 0) {
                character.add(column);
            } else {
                if (!character.isEmpty()) {
                    int number = getDigit(character.toArray(new int[character.size()][]));
                    sequence.append(number);
                    character.clear();
                }
            }
        }

        return sequence.toString();
    }

    @Override
    public String toString() {
        return readSequence();
    }

    /**
     * Retrieves the digit representation for an array.
     * 
     * @param character
     *            the character as array
     * 
     * @return the digit
     */
    private int getDigit(int[][] character) {

        if (check(character, CaptchaCharacterMap.ZERO)) {
            return 0;
        }
        if (check(character, CaptchaCharacterMap.ONE)) {
            return 1;
        }
        if (check(character, CaptchaCharacterMap.TWO)) {
            return 2;
        }
        if (check(character, CaptchaCharacterMap.THREE)) {
            return 3;
        }
        if (check(character, CaptchaCharacterMap.FOUR)) {
            return 4;
        }
        if (check(character, CaptchaCharacterMap.FIVE)) {
            return 5;
        }
        if (check(character, CaptchaCharacterMap.SIX)) {
            return 6;
        }
        if (check(character, CaptchaCharacterMap.SEVEN)) {
            return 7;
        }
        if (check(character, CaptchaCharacterMap.EIGHT)) {
            return 8;
        }
        if (check(character, CaptchaCharacterMap.NINE)) {
            return 9;
        }

        return -1;
    }

    /**
     * Checks two character arrays for equality.
     * 
     * @param a
     *            the first character
     * @param a2
     *            the second character
     * 
     * @return <b>true</b> if the characters are equal, <b>false</b> if not
     */
    private boolean check(int[][] a, int[][] a2) {

        if (a == a2) {
            return true;
        }
        if (a == null || a2 == null) {
            return false;
        }

        int length = a.length;
        if (a2.length != length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (!Arrays.equals(a[i], a2[i])) {
                return false;
            }
        }

        return true;
    }

}
