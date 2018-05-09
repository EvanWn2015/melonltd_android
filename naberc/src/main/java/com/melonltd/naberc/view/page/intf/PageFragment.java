package com.melonltd.naberc.view.page.intf;

import android.os.Bundle;

public interface PageFragment {

//    PageFragment getInstance(Bundle bundle);

    PageFragment getInstance(Bundle bundle);

    PageFragment newInstance(Object... o);
}
