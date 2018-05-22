package com.melonltd.naberc.view.customize;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;

import com.melonltd.naberc.R;

public class NaberCheckButton {
    private CheckBox box;
    private String title = "", price = "";
    private String text = "";

    public NaberCheckButton() {

    }

    private NaberCheckButton setParameter(CheckBox box, String title, String price) {
        this.box = box;
        this.title = title;
        this.price = price;
        this.text = title + "\u3000\u3000\u3000\u3000" + price + "$";
        this.box.setText(this.text);
        return this;
    }

    public static Builder Builder(Context context) {
        return new Builder(context);
    }

    private void setTitleAndPrice(String title, String price) {
        this.title = title;
        this.price = price;
        this.text = title + "\u3000\u3000\u3000\u3000" + price + "$";
        this.box.setText(this.text);
    }

    private CheckBox getView() {
        return this.box;
    }

    public static class Builder {
        private CheckBox box;
        private NaberCheckButton naberCheckBox;
        private String title = "", price = "";

        Builder(Context context) {
            this.naberCheckBox = new NaberCheckButton();
            this.box = new CheckBox(context, null, R.attr.radioButtonStyle);
            this.box.setGravity(Gravity.START);
            this.box.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            this.box.setText("");
            this.box.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            this.box.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            this.box.setPaddingRelative(0, 0, 0, 0);
        }

        public Builder setChecked(boolean checked) {
            this.box.setChecked(checked);
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
            this.box.setId(id);
            return this;
        }

        public Builder setTag(int id, Object o) {
            this.box.setTag(id, o);
            return this;
        }

        public Builder setPadding(int left, int top, int right, int bottom) {
            this.box.setPaddingRelative(left, top, right, bottom);
            return this;
        }

        public Builder setPadding(int padding) {
            setPadding(padding, padding, padding, padding);
            return this;
        }

        public Builder setTag(Object o) {
            this.box.setTag(o);
            return this;
        }

        public CheckBox build() {
            return this.naberCheckBox.setParameter(this.box, this.title, this.price).getView();
        }
    }
}
