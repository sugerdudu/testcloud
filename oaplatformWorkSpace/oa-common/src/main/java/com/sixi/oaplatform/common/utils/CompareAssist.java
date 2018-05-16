package com.sixi.oaplatform.common.utils;

/**
 * 排序、比较辅助类
 * @author sugercgq
 *
 * @param <T>
 */
public class CompareAssist<T> {
    private T first;
    private T second;
    private int result;

    public CompareAssist(T first, T second) {
        this.first = first;
        this.second = second;
    }


    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public int getResult() {
        return result;
    }

    /**
     * 设置比较结果，请把getFirst()和getSecond()的比较结果放到这里
     * 0：getFirst()的结果 等于 getSecond()的结果
     * -1：getFirst()的结果 小于 getSecond()的结果
     * 1：getFirst()的结果 大于 getSecond()的结果
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


}