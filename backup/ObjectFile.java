package org.acme;

import java.util.Objects;

public class ObjectFile {

    private Long   size;
    private String object;

    public ObjectFile() {
    }

    public static ObjectFile from(Object o) {
        ObjectFile file = new ObjectFile();
        if (o != null) {
            file.setObject(o.toString());
            file.setSize(Long.valueOf(o.toString()));
        }
        return file;
    }


    public String getObject() {
        return object;
    }

    public ObjectFile setObject(String object) {
        this.object = object;
        return this;
    }


    public Long getSize() {
        return size;
    }


    public ObjectFile setSize(Long size) {
        this.size = size;
        return this;
    }


}
