package com.catail.bimax_defect2.f_defect2.bean;

import java.io.Serializable;
import java.util.List;

public class QueryDefectBlockListsResultBean implements Serializable {

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

    public static class ResultBean implements Serializable {
        private List<BlockListBean> block_list;
        private ProgramDrawingBean program_drawing;

        public List<BlockListBean> getBlock_list() {
            return block_list;
        }

        public void setBlock_list(List<BlockListBean> block_list) {
            this.block_list = block_list;
        }

        public ProgramDrawingBean getProgram_drawing() {
            return program_drawing;
        }

        public void setProgram_drawing(ProgramDrawingBean program_drawing) {
            this.program_drawing = program_drawing;
        }

        public static class ProgramDrawingBean implements Serializable {
            private Integer drawing_id;
            private String drawing_name;
            private String drawing_path;
            private String program_version;

            public Integer getDrawing_id() {
                return drawing_id;
            }

            public void setDrawing_id(Integer drawing_id) {
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

            public String getProgram_version() {
                return program_version;
            }

            public void setProgram_version(String program_version) {
                this.program_version = program_version;
            }
        }

        public static class BlockListBean implements Serializable {
            private String block;
            private String block_version;
            private Integer drawing_id;
            private String drawing_name;
            private String drawing_path;
            private String drawing_position;

            public String getBlock() {
                return block;
            }

            public void setBlock(String block) {
                this.block = block;
            }

            public String getBlock_version() {
                return block_version;
            }

            public void setBlock_version(String block_version) {
                this.block_version = block_version;
            }

            public Integer getDrawing_id() {
                return drawing_id;
            }

            public void setDrawing_id(Integer drawing_id) {
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

            @Override
            public String toString() {
                return "BlockListBean{" +
                        "block='" + block + '\'' +
                        ", block_version='" + block_version + '\'' +
                        ", drawing_id=" + drawing_id +
                        ", drawing_name='" + drawing_name + '\'' +
                        ", drawing_path='" + drawing_path + '\'' +
                        ", drawing_position='" + drawing_position + '\'' +
                        '}';
            }
        }
    }
}
