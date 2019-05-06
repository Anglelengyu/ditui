package net.hongkuang.ditui.project.busi.order.dto;

import java.util.List;

/**
 * Created by apple on 2018/12/31.
 */
public class ImportOrderDto {
    private List<String> files;

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "ImportOrderDto{" +
                "files=" + files +
                '}';
    }
}
