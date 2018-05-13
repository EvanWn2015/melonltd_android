package com.melonltd.naberc.view.user.page.abs;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.melonltd.naberc.view.user.page.intf.PageFragment;

public abstract class AbsPageFragment extends Fragment implements PageFragment {

    @Override
    public abstract PageFragment getInstance(Bundle bundle);

    @Override
    public abstract PageFragment newInstance(Object... o);

}
