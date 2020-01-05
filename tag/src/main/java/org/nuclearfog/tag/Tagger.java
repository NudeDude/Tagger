package org.nuclearfog.tag;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Tagger {

    private static final String LINK_PATTERN_STRING = "[\n\\s]https?://[^\\s]+";
    private static final String TW_PATTERN_STRING = "[\n\\s][@#][^@#\\s\\^\\!\"\\§\\%\\&\\/\\(\\)\\=\\?\\´\\°\\{\\[\\]\\}\\\\\\`\\+\\-\\*\\'\\~\\.\\,\\;\\:\\<\\>\\|]+";
    private static final Pattern LINK_PATTERN = Pattern.compile(LINK_PATTERN_STRING);
    private static final Pattern TW_PATTERN = Pattern.compile(TW_PATTERN_STRING);
    private static final int MODE = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;


    /**
     * Make a spannable colored String with click listener
     *
     * @param text  String that should be spannable
     * @param color Text Color
     * @param l     click listener
     * @return Spannable String
     */
    public static Spannable makeText(final String text, final int color, @NonNull final OnTagClickListener l) {
        SpannableStringBuilder sText = new SpannableStringBuilder(text);
        sText.insert(0, " "); // Add whitespace at begin to match if target string is at beginning
        Matcher m = TW_PATTERN.matcher(sText);

        while (m.find()) {
            final int start = m.start();
            final int end = m.end();

            sText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    l.onClick(text.substring(start, end));
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    ds.setColor(color);
                    ds.setUnderlineText(false);
                }
            }, start, end, MODE);
        }
        sText.delete(0, 1); // Remove first whitespace added at the beginning of this method
        return sText;
    }


    /**
     * Make a spannable colored String with click listener
     * http(s) links included
     *
     * @param text  String that should be spannable
     * @param color Text Color
     * @param l     click listener
     * @return Spannable String
     */
    public static Spannable makeTextWithLinks(final String text, final int color, @NonNull final OnTagClickListener l) {
        SpannableStringBuilder sText = new SpannableStringBuilder(makeText(text, color, l));
        sText.insert(0, " "); // Add whitespace at begin to match if target string is at beginning
        Matcher m = LINK_PATTERN.matcher(sText);

        while (m.find()) {
            final int start = m.start();
            final int end = m.end();

            sText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    l.onClick(text.substring(start, end));
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    ds.setColor(color);
                    ds.setUnderlineText(false);
                }
            }, start, end, MODE);
        }
        sText.delete(0, 1); // Remove first whitespace added at the beginning of this method
        return sText;
    }


    /**
     * Make a spannable String without listener
     *
     * @param text  String that should be spannable
     * @param color Text Color
     * @return Spannable String
     */
    public static Spannable makeText(String text, int color) {
        SpannableStringBuilder sText = new SpannableStringBuilder(text);
        sText.insert(0, " "); // Add whitespace at begin to match if target string is at beginning
        Matcher m = TW_PATTERN.matcher(sText);

        while (m.find()) {
            ForegroundColorSpan sColor = new ForegroundColorSpan(color);
            sText.setSpan(sColor, m.start(), m.end(), MODE);
        }
        sText.delete(0, 1); // Remove first whitespace added at the beginning of this method
        return sText;
    }


    /**
     * Make a spannable String without listener
     * http(s) links included
     *
     * @param text  String that should be spannable
     * @param color Text Color
     * @return Spannable String
     */
    public static Spannable makeTextWithLinks(String text, int color) {
        SpannableStringBuilder sText = new SpannableStringBuilder(makeText(text, color));
        sText.insert(0, " "); // Add whitespace at begin to match if target string is at beginning
        Matcher m = LINK_PATTERN.matcher(sText);

        while (m.find()) {
            ForegroundColorSpan sColor = new ForegroundColorSpan(color);
            sText.setSpan(sColor, m.start(), m.end(), MODE);
        }
        sText.delete(0, 1); // Remove first whitespace added at the beginning of this method
        return sText;
    }


    /**
     * Interface definition of Tag click listener
     */
    public interface OnTagClickListener {
        /**
         * Called on tag click
         *
         * @param tag Clicked Tag
         */
        void onClick(String tag);
    }
}