package io.github.lizhangqu.corepatch.applier.core;

/**
 * 支持的类型
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:43
 */
public enum CoreApplierType {
    BS("BS"),
    ARCHIVE("ARCHIVE");

    private String name;

    CoreApplierType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
