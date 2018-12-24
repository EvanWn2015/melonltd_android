package com.melonltd.naber.vo;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.List;

public class IdentityTableVo implements Serializable {
    private static final long serialVersionUID = -282363793459333630L;
    public String area;
    public List<Identitys> identitys;


    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this.getClass())
                .add("areaa", area)
                .add("identitys", identitys)
                .toString();
    }



    public class Identitys implements Serializable {
        private static final long serialVersionUID = -7758988026098744320L;
        public String name;
        public List<String> items;


        @Override
        public String toString() {
            return MoreObjects
                    .toStringHelper(this.getClass())
                    .add("name", name)
                    .add("items", items)
                    .toString();
        }
    }
}
