package com.melonltd.naberc.view.common.abs;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.melonltd.naberc.view.common.intf.PageFragment;

public abstract class AbsPageFragment extends Fragment implements PageFragment {

    @Override
    public abstract PageFragment getInstance(Bundle bundle);

    @Override
    public abstract PageFragment newInstance(Object... o);

}
