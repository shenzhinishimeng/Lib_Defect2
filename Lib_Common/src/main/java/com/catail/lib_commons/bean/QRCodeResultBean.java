package com.catail.lib_commons.bean;

import java.io.Serializable;

public class QRCodeResultBean implements Serializable {
    private String model_id;
    private String program_id;
    private String uuid;
    private String version;

    private String block;
    private String model_level;
    private String level;

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getProgram_id() {
        return program_id;
    }

    public void setProgram_id(String program_id) {
        this.program_id = program_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getModel_level() {
        return model_level;
    }

    public void setModel_level(String model_level) {
        this.model_level = model_level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "QRCodeResultBean{" +
                "model_id='" + model_id + '\'' +
                ", program_id='" + program_id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", version='" + version + '\'' +
                ", block='" + block + '\'' +
                ", model_level='" + model_level + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
