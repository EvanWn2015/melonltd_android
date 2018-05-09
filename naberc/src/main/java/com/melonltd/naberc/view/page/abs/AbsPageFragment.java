package com.melonltd.naberc.view.page.abs;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.melonltd.naberc.view.page.intf.PageFragment;

public abstract class AbsPageFragment extends Fragment implements PageFragment {

    @Override
    public PageFragment newInstance(Bundle bundle) {
        return null;
    }

    @Override
    public PageFragment newInstance(Object... o) {
        return null;
    }

}
