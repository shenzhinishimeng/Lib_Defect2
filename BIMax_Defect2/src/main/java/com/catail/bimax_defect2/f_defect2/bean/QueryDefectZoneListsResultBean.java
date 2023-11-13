package com.catail.bimax_defect2.f_defect2.bean;

import java.util.List;

public class QueryDefectZoneListsResultBean {
    private Integer errno;
    private String errstr;
    private String errstr_cn;
    private ResultBean result;

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public String getErrstr() {
        return errstr;
    }

    public void setErrstr(String errstr) {
        this.errstr = errstr;
    }

    public String getErrstr_cn() {
        return errstr_cn;
    }

    public void setErrstr_cn(String errstr_cn) {
        this.errstr_cn = errstr_cn;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private String block;
        private String level;
        private List<UnitListBean> unit_list;

        public String getBlock() {
            return block;
        }

        public void setBlock(String block) {
            this.block = block;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public List<UnitListBean> getUnit_list() {
            return unit_list;
        }

        public void setUnit_list(List<UnitListBean> unit_list) {
            this.unit_list = unit_list;
        }

        public static class UnitListBean {
            private String drawing_id;
            private String drawing_name;
            private String drawing_path;
            private String drawing_position;
            private String unit;
            private String version;

            public String getDrawing_id() {
                return drawing_id;
            }

            public void setDrawing_id(String drawing_id) {
                this.drawing_id = drawing_id;
            }

            public String getDrawing_name() {
                return drawing_name;
            }

            public void setDrawing_name(String drawing_name) {
                this.drawing_name = drawing_name;
            }

            public String getDrawing_path() {
                return drawing_path;
            }

            public void setDrawing_path(String drawing_path) {
                this.drawing_path = drawing_path;
            }

            public String getDrawing_position() {
                return drawing_position;
            }

            public void setDrawing_position(String drawing_position) {
                this.drawing_position = drawing_position;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }
        }
    }
}
