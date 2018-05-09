package com.melonltd.naberc.view.page.intf;

import android.os.Bundle;

public interface PageFragment {

    PageFragment newInstance(Bundle bundle);

    PageFragment newInstance(Object... o);
}
