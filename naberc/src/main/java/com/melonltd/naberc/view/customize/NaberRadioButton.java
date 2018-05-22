package com.melonltd.naberc.view.customize;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;

public class NaberRadioButton {
    private RadioButton radio;
    private String title = "", price = "";
    private String text = "";

    public NaberRadioButton() {

    }

    private NaberRadioButton setParameter(RadioButton radio, String title, String price) {
        this.radio = radio;
        this.title = title;
        this.price = price;
        this.text = title + "\u3000\u3000\u3000\u3000" + price + "$";
        this.radio.setText(this.text);
        return this;
    }

    public static Builder Builder(Context context) {
        return new Builder(context);
    }

    private void setTitleAndPrice(String title, String price){
        this.title = title;
        this.price = price;
        this.text = title + "\u3000\u3000\u3000\u3000" + price + "$";
        this.radio.setText(this.text);
    }

    private RadioButton getView() {
        return this.radio;
    }

    public static class Builder {
        private RadioButton radio;
        private NaberRadioButton naberRadio;
        private String title = "", price = "";

        Builder(Context context) {
            this.naberRadio = new NaberRadioButton();
            this.radio = new RadioButton(context);
            this.radio.setGravity(Gravity.START);
            this.radio.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            this.radio.setText("");
            this.radio.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            this.radio.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            this.radio.setPaddingRelative(0, 0, 0, 0);
        }

        public Builder setChecked(boolean checked) {
            this.radio.setChecked(checked);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPrice(String price) {
            this.price = price;
            return this;
        }

        public Builder setId(int id) {
            this.radio.setId(id);
            return this;
        }

        public Builder setTag(int id, Object o) {
            this.radio.setTag(id, o);
            return this;
        }

        public Builder setPadding(int left, int top, int right, int bottom) {
            this.radio.setPaddingRelative(left, top, right, bottom);
            return this;
        }

        public Builder setPadding(int padding) {
            setPadding(padding, padding, padding, padding);
            return this;
        }

        public Builder setTag(Object o) {
            this.radio.setTag(o);
            return this;
        }

        public RadioButton build() {
            return this.naberRadio.setParameter(this.radio, this.title, this.price).getView();
        }
    }
}
