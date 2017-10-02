package io.github.lizhangqu.corepatch.generator.core;

/**
 * 支持的类型
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-10-02 19:44
 */
public enum CoreGeneratorType {
    BS("BS"),
    ARCHIVE("ARCHIVE");
    private String name;

    CoreGeneratorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
